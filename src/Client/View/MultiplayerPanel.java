package Client.View;

import Client.Controller.MultiplayerListener;
import Client.Controller.PlayerController;
import Client.Model.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;

public class MultiplayerPanel
{
    private  JButton[][] buttons;
    private static ActionListener a;
    public static Socket socket;
    public static Boolean gameStarted=null;

    public static JPanel crateMultiplayerPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("p2");
        return panel;
    }
    public JPanel createMultiplayerPanel(JFrame jFrame, PlayerController playerController) throws IOException, ClassNotFoundException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setName("p2");

        // Creating a loading screen components
        ImageIcon loadingIcon = createScaledImageIcon("Resources/Images/loading.jpeg",150,150); // Replace with your loading image
        JLabel loadingLabel = new JLabel(loadingIcon);

        JProgressBar loadingBar = new JProgressBar();
        loadingBar.setIndeterminate(true); // Indeterminate progress bar for loading
        loadingBar.setStringPainted(true);
        loadingBar.setPreferredSize(new Dimension(300, 30)); // Set a thinner progress bar
        loadingBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loadingBar.setForeground(Color.BLUE);

        JLabel waitingLabel = new JLabel("Waiting for other player...");
       waitingLabel.setFont(new Font("Arial", Font.BOLD, 30));
       waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the panel
        panel.add(loadingLabel, BorderLayout.CENTER);
        panel.add(loadingBar, BorderLayout.SOUTH);
        panel.add(waitingLabel, BorderLayout.NORTH);

        // Create a separate thread to listen for the GameBoard from the server
        new Thread(() -> {
            try {
                gameStarted=false;
                socket = new Socket("localhost", 8888);
                ObjectInputStream in_ = new ObjectInputStream(socket.getInputStream());
                GameBoard receivedGameBoard = (GameBoard) in_.readObject();
                receivedGameBoard.printBoard();
                MultiplayerListener multiplayerListener=new MultiplayerListener(socket,in_,playerController,jFrame);
                multiplayerListener.start();
                // Update the UI on the Event Dispatch Thread (EDT)
                jFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        if(socket!=null && socket.isConnected() && !socket.isClosed()) {
                            PrintWriter out = null;
                            try {
                                out = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            out.println("LOSS:1"); // Sending a line of text to the server
                            gameStarted = null;
                        }
                    }
                });
                SwingUtilities.invokeLater(() -> {
                    gameStarted=true;
                    panel.removeAll();
                    panel.add( createGUI(receivedGameBoard,playerController.getPlayer(),jFrame,socket));
                    panel.revalidate();
                    panel.repaint();
                });


            } catch (IOException | ClassNotFoundException e) {
                Home.displayPanel(LevelPanel.createLevelPanel(playerController.getPlayer(),jFrame));
                e.printStackTrace();
            }
        }).start();

        return panel;
    }
    private ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
        try {
            URL imageUrl = MultiplayerPanel.class.getResource(imagePath);
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

    public  JPanel createGUI(GameBoard gameBoard, Player p, JFrame jframe,Socket socket) {
        JPanel main=new JPanel(new BorderLayout());
        JPanel movesPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,270,0));
        JPanel jPanel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];
        Tile[][] board = gameBoard.getBoard();
        JLabel label23 = new JLabel("Moves "+gameBoard.getMoves());
        label23.setFont(new Font("Arial", Font.BOLD, 15));
        movesPanel.add(label23);
        main.add(movesPanel,BorderLayout.NORTH);
        a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m[]=label23.getText().split(" ");
                int mo=Integer.parseInt(m[1]);
                mo--;
                label23.setText("Moves "+mo);
                JButton button = (JButton) e.getSource();
                String s = button.getName();
                String[] s1 = s.split(",");
                int i = Integer.parseInt(s1[0]);
                int j = Integer.parseInt(s1[1]);
                if(gameBoard.move(i, j))
                {
                    int[] c =PlayerController.readSecondAndThirdLines();
                    if(c[0]==1)
                        playSound();
                }
                if(gameBoard.empty())
                {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    out.println("WIN:1"); // Sending a line of text to the server
                    gameStarted=null;
                }
                if(mo==0)
                {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    out.println("LOSS:1"); // Sending a line of text to the server
                    gameStarted=null;
                }
                //gameBoard.printBoard();
                updateGUI(gameBoard,jPanel); // Update GUI after move
            }
        };

        initializeButtons(board,jPanel);
        main.add(jPanel,BorderLayout.CENTER);
        return main;
    }
    private  void initializeButtons(Tile[][] board,JPanel jPanel) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(40, 40));
                buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setName(i + "," + j);
                buttons[i][j].addActionListener(a);

                setButtonIcon(board[i][j], i, j); // Set initial icons
                jPanel.add(buttons[i][j]);
            }
        }
    }

    private void setButtonIcon(Tile tile, int i, int j) {
        if (tile instanceof Up) {
            buttons[i][j].setIcon(createScaledImageIcon(((Up) tile).imagePath, 40, 40));
        } else if (tile instanceof Down) {
            buttons[i][j].setIcon(createScaledImageIcon(((Down) tile).imagePath, 40, 40));
        } else if (tile instanceof Left) {
            buttons[i][j].setIcon(createScaledImageIcon(((Left) tile).imagePath, 40, 40));
        } else if (tile instanceof Right) {
            buttons[i][j].setIcon(createScaledImageIcon(((Right) tile).imagePath, 40, 40));
        } else if (tile instanceof Rotatory) {
            buttons[i][j].setIcon(createScaledImageIcon(((Rotatory) tile).imagePath, 40, 40));
        } else if (tile instanceof Blade) {
            buttons[i][j].setIcon(createScaledImageIcon(((Blade) tile).imagePath, 40, 40));
        } else if (tile instanceof Wall) {
            buttons[i][j].setIcon(createScaledImageIcon(((Wall) tile).imagePath, 40, 40));
        } else {
            buttons[i][j].setName("-");

        }
    }
    private  void updateGUI(GameBoard gameBoard,JPanel jPanel) {
        Tile[][] board = gameBoard.getBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                setButtonIcon(board[i][j], i, j);
            }
        }
        jPanel.removeAll();
        initializeButtons(gameBoard.getBoard(),jPanel);
        jPanel.repaint();
        jPanel.revalidate();
    }
    public static void playSound() {
        try {
            // Load the audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(GamePanel.class.getResourceAsStream("Resources/Sound/SoundEffect.wav"))
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            //Thread.sleep(clip.getMicrosecondLength() / 1000);
            //clip.stop();
            //clip.close();
            //audioInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
