package Client.Controller;

import Client.Model.GameBoard;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiplayerListener2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            ObjectInputStream in_ = new ObjectInputStream(socket.getInputStream());
            GameBoard receivedGameBoard = (GameBoard) in_.readObject();
            receivedGameBoard.printBoard();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
