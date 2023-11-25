package Client.View;

import Client.Controller.PlayerController;
import Client.Model.GameBoard;
import Client.Model.Level;
import Client.Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class LevelPanel
{
    private static JLabel label23;
    private static JPanel jPanel;
    public static void UpdateDiamondLabel(Player p)
    {
        label23.setText(Integer.toString(p.getDiamond()));
    }
    public static void update(Player p,JFrame J)
    {
        jPanel.removeAll();
        jPanel=LevelPanel.createLevelPanel(p,J);
        jPanel.revalidate();
        jPanel.repaint();
    }
    public static JPanel createLevelPanel(Player p,JFrame J)
    {
        Level level=Level.loadFromDatabase(p.getLevel());
        jPanel=new JPanel();
        jPanel.setBackground(Color.lightGray);
        jPanel.setLayout(new BorderLayout());
        JPanel upper=new JPanel(new FlowLayout(FlowLayout.LEFT,90,10));
        JPanel j1=new JPanel(new FlowLayout());
        JPanel j2=new JPanel(new FlowLayout());
        JPanel j3=new JPanel(new FlowLayout());
        // Left side with two button icons
        JButton button1 = new JButton(createScaledImageIcon("Resources/Images/setting.png",48,48)); // Replace with your icon image path
        JButton button2 = new JButton(createScaledImageIcon("Resources/Images/retry.png",48,48)); // Replace with your icon image path
        JButton button3 = new JButton(createScaledImageIcon("Resources/Images/profile.png",48,48));
        button1.setPreferredSize(new Dimension(48, 48));
        button2.setPreferredSize(new Dimension(48, 48));
        button3.setPreferredSize(new Dimension(48, 48));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home.displayPanel(LevelPanel.createLevelPanel(p,J));
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerController.toProfile(jPanel,p,J);
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerController.toSetting(jPanel,p,J);
            }
        });
        //button1.setBackground(Color.WHITE);
        //button2.setBackground(Color.WHITE);
        button1.setBorder(new EmptyBorder(0, 0, 0, 0));
        button2.setBorder(new EmptyBorder(0, 0, 0, 0));
        button3.setBorder(new EmptyBorder(0, 0, 0, 0));
        j1.add(button1);
        j1.add(button3);
        j1.add(button2);
        // Centered label "Level 21"
        JLabel levelLabel = new JLabel("Level "+String.valueOf(p.getLevel()));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        j2.add(levelLabel);

        // Right side with an image and a label
        ImageIcon imageIcon = createScaledImageIcon("Resources/Images/diamond.png",48,48); // Replace with your image path
        JLabel imageLabel = new JLabel(imageIcon);
         label23 = new JLabel(String.valueOf(p.getDiamond()));
        label23.setFont(new Font("Arial", Font.BOLD, 20));
        j3.add(imageLabel);
        j3.add(label23);

        upper.add(j1);
        upper.add(j2);
        upper.add(j3);

       jPanel.add(upper,BorderLayout.NORTH);
        if(level!=null) {
            GamePanel gamePanel=new GamePanel();
            jPanel.add(gamePanel.createGUI(level,p,J), BorderLayout.CENTER);
        }
        else
        {
            GameBoard gameBoard=new GameBoard();
            gameBoard.solve_ableBoardGenerator(p.getLevel());
            level=new Level();
            level.setGameBoard(gameBoard);
            level.saveToDatabase();
            GamePanel gamePanel=new GamePanel();
            jPanel.add(gamePanel.createGUI(gameBoard,p,J),BorderLayout.CENTER);
        }
        jPanel.setName("p1");
        return jPanel;
    }

    private static ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
        try {
            URL imageUrl = LevelPanel.class.getResource(imagePath);
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
