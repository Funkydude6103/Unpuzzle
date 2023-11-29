package Client.View;

import Client.Controller.PlayerController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class Signup {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ageField;
    public void createGUI() {
        frame = new JFrame("Unpuzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(738, 625);
        frame.setIconImage(createScaledImageIcon("Resources/Images/icon.jpeg",30,30).getImage());
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Heading Label
        JLabel headingLabel = new JLabel("Sign Up");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(headingLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");

        // Fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        ageField = new JTextField(20);

        // Add components to panel using GridBagConstraints
        panel.add(usernameLabel, gbc);
        gbc.gridy++;
        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(ageLabel, gbc);
        gbc.gridy++;
        panel.add(ageField, gbc);
        gbc.gridy++;

        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String ageText = ageField.getText();

                int age = 0;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Password validation
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Fill all Fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PlayerController playerController = new PlayerController();
                if (playerController.signUp(username, password, age)) {
                    JOptionPane.showMessageDialog(null, "Successful");
                    frame.dispose();
                    LoginSignupPage loginSignupPage = new LoginSignupPage();
                    loginSignupPage.createGUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Make the username Unique", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginSignupPage loginSignupPage = new LoginSignupPage();
                loginSignupPage.createGUI();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // Add components to main frame
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    private ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                Image img = ImageIO.read(imageUrl);
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

