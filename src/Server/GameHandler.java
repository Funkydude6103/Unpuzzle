package Server;

import Client.Model.GameBoard;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class GameHandler implements Runnable {
    private final Socket player1Socket;
    private final Socket player2Socket;

    public GameHandler(Socket player1Socket, Socket player2Socket) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
    }

    @Override
    public void run() {
        try {
            //Sending the game Board to both of the Clients
            GameBoard gameBoard=new GameBoard();
            gameBoard.solve_ableBoardGenerator(1);
            ObjectOutputStream player1Out_ = new ObjectOutputStream(player1Socket.getOutputStream());
            ObjectOutputStream player2Out_ = new ObjectOutputStream(player2Socket.getOutputStream());
            player1Out_.writeObject(gameBoard);
            player2Out_.writeObject(gameBoard);

            //Threads to Listen to the Client
            AtomicBoolean gameFinished = new AtomicBoolean(false);

            // Create separate threads to listen for input from each socket
            Thread player1Thread = new Thread(() -> {
                try {
                    BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
                    String input=player1In.readLine();
                        if (input.equals("WIN:1") ) {
                            player2Out_.writeObject("LOSS:1");
                            player1Out_.writeObject("WIN:1");
                            gameFinished.set(true);
                        }
                    if(input.equals("LOSS:1") ) {
                        player2Out_.writeObject("WIN:1");
                        player1Out_.writeObject("LOSS:1");
                        gameFinished.set(true);
                    }
                        else if (input.equals("LOSS:2")) {
                            player2Out_.writeObject("WIN:2");
                            player1Out_.writeObject("LOSS:2");
                            gameFinished.set(true);
                        }
                    player2Socket.close();
                    player1Socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            player1Thread.start(); // Start the thread for player1Socket

            Thread player2Thread = new Thread(() -> {
                try {
                    BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
                    String input=player2In.readLine();
                        if (input.equals("LOSS:2")) {
                            player2Out_.writeObject("WIN:2");
                            player1Out_.writeObject("LOSS:2");
                            gameFinished.set(true); // Stop both threads
                        }
                        if (input.equals("WIN:1") ) {
                            player1Out_.writeObject("LOSS:1");
                            player2Out_.writeObject("WIN:1");
                            gameFinished.set(true);
                        }
                        if(input.equals("LOSS:1") ) {
                            player1Out_.writeObject("WIN:1");
                            player2Out_.writeObject("LOSS:1");
                            gameFinished.set(true);
                        }
                        player2Socket.close();
                        player1Socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            player2Thread.start(); // Start the thread for player2Socket

            // Wait for both threads to finish
            player1Thread.join();
            player2Thread.join();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
