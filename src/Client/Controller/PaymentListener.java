package Client.Controller;

import javax.crypto.Mac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class PaymentListener implements ActionListener {
    private int Payment;
    private String url = "https://sandbox.jazzcash.com.pk/ApplicationAPI/API/2.0/Purchase/DoMWalletTransaction";
    private String merchantId = "MC61756";
    private String password = "sb4h0tebyv";
    private String integritySalt = "47zstx9934";
   //private String paymentToken = "YOUR_PAYMENT_TOKEN";
    private static final OkHttpClient client = new OkHttpClient();
    public PaymentListener(String payment)
    {
        String [] arr=payment.split(" ");
        Payment=Integer.parseInt(arr[1]);
    }
    @Override
    public void actionPerformed(ActionEvent e1) {
        // Generate timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        String txnRefNo = "TxnRef" + timestamp;
        String amount = String.valueOf(this.Payment);
        String txnCurrency = "PKR";
        String billReference = "billRef123";
        String description = "Testpayment";
        String txnExpiryDateTime = sdf.format(new Date(System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000)));
        // Create message hash
        String messageHash = integritySalt + "&" + amount + "&" + billReference + "&" +
                description + "&" + merchantId + "&" + password + "&" +
                txnCurrency + "&" + timestamp + "&" + txnExpiryDateTime + "&" + txnRefNo;
        String secureHash = calculateHmacSha256(messageHash, integritySalt);

        // Create request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("pp_MerchantID", merchantId);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_Password", password);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_Language", "EN");
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_MobileNumber", "03123456789");
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_CNIC", "345678");
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
//        try {
//            requestBody.put("pp_PaymentToken", paymentToken);
//        } catch (JSONException ex) {
//            throw new RuntimeException(ex);
//        }
        try {
            requestBody.put("pp_TxnRefNo", txnRefNo);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_Amount", amount);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_TxnCurrency", txnCurrency);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_TxnDateTime", timestamp);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_BillReference", billReference);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_Description", description);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            requestBody.put("pp_TxnExpiryDateTime", txnExpiryDateTime);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        try {
            System.out.println(secureHash);
            requestBody.put("pp_SecureHash", secureHash);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }

        // Make HTTP POST request
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
