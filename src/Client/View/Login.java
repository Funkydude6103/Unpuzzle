package Client.View;

import Client.Controller.PlayerController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;


public class Login {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public void createGUI() {
        frame = new JFrame();
        frame.setTitle("Unpuzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(738, 625);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(createScaledImageIcon("Resources/Images/icon.jpeg",30,30).getImage());


        // Heading Label
        JLabel headingLabel = new JLabel("Login");
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

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);

        gbc.gridx++;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);

        gbc.gridx++;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login and Cancel buttons
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Fill all Fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PlayerController playerController = new PlayerController();
                if (playerController.login(username, password)) {
                    JOptionPane.showMessageDialog(null, "Successful");
                    frame.dispose();
                    Home home = new Home();
                    try {
                        home.createGUI(playerController, 1, 1);
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Credentials", "Error", JOptionPane.ERROR_MESSAGE);
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
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
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
