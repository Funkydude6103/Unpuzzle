package Client.View;

import Client.Controller.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ageField;
    public void createGUI()
    {
        frame = new JFrame("Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(738, 625);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");

        // Fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        ageField = new JTextField(20);

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(ageLabel);
        panel.add(ageField);

        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String ageText = ageField.getText();

                // Convert age to integer (validate if necessary)
                int age = 0;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    // Handle invalid age input
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(username.equals("") || password.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please Fill all Fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PlayerController playerController=new PlayerController();
                if(playerController.signUp(username,password, age))
                {
                    JOptionPane.showMessageDialog(null,"Successful");
                    frame.dispose();
                    LoginSignupPage loginSignupPage=new LoginSignupPage();
                    loginSignupPage.createGUI();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Make the username Unique", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginSignupPage loginSignupPage=new LoginSignupPage();
                loginSignupPage.createGUI();
            }
        });

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        // Add components to main frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
