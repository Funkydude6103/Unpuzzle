package Client.View;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public class Home {
    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    public void createGUI() {
        JFrame jFrame=new JFrame("Unpuzzle");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(738, 625);
        jFrame.setLocationRelativeTo(null);

        try {
        InputStream inputStream = getClass().getResourceAsStream("Resources/Sound/music.wav");
            assert inputStream != null;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
        }


        panel1 = createContentPanel(Color.RED, "Panel 1");
        panel2 = createContentPanel(Color.GREEN, "Panel 2");
        panel3 = createContentPanel(Color.BLUE, "Panel 3");
        panel4 = createContentPanel(Color.YELLOW, "Panel 4");


        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel1, BorderLayout.CENTER);


        JButton button1 = createMenuButton("Resources/Images/leaderboard.png", panel1);
        JButton button2 = createMenuButton("Resources/Images/profile.png", panel2);
        JButton button3 = createMenuButton("Resources/Images/cart.png", panel3);
        JButton button4 = createMenuButton("Resources/Images/home.png", panel4);


        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
        menuPanel.add(button1);
        menuPanel.add(button2);
        menuPanel.add(button3);
        menuPanel.add(button4);


        jFrame.add(mainPanel, BorderLayout.CENTER);
        jFrame.add(menuPanel, BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }

    private JPanel createContentPanel(Color color, String label) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(new JLabel(label));
        return panel;
    }

    private JButton createMenuButton(String imagePath, JPanel targetPanel) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image image = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        JButton button = new JButton(scaledIcon);
        button.setPreferredSize(new Dimension(64, 64));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(targetPanel);
            }
        });
        button.setBorder(new EmptyBorder(0, 0, 0, 0));

        return button;
    }


    private void displayPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private ImageIcon createScaledIcon(String imagePath) {
        try {
            BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath)));
            Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Home home=new Home();
        home.createGUI();

    }
}


// For Sound
//    JFrame frame = new JFrame("Background Sound Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(300, 200);
//
//                // Create a panel for other components (optional)
//                JPanel panel = new JPanel();
//                frame.add(panel);
//                JButton button = new JButton("Click me!");
//                panel.add(button);
//                button.addActionListener(new ActionListener() {
//@Override
//public void actionPerformed(ActionEvent e) {
//        System.out.println("sdd");
//        }
//        });
//        try {
//        InputStream inputStream = getClass().getResourceAsStream("Resources/Sound/music.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//        clip.start();
//        while (clip.isRunning()) {
//        Thread.sleep(100);
//        }
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//        e.printStackTrace();
//        } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//        }
//        frame.setVisible(true);