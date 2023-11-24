package Client.View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class LoginSignupPage{
public void createGUI()
{
    JFrame frame;
    frame = new JFrame("Unpuzzle");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(738, 625);
    frame.setLocationRelativeTo(null);

    // Loading image from resource
    ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Resources/Images/loading.jpeg")));
    JLabel imageLabel = new JLabel(imageIcon);
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageLabel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Adding vertical space with EmptyBorder

    JPanel buttonPanel = new JPanel(new FlowLayout());
    // Login button
    JButton loginButton = new JButton("Login");
    loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
    loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Login login=new Login();
            login.createGUI();
        }
    });
    buttonPanel.add(loginButton);

    // Signup button
    JButton signupButton = new JButton("Signup");
    signupButton.setFont(new Font("Arial", Font.PLAIN, 18));
    signupButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           frame.dispose();
           Signup signup=new Signup();
           signup.createGUI();
        }
    });
    buttonPanel.add(signupButton);
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(imageLabel, BorderLayout.NORTH);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    frame.add(panel);

    frame.setVisible(true);

}
}
