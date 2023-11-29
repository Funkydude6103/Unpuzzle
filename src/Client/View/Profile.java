package Client.View;

import Client.Controller.PlayerController;
import Client.Model.Player;

import javax.swing.*;
import java.awt.*;

public class Profile {
    public static JPanel createUserPanel(Player player, JFrame jFrame) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Heading Label
        JLabel headingLabel = new JLabel("Profile");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(headingLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(usernameLabel, gbc);

        gbc.gridx++;
        JTextField usernameField = new JTextField(player.getUsername());
        usernameField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        JTextField passwordField = new JTextField(player.getPassword());
        passwordField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        // Age label and field
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(ageLabel, gbc);

        gbc.gridx++;
        JTextField ageField = new JTextField(player.getAge().toString());
        ageField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(ageField, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            player.deletefile();
            jFrame.dispose();
            Loading loading = new Loading();
            loading.createGUI(PlayerController.checkAndCreateFile());
        });
        buttonPanel.add(logoutButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String ageText = ageField.getText();

            if (username.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (player.checkUsernameExists(username) && !player.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(panel, "Make the username Unique", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int age = Integer.parseInt(ageText);
                    if (age < 0) {
                        JOptionPane.showMessageDialog(panel, "Age must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        player.setAge(age);
                        player.setPassword(password);
                        player.setUsername(username);
                        player.save();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Age must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}
