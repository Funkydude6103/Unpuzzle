package Client.View;

import Client.Controller.PlayerController;
import Client.Model.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardPanel
{
    public static JScrollPane createScrollPane(PlayerController playerController)
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Heading Label
        JLabel headingLabel = new JLabel("Leaderboard");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headingLabel, BorderLayout.NORTH);

        // Panel to hold player information labels
        JPanel playersPanel = new JPanel(); // Adjust spacing as needed
        playersPanel.setLayout(new BoxLayout(playersPanel,BoxLayout.Y_AXIS));
        playersPanel.setBackground(Color.WHITE); // Optional: Set panel background color

        java.util.List<Player> players = playerController.getAllPlayers();
        players.sort(Comparator.comparingInt(Player::getLevel).reversed());

        // Create table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Position");
        tableModel.addColumn("Username");
        tableModel.addColumn("Level");

        int pos = 1;
        int lv = 100000;

        for (Player player : players) {
            if (lv < player.getLevel()) {
                pos++;
            }

            // Add player information to the table model
            tableModel.addRow(new Object[]{pos, player.getUsername(), player.getLevel()});
            lv = player.getLevel();
        }

        JTable playerTable = new JTable(tableModel);
        playerTable.setFont(new Font("Arial", Font.PLAIN, 15));
        playerTable.setRowHeight(25);


        // Highlight the row of the currently playing player
        Player currentPlayingPlayer = playerController.getPlayer();
        if (currentPlayingPlayer != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 1).toString().matches(currentPlayingPlayer.getUsername())){
                    playerTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
        playerTable.setDragEnabled(false);
       // playerTable.setCellSelectionEnabled(false);
        playerTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(playerTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
scrollPane.setName("p4");
        return scrollPane;
    }
}
