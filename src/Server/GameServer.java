package Server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameServer{
    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(8888)) {
                System.out.println("Server waiting for players...");

                while (true) {
                    Socket player1Socket = serverSocket.accept();
                    System.out.println("Player 1 connected");

                    Socket player2Socket = serverSocket.accept();
                    System.out.println("Player 2 connected");

                    // Check if either socket is closed or not connected
                    try {
                        ObjectOutputStream player1Out_ = new ObjectOutputStream(player1Socket.getOutputStream());
                        ObjectOutputStream player2Out_ = new ObjectOutputStream(player2Socket.getOutputStream());
                    }
                    catch (Exception e) {
                        continue;
                    }

                    Thread gameThread = new Thread(new GameHandler(player1Socket, player2Socket));
                    gameThread.start();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}