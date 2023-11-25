package Server;

import java.net.Socket;

class GameHandler {
    private Socket socket;

    public GameHandler(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    public boolean isDisconnected() {

        return socket.isClosed() || !socket.isConnected();
    }

    public static void startGame(GameHandler player1, GameHandler player2) {
        // Start a new thread or manage a game instance between two players
        // Example: Create a new Game instance and handle communication between player1 and player2
        // You'll need to implement your game logic here

        // Example:
        // Game game = new Game(player1, player2);
        // game.start(); // Start the game

        System.out.println("Game started between " + player1.getSocket() + " and " + player2.getSocket());

        while (!player1.isDisconnected() && !player2.isDisconnected()) {
            System.out.println("d");
        }

        // If a player disconnected, handle the situation accordingly
        if (player1.isDisconnected()) {
            System.out.println("Player 1 disconnected");
            // Handle the case where player 1 has disconnected
        } else if (player2.isDisconnected()) {
            System.out.println("Player 2 disconnected");
            // Handle the case where player 2 has disconnected
        }
    }
}
