package Client.View;

import Client.Controller.PlayerController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class Setting
{
    public static JPanel createCheckBoxPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel inner=new JPanel();
        inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));

        int[] arr= PlayerController.readSecondAndThirdLines();
        boolean soundcheck=false;
        boolean musiccheck=false;
        if(arr[0]==1)
        {
            soundcheck=true;
        }
        if(arr[1]==1)
        {
            musiccheck=true;
        }


        // Sound label and checkbox
        JLabel soundLabel = new JLabel("Sound");
        soundLabel.setFont(new Font("Arial", Font.BOLD, 18));
        ImageIcon imageIcon = createScaledImageIcon("Resources/Images/sound.png",48,48); // Replace with your image path
        JLabel imageLabel = new JLabel(imageIcon);
        JCheckBox soundCheckBox = new JCheckBox();
        soundCheckBox.setSelected(soundcheck);
        soundCheckBox.setPreferredSize(new Dimension(100,100));
        JPanel j1=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        j1.add(imageLabel);
        j1.add(soundLabel);
        j1.add(soundCheckBox);
        inner.add(j1);

        inner.add(Box.createVerticalStrut(2));
        // Music label and checkbox
        JLabel musicLabel = new JLabel("Music");
        musicLabel.setFont(new Font("Arial", Font.BOLD, 18));
        ImageIcon imageIcon2 = createScaledImageIcon("Resources/Images/music.png",48,48); // Replace with your image path
        JLabel imageLabel2 = new JLabel(imageIcon2);
        JCheckBox musicCheckBox = new JCheckBox();
        musicCheckBox.setSelected(musiccheck);
        musicCheckBox.setPreferredSize(new Dimension(100,100));
        JPanel j2=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        j2.add(imageLabel2);
        j2.add(musicLabel);
        j2.add(musicCheckBox);
        inner.add(j2);

        inner.add(Box.createVerticalGlue());

        // Action listener for both checkboxes
        ActionListener checkBoxListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == soundCheckBox) {
                   PlayerController.modifySecondAndThirdLines(booleanToInt(soundCheckBox.isSelected()),booleanToInt(musicCheckBox.isSelected()));

                }
                if (e.getSource() == musicCheckBox) {
                    PlayerController.modifySecondAndThirdLines(booleanToInt(soundCheckBox.isSelected()),booleanToInt(musicCheckBox.isSelected()));
                    if(musicCheckBox.isSelected())
                    {
                        Home.playMusic();
                    }
                    else {
                        Home.stopMusic();
                    }
                }
            }
        };

        // Add the same action listener to both checkboxes
        soundCheckBox.addActionListener(checkBoxListener);
        musicCheckBox.addActionListener(checkBoxListener);
       panel.add(inner,BorderLayout.CENTER);
        return panel;
    }
    private static ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
        try {
            URL imageUrl = Setting.class.getResource(imagePath);
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
    public static int booleanToInt(boolean value) {
        return value ? 1 : 0;
    }
}
