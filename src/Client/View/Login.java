package Client.View;

import Client.Controller.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login
{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public void createGUI()
    {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(738, 625);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if(username.equals("") || password.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please Fill all Fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PlayerController playerController=new PlayerController();
                if(playerController.login(username,password))
                {
                    JOptionPane.showMessageDialog(null,"Successful");
                    frame.dispose();
                    Home home=new Home();
                    try {
                        home.createGUI(playerController,1,1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Wrong Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the login window
                frame.dispose();
                LoginSignupPage loginSignupPage=new LoginSignupPage();
                loginSignupPage.createGUI();
            }
        });

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Add components to main frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }
}
