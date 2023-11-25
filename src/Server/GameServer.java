package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements Runnable{
    private static final int PORT = 8888;
    private static final int MAX_PLAYERS = 100; // Maximum number of players supported by the server
    private List<GameHandler> waitingPlayers = new ArrayList<>();

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.run();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for players...");

            while (true) {
                if (waitingPlayers.size() < 2) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Player connected: " + socket);

                    GameHandler player = new GameHandler(socket);
                    waitingPlayers.add(player);

                    if (waitingPlayers.size() == 2) {
                        // Start the game for two waiting players
                        GameHandler.startGame(waitingPlayers.get(0), waitingPlayers.get(1));
                        waitingPlayers.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}