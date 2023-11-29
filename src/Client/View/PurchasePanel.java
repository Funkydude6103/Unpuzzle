package Client.View;

import Client.Controller.PaymentListener;
import Client.Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

public class PurchasePanel {
    public static JScrollPane createPurchasePanel(Player player) {
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 0, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Added empty border for vertical space

        String[] squareNames = {"Mini", "Small", "Medium", "Maximum", "Grand", "Ultra"};
        String[] images = {"Resources/Images/1.jpeg", "Resources/Images/2.jpeg", "Resources/Images/3.jpeg", "Resources/Images/4.jpeg", "Resources/Images/5.jpeg", "Resources/Images/6.jpeg"};
        String[] prices = {"100", "200", "300", "400", "500", "600"};
        int i = 0;
        for (String name : squareNames) {
            JPanel roundedSquare = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    int radius = 30;
                    g2d.setColor(Color.RED); // Change color according to your preference
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
                    g2d.dispose();
                }
            };
            roundedSquare.setLayout(new BorderLayout());

            JLabel nameLabel = new JLabel(name);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setForeground(Color.WHITE); // Label text color
            roundedSquare.add(nameLabel, BorderLayout.NORTH);

            JPanel centerPanel = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel(createScaledImageIcon(images[i], 100, 100)); // Replace with your image path
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel helloLabel = new JLabel(prices[i]);
            helloLabel.setHorizontalAlignment(SwingConstants.CENTER);

            centerPanel.add(imageLabel, BorderLayout.CENTER);
            centerPanel.add(helloLabel, BorderLayout.SOUTH);
            roundedSquare.add(centerPanel, BorderLayout.CENTER);

            JButton buyButton = new JButton("Buy " + prices[i] + " PKR");
            buyButton.addActionListener(new PaymentListener("Buy " + prices[i] + " PKR",player));
            buyButton.setBackground(Color.green);
            buyButton.setPreferredSize(new Dimension(100, 30)); // Defined width and height for the button
            roundedSquare.add(buyButton, BorderLayout.SOUTH);
            i++;
            roundedSquare.setBackground(Color.white);

            mainPanel.add(roundedSquare);
        }
       JPanel m=new JPanel(new BorderLayout());m.add(mainPanel,BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(m);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setName("p3");
        return scrollPane;
    }
    private static ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
        try {
            URL imageUrl = PurchasePanel.class.getResource(imagePath);
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
