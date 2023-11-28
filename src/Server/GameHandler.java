package Server;

import Client.Model.GameBoard;

import java.io.*;
import java.net.Socket;

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
            GameBoard gameBoard=new GameBoard();
            gameBoard.solve_ableBoardGenerator(20);
            ObjectOutputStream player1Out_ = new ObjectOutputStream(player1Socket.getOutputStream());
            ObjectOutputStream player2Out_ = new ObjectOutputStream(player2Socket.getOutputStream());
            player1Out_.writeObject(gameBoard);
            player2Out_.writeObject(gameBoard);

            player1Socket.close();
            player2Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
