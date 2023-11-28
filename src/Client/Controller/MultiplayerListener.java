package Client.Controller;

import Client.Model.GameBoard;
import Client.View.Home;
import Client.View.LevelPanel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiplayerListener extends Thread {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private PlayerController player;
    private JFrame jframe;
    public MultiplayerListener(Socket socket, ObjectInputStream objectInputStream,PlayerController player,JFrame jframe)
    {
        super();
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.player=player;
        this.jframe=jframe;
    }
    @Override
    public void run() {
        try {
            String message=(String) objectInputStream.readObject();
            if(message.split(":")[0].equals("WIN"))
            {
                JOptionPane.showMessageDialog(null,"CONGRATS YOU WIN HERES 100 DIAMONDS AS A REWARD");
                player.getPlayer().setDiamond(player.getPlayer().getDiamond()+100);
                player.getPlayer().save();
                Home.displayPanel(LevelPanel.createLevelPanel(player.getPlayer(),jframe));

            }
            if (message.split(":")[0].equals("LOSS"))
            {
                JOptionPane.showMessageDialog(null,"YOU LOSE");
                Home.displayPanel(LevelPanel.createLevelPanel(player.getPlayer(),jframe));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
