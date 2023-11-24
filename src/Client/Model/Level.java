package Client.Model;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Level {
    private Integer id;
    private GameBoard gameBoard;
    public void saveToDatabase() {
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO Level (gameboard) VALUES (?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Serialize GameBoard object
                byte[] serializedGameBoard = serializeGameBoard(gameBoard);

                // Set the serialized GameBoard object as a BLOB in the prepared statement
                preparedStatement.setBytes(1, serializedGameBoard);

                // Execute the SQL query to insert the Level into the database
                preparedStatement.executeUpdate();
                System.out.println("Level saved to the database.");
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Method to serialize GameBoard object to byte array
    private byte[] serializeGameBoard(GameBoard gameBoard) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            // Serialize the GameBoard object to byte array
            objectOutputStream.writeObject(gameBoard);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static Level loadFromDatabase(int id) {
        Level level = null;
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM Level WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // ID found, retrieve the serialized GameBoard from the database
                    byte[] serializedGameBoard = resultSet.getBytes("gameboard");

                    // Deserialize the GameBoard object from the retrieved byte array
                    GameBoard gameBoard = deserializeGameBoard(serializedGameBoard);

                    // Create a new Level object and set its properties
                    level = new Level();
                    level.setId(resultSet.getInt("id"));
                    level.setGameBoard(gameBoard);

                    System.out.println("Level loaded from the database.");
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }

        return level;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard=gameBoard;
    }

    public void setId(int id) {
      this.id=id;
    }
    public Integer getId() {
        return id;
    }


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // Method to deserialize byte array to GameBoard object
    private static GameBoard deserializeGameBoard(byte[] serializedGameBoard) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedGameBoard);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

            // Deserialize the GameBoard object from byte array
            return (GameBoard) objectInputStream.readObject();
        }
    }


}
