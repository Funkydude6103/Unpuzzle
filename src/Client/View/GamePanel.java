package Client.View;
import Client.Controller.PlayerController;
import Client.Model.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class GamePanel {
    private  JButton[][] buttons;
    private static ActionListener a;
    public static void playMusic()
    {
        try {
            InputStream inputStream = Home.class.getResourceAsStream("Resources/Sound/SoundEffect.wav");
            assert inputStream != null;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
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
    public JPanel createGUI(Level level, Player p, JFrame jFrame) {
        JPanel main=new JPanel(new BorderLayout());
        JPanel jPanel = new JPanel(new GridLayout(10, 10));
        JPanel movesPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,270,0));
        JButton buyMoves=new JButton("Buy Moves");
        buttons = new JButton[10][10];
        GameBoard gameBoard = level.getGameBoard();
        Tile[][] board = gameBoard.getBoard();
        JLabel label23 = new JLabel("Moves "+gameBoard.getMoves());
        label23.setFont(new Font("Arial", Font.BOLD, 15));
        buyMoves.setBackground(Color.BLACK);
        buyMoves.setForeground(Color.WHITE);
        movesPanel.add(label23);
        movesPanel.add(buyMoves);
        buyMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"5 Moves - 50 Diamonds",
                        "10 Moves - 90 Diamonds",
                        "20 Moves - 150 Diamonds"};

                int choice = JOptionPane.showOptionDialog(null,
                        "Choose an option:",
                        "Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) {
                    // Handle 5 Moves - 50 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 5 Moves - 50 Diamonds");
                    // Place your logic for this choice here
                } else if (choice == 1) {
                    // Handle 10 Moves - 90 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 10 Moves - 90 Diamonds");
                    // Place your logic for this choice here
                } else if (choice == 2) {
                    // Handle 20 Moves - 150 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 20 Moves - 150 Diamonds");
                    // Place your logic for this choice here
                }
            }

        });
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
                    JOptionPane.showMessageDialog(null, "Congrats, here are 50 Diamonds", "Level Finished", JOptionPane.INFORMATION_MESSAGE);
                    p.setDiamond(p.getDiamond()+50);
                    p.setLevel(p.getLevel()+1);
                    p.save();
                    Home.displayPanel(LevelPanel.createLevelPanel(p,jFrame));
                }
                if(mo==0)
                {
                    JOptionPane.showMessageDialog(null, "Moves are finished", "Moves Finished", JOptionPane.INFORMATION_MESSAGE);
                    Home.displayPanel(LevelPanel.createLevelPanel(p,jFrame));
                }
                //gameBoard.printBoard();
                updateGUI(gameBoard,jPanel); // Update GUI after move
            }
        };

        initializeButtons(board,jPanel);
        main.add(jPanel,BorderLayout.CENTER);
        return main;
    }
    public  JPanel createGUI(GameBoard gameBoard,Player p, JFrame jframe) {
        JPanel main=new JPanel(new BorderLayout());
        JPanel movesPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,270,0));
        JButton buyMoves=new JButton("Buy Moves");

        JPanel jPanel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];
        Tile[][] board = gameBoard.getBoard();
        JLabel label23 = new JLabel("Moves "+gameBoard.getMoves());
        label23.setFont(new Font("Arial", Font.BOLD, 15));
        movesPanel.add(label23);
        movesPanel.add(buyMoves);
        main.add(movesPanel,BorderLayout.NORTH);
        buyMoves.setBackground(Color.BLACK);
        buyMoves.setForeground(Color.WHITE);
        buyMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"5 Moves - 50 Diamonds",
                        "10 Moves - 90 Diamonds",
                        "20 Moves - 150 Diamonds"};

                int choice = JOptionPane.showOptionDialog(null,
                        "Choose an option:",
                        "Options",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) {
                    // Handle 5 Moves - 50 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 5 Moves - 50 Diamonds");
                    // Place your logic for this choice here
                } else if (choice == 1) {
                    // Handle 10 Moves - 90 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 10 Moves - 90 Diamonds");
                    // Place your logic for this choice here
                } else if (choice == 2) {
                    // Handle 20 Moves - 150 Diamonds
                    JOptionPane.showMessageDialog(null, "You selected: 20 Moves - 150 Diamonds");
                    // Place your logic for this choice here
                }
            }

        });
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
                    JOptionPane.showMessageDialog(null, "Congrats, here are 50 Diamonds", "Level Finished", JOptionPane.INFORMATION_MESSAGE);
                    p.setDiamond(p.getDiamond()+50);
                    p.setLevel(p.getLevel()+1);
                    p.save();
                    Home.displayPanel(LevelPanel.createLevelPanel(p,jframe));
                }
                if(mo==0)
                {
                    JOptionPane.showMessageDialog(null, "Moves are finished", "Moves Finished", JOptionPane.INFORMATION_MESSAGE);
                    Home.displayPanel(LevelPanel.createLevelPanel(p,jframe));
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
}
