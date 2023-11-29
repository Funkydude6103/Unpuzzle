package Client.Controller;

import javax.crypto.Mac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import Client.Model.Player;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.text.*;
import java.nio.charset.StandardCharsets;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerRetrieveParams;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentListener implements ActionListener {
    private int Payment;

   private Player player;
    public PaymentListener(String payment, Player player)
    {
        this.player=player;
        String [] arr=payment.split(" ");
        Payment=Integer.parseInt(arr[1]);
    }
    @Override
    public void actionPerformed(ActionEvent e1) {
        JTextField cardNumberField = new JTextField(10);
        ((PlainDocument) cardNumberField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        JTextField cvcField = new JTextField(3);
        ((PlainDocument) cvcField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        JTextField expiryMonthField = new JTextField(2);
        ((PlainDocument) expiryMonthField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        JTextField expiryYearField = new JTextField(4);
        ((PlainDocument) expiryYearField.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvcField);
        panel.add(new JLabel("Expiry Month:"));
        panel.add(expiryMonthField);
        panel.add(new JLabel("Expiry Year:"));
        panel.add(expiryYearField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Card Details:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String result_ = verifyPayment(cardNumberField.getText(),expiryMonthField.getText(),expiryYearField.getText(),cvcField.getText(),String.valueOf(Payment));
            if(result_.equals("success"))
            {
                JOptionPane.showMessageDialog(null,"Payment Success Diamonds Added");
                player.setDiamond(player.getDiamond()+Payment);
                player.save();
            }
            else
            {
              JOptionPane.showMessageDialog(null,"Payment Failure");
            }
        } else {
            System.out.println("User canceled the operation.");
        }



    }

    private static String calculateHmacSha256(String message, String key) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] hash = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String verifyPayment(String cc, String mon, String yr, String cvv, String amount) {
        String result = "success";
        if (cc.isBlank())
            return result = "Invalid Card Number";
        if (mon.isBlank())
            return result = "Invalid Expiry Month";
        if (yr.isBlank())
            return result = "Invalid Expiry Year";
        if (cvv.isBlank())
            return result = "Invalid CVV Code";

        try {
            Stripe.apiKey = "sk_test_51OHqYiGpWxWAnbt9dG0CQzEJTHAmq5eLVtjLF90PEVmf0SR5w3QF4G2fNGpKcm36Bg4HmPBwoHCrLhGKHRbDwQH600zCbPlNdO";

            // Creating a customer
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", "2@2.com");
            Customer newCustomer = Customer.create(customerParams);

            // Retrieve the created customer with sources expanded
            CustomerRetrieveParams retrieveParams = CustomerRetrieveParams.builder()
                    .addExpand("sources")
                    .build();
            Customer customer = Customer.retrieve(newCustomer.getId(), retrieveParams, null);

            // Use tokenization for the provided card information
            Token token = createToken(cc, mon, yr, cvv);

            // Adding the token as a source to the customer
            Map<String, Object> source = new HashMap<>();
            source.put("source", token.getId());
            customer.getSources().create(source);

            // Creating a charge for the given amount
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "PKR");
            chargeParams.put("customer", customer.getId());

            Charge.create(chargeParams);

            // Optional: Print customer details for debugging
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(customer));

        } catch (StripeException ex) {
            String error = ex.toString();
            System.out.println(error);
            if (error.contains("Your card number is incorrect."))
                return "Your card number is incorrect.";
            if (error.contains("invalid_expiry_year"))
                return "Your card's Expiration Date is Invalid.";
            if (error.contains("invalid_expiry_month"))
                return "Your card's Expiration Date is Invalid.";
            if (error.contains("invalid_cvc"))
                return "Your card's Security Code is Invalid.";
            if (error.contains("card_declined"))
                return "Your card was Declined, Try A Different Card.";
            if (error.contains("testmode_charges_only"))
                return "API Key is Down.";
            else
                return "Payment Failed";
        }
        return result;
    }

    private static Token createToken(String cc, String mon, String yr, String cvv) throws StripeException {
        // Create a map representing the card details
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cc); // Use the provided card number
        cardParams.put("exp_month", Integer.parseInt(mon)); // Use provided expiration month
        cardParams.put("exp_year", Integer.parseInt(yr)); // Use provided expiration year
        cardParams.put("cvc", cvv); // Use provided CVC

        // Create a map for token creation using the provided card details
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);

        // Create a token using the provided card information
        return Token.create(tokenParams);
    }
    static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }

        private boolean test(String text) {
            return text.matches("\\d*"); // Only allow numeric characters (digits)
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            }
        }
    }
}

//    // Generate timestamp
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//    String timestamp = sdf.format(new Date());
//
//    String txnRefNo = "TxnRef" + timestamp;
//    String amount = String.valueOf(this.Payment);
//    String txnCurrency = "PKR";
//    String billReference = "billRef123";
//    String description = "Testpayment";
//    String txnExpiryDateTime = sdf.format(new Date(System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000)));
//    // Create message hash
//    String messageHash = integritySalt + "&" + amount + "&" + billReference + "&" +
//            description + "&" + merchantId + "&" + password + "&" +
//            txnCurrency + "&" + timestamp + "&" + txnExpiryDateTime + "&" + txnRefNo;
//    String secureHash = calculateHmacSha256(messageHash, integritySalt);
//
//    // Create request body
//    JSONObject requestBody = new JSONObject();
//        try {
//                requestBody.put("pp_MerchantID", merchantId);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_Password", password);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_Language", "EN");
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_MobileNumber", "03123456789");
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_CNIC", "345678");
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
////        try {
////            requestBody.put("pp_PaymentToken", paymentToken);
////        } catch (JSONException ex) {
////            throw new RuntimeException(ex);
////        }
//                try {
//                requestBody.put("pp_TxnRefNo", txnRefNo);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_Amount", amount);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_TxnCurrency", txnCurrency);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_TxnDateTime", timestamp);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_BillReference", billReference);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_Description", description);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                requestBody.put("pp_TxnExpiryDateTime", txnExpiryDateTime);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//                try {
//                System.out.println(secureHash);
//                requestBody.put("pp_SecureHash", secureHash);
//                } catch (JSONException ex) {
//                throw new RuntimeException(ex);
//                }
//
//                // Make HTTP POST request
//                try {
//                RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());
//                Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//                Response response = client.newCall(request).execute();
//                if (response.isSuccessful()) {
//                assert response.body() != null;
//                System.out.println("Response: " + response.body().string());
//                } else {
//                System.out.println("Request failed");
//                }
//                } catch (Exception e) {
//                e.printStackTrace();
//                }