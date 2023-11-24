package Client.View;
import Client.Controller.PlayerController;
import Client.Model.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class Home {
    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JScrollPane panel3;
    private JScrollPane panel4;
    private static Clip clip;
    public static void playMusic()
    {
        try {
            InputStream inputStream = Home.class.getResourceAsStream("Resources/Sound/music.wav");
            assert inputStream != null;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
    public void createGUI(PlayerController playerController,int sound,int music) {
        Player p=playerController.getPlayer();
        System.out.println(p.getUsername());
        JFrame jFrame=new JFrame("Unpuzzle");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(738, 625);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setIconImage(createScaledImageIcon("Resources/Images/icon.jpeg",30,30).getImage());

        if(music==1)
            playMusic();


        panel1 = LevelPanel.createLevelPanel(playerController.getPlayer(),jFrame);
        panel2 = createContentPanel(Color.GREEN, "Panel 2");
        panel3 = PurchasePanel.createPurchasePanel();
        panel4 = LeaderBoardPanel.createScrollPane(playerController);


        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel1, BorderLayout.CENTER);


        JButton button1 = createMenuButton("Resources/Images/home.png", panel1,playerController,jFrame);
        JButton button2 = createMenuButton("Resources/Images/multiplayer.png", panel2,playerController,jFrame);
        JButton button3 = createMenuButton("Resources/Images/cart.png", panel3,playerController,jFrame);
        JButton button4 = createMenuButton("Resources/Images/leaderboard.png", panel4,playerController,jFrame);


        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
        menuPanel.setBackground(Color.WHITE);
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

    private JButton createMenuButton(String imagePath, JComponent targetPanel,PlayerController p,JFrame jFrame) {
        ImageIcon icon = createScaledImageIcon(imagePath, 48, 48);
        JButton button = new JButton(icon);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(48, 48));
        button.setMargin(new Insets(5, 5, 5, 5));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(targetPanel.getName().matches("p1"))
                displayPanel(LevelPanel.createLevelPanel(p.getPlayer(),jFrame));
                else if(targetPanel.getName().matches("p3"))
                    displayPanel(PurchasePanel.createPurchasePanel());
                else if(targetPanel.getName().matches("p4"))
                    displayPanel(LeaderBoardPanel.createScrollPane(p));
                else
                {
                    displayPanel(targetPanel);
                }


            }
        });
        button.setBorder(new EmptyBorder(0, 0, 0, 0));

        return button;
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


    private void displayPanel(JComponent panel) {
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