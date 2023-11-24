package Client.View;
//package Client.View;
//
//import Client.Model.*;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.net.URL;
//import java.util.Arrays;
//
//public class GamePanel
//{
//    private static JButton buttons[][];
//    private static JPanel jPanel;
//    private static ActionListener a;
//    public static JPanel  createGUI()
//    {
//        jPanel=new JPanel(new GridLayout(10,10));
//        buttons=new JButton[10][10];
//        GameBoard gameBoard=new GameBoard();
//        gameBoard.solve_ableBoardGenerator(20);
//        Tile[][] board=gameBoard.getBoard();
//        a=new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JButton button= (JButton) e.getSource();
//                String s=button.getName();
//                String[] s1 =s.split(",");
//                int i=Integer.parseInt(s1[0]);
//                int j=Integer.parseInt(s1[1]);
//                gameBoard.move(i,j);
//                gameBoard.printBoard();
//            }
//        };
//        for(int i=0;i<10;i++)
//        {
//            for(int j=0;j<10;j++)
//            {
//                if (board[i][j] instanceof Up) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Up) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//                }
//                else if (board[i][j]  instanceof Down)
//                {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Down) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//
//                }
//                else if (board[i][j]  instanceof Left) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Left) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//
//                }
//                else if (board[i][j]  instanceof Right) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Right) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//                }
//                else if (board[i][j]  instanceof Rotatory) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Rotatory) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//                }
//                else if (board[i][j]  instanceof Blade) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Blade) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//                }
//                else if (board[i][j]  instanceof Wall) {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setIcon(createScaledImageIcon(((Wall) board[i][j]).imagePath,40,40));
//                    buttons[i][j].setName(i+","+j);
//                    buttons[i][j].addActionListener(a);
//                }
//                else
//                {
//                    buttons[i][j] = new JButton();
//                    buttons[i][j].setPreferredSize(new Dimension(40, 40));
//                    buttons[i][j].setMargin(new Insets(5, 5, 5, 5)); // Add margin around buttons
//                    buttons[i][j].setBackground(Color.WHITE);
//                    buttons[i][j].setName("-");
//                }
//               jPanel.add(buttons[i][j]);
//            }
//        }
//
//        return jPanel;
//
//    }
//    private static ImageIcon createScaledImageIcon(String imagePath, int width, int height) {
//        try {
//            URL imageUrl = LevelPanel.class.getResource(imagePath);
//            if (imageUrl != null) {
//                Image img = ImageIO.read(imageUrl);
//                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//                return new ImageIcon(scaledImg);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
import Client.Model.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class GamePanel {
    private static JButton[][] buttons;
    private static ActionListener a;

    public static JPanel createGUI() {
        JPanel jPanel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];
        GameBoard gameBoard = new GameBoard();
        gameBoard.solve_ableBoardGenerator(20);
        Tile[][] board = gameBoard.getBoard();
        a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String s = button.getName();
                String[] s1 = s.split(",");
                int i = Integer.parseInt(s1[0]);
                int j = Integer.parseInt(s1[1]);
                gameBoard.move(i, j);
                gameBoard.printBoard();
                updateGUI(gameBoard,jPanel); // Update GUI after move
            }
        };

        initializeButtons(board,jPanel);

        return jPanel;
    }

    private static void initializeButtons(Tile[][] board,JPanel jPanel) {
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

    private static void setButtonIcon(Tile tile, int i, int j) {
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

    private static void updateGUI(GameBoard gameBoard,JPanel jPanel) {
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
