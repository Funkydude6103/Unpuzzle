package Client.Controller;

import Client.Model.Player;
import Client.View.Profile;
import Client.View.Setting;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class PlayerController
{
    private Player player;
    public static boolean checkAndCreateFile() {
        String fileName = "User.txt";
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("File already exists.");
            return true;
        } else {
            return false;
        }
    }

    public static void toSetting(JPanel jPanel,Player p,JFrame jFrame) {
        jPanel.removeAll();
        JPanel newPanel = Setting.createCheckBoxPanel(); // Create a new panel
        jPanel.add(newPanel); // Add the new panel to the existing jPanel
        jPanel.revalidate(); // Revalidate the jPanel to update its contents
        jPanel.repaint();

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean signUp(String newUsername, String newPassword, int newAge)
    {
        Player player1=new Player();
        return  player1.signUp(newUsername,newPassword,newAge);
    }
    public boolean login(String providedUsername, String providedPassword)
    {
        Player player1=new Player();
        this.player=player1;
        return  player1.login(providedUsername,providedPassword);
    }
    public List<Player> getAllPlayers()
    {
        return player.getAllPlayers();
    }
    public static void toProfile(JPanel jPanel,Player p,JFrame jFrame)
    {
        jPanel.removeAll();
        JPanel newPanel = Profile.createUserPanel(p,jFrame); // Create a new panel
        jPanel.add(newPanel); // Add the new panel to the existing jPanel
        jPanel.revalidate(); // Revalidate the jPanel to update its contents
        jPanel.repaint();
    }

    public static int[] readSecondAndThirdLines() {
        int[] lines = new int[2]; // Array to hold the second and third lines

        try (BufferedReader reader = new BufferedReader(new FileReader("User.txt"))) {
            // Read the first line (skipping it)
            reader.readLine();

            // Read the second and third lines as integers
            lines[0] = Integer.parseInt(reader.readLine());
            lines[1] = Integer.parseInt(reader.readLine());

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void modifySecondAndThirdLines(int newNumber1, int newNumber2) {
        try (BufferedReader reader = new BufferedReader(new FileReader("User.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("User_temp.txt"))) {

            // Read the first line and copy it to the temporary file
            String line = reader.readLine();
            writer.write(line + "\n");

            // Modify the second and third lines with new numbers and write to the temporary file
            writer.write(newNumber1 + "\n");
            writer.write(newNumber2 + "\n");


        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file after modifications
        File originalFile = new File("User.txt");
        originalFile.delete();
        File tempFile = new File("User_temp.txt");
        if (tempFile.renameTo(new File("User.txt"))) {
            System.out.println("Lines modified successfully.");
        } else {
            System.out.println("Failed to modify lines.");
        }
    }
}
