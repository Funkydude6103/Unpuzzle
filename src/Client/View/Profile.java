package Client.View;

import Client.Controller.PlayerController;
import Client.Model.Player;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profile
{
    public static JPanel createUserPanel(Player player,JFrame jFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel for labels and text fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 0, 10)); // Three rows, two columns

        // Username label and field (Bigger font)
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bigger font
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
        usernameField.setText(player.getUsername());
        usernameField.setSize(50,50);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);

        // Password label and field (Bigger font)
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bigger font
        JTextField passwordField = new JTextField();
        passwordField.setText(player.getPassword());
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Age label and field (Bigger font)
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bigger font
        JTextField ageField = new JTextField();
        ageField.setText(player.getAge().toString());
        ageField.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);

        panel.add(inputPanel, BorderLayout.CENTER);

        // Logout button (Left-aligned, South position)
        JButton logoutButton = new JButton("Logout");
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.deletefile();
                jFrame.dispose();
                Loading loading=new Loading();
                loading.createGUI(PlayerController.checkAndCreateFile());
            }
        });
       // panel.add(logoutPanel, BorderLayout.SOUTH);

        // Save button (Right-aligned, South position)
        JButton saveButton = new JButton("Save");
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        savePanel.add(saveButton);
        JPanel p=new JPanel();
        p.add(logoutPanel);
        p.add(savePanel);
        panel.add(p, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String ageText = ageField.getText();

            // Mandatory checks
            if (username.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int age = Integer.parseInt(ageText);

                    // Additional check for positive age values
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


        return panel;
    }

}
