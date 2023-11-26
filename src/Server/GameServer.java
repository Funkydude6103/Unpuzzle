package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                    Thread gameThread = new Thread(new GameHandler(player1Socket, player2Socket));
                    gameThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}