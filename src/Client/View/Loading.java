package Client.View;

import Client.Controller.PlayerController;
import Client.Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javax.swing.border.EmptyBorder;

public class Loading {
    public void createGUI(Boolean check) {
        JFrame frame;
        JLabel imageLabel;
        JProgressBar progressBar;

        frame = new JFrame("Unpuzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(738, 625);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(createScaledImageIcon("Resources/Images/icon.jpeg",30,30).getImage());
        frame.setResizable(false);

        // Loading image from resource
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Resources/Images/loading.jpeg")));
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Adding vertical space with EmptyBorder

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 30)); // Set a thinner progress bar
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        progressBar.setForeground(Color.BLUE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.SOUTH);

        frame.add(panel);

        frame.setVisible(true);

        Timer timer = new Timer(50, e -> {
            int value = progressBar.getValue();
            if (value < 100) {
                value += 2;
                progressBar.setValue(value);
            } else {
                ((Timer) e.getSource()).stop();
                hideLoadingScreen(frame);
                if(check) {
                    String fileName = "User.txt";

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(fileName));
                        String line;
                        int count = 0;
                        int firstInt = 0, secondInt = 0, thirdInt = 0;
                        while ((line = reader.readLine()) != null && count < 3) {
                            int number = Integer.parseInt(line);
                            if (count == 0) {
                                firstInt = number;
                            } else if (count == 1) {
                                secondInt = number;
                            } else if (count == 2) {
                                thirdInt = number;
                            }

                            count++;
                        }
                        reader.close();
                        Home home = new Home();
                        PlayerController playerController=new PlayerController();
                        playerController.setPlayer(new Player(firstInt));
                        home.createGUI(playerController,secondInt,thirdInt);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    LoginSignupPage loginSignupPage=new LoginSignupPage();
                    loginSignupPage.createGUI();
                }
            }
        });
        timer.start();
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

    public void hideLoadingScreen(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }
}
