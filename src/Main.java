import Client.Controller.PlayerController;
import Client.Model.Player;
import Client.View.Loading;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Loading loading=new Loading();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loading.createGUI(PlayerController.checkAndCreateFile());
            }
        });

    }
}