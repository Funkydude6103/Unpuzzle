package Client.Model;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Player
{
    private Integer id;
    private String username;
    private String password;
    private Integer level;
    private Integer age;
    private Integer diamond;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDiamond() {
        return diamond;
    }

    public void setDiamond(Integer diamond) {
        this.diamond = diamond;
    }
    public Player()
    {

    }
    public Player(int id)
    {
        String query = "SELECT * FROM Player WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                            this.id=resultSet.getInt("id");
                            this.username=resultSet.getString("username");
                            this.password=resultSet.getString("password");
                            this.age=resultSet.getInt("age");
                            this.level=resultSet.getInt("level");
                            this.diamond=resultSet.getInt("diamonds");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp(String newUsername, String newPassword, int newAge) {
        // Check if the username already exists
        boolean usernameExists = checkUsernameExists(newUsername);

        if (usernameExists) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        } else {
            String insertQuery = "INSERT INTO Player(username, password, age, level, diamonds) VALUES (?, ?, ?, ?, ?)";
            try (Connection connection = Database.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setInt(3, newAge);
                preparedStatement.setInt(4, 1); // level by 1
                preparedStatement.setInt(5, 200); // Adding 200 diamonds

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Signed up successfully!");
                    return true;
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean checkUsernameExists(String usernameToCheck) {
        String query = "SELECT * FROM Player WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, usernameToCheck);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If result set has next, the username exists
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String providedUsername, String providedPassword) {
        String query = "SELECT * FROM Player WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, providedUsername);
            preparedStatement.setString(2, providedPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.username = resultSet.getString("username");
                    this.password = resultSet.getString("password");
                    this.age = resultSet.getInt("age");
                    this.level = resultSet.getInt("level");
                    this.diamond = resultSet.getInt("diamonds");
                    System.out.println("Login successful!");
                    File file = new File("User.txt");

                    // If the file doesn't exist, create a new file
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println("File created: " );
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                    writer.write(this.id+"\n");
                    writer.write("1\n");
                    writer.write("1\n");
                    writer.close();
                    return true;
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();

        String query = "SELECT * FROM Player";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Player player = new Player();
                player.setId(resultSet.getInt("id"));
                player.setUsername(resultSet.getString("username"));
                player.setPassword(resultSet.getString("password"));
                player.setAge(resultSet.getInt("age"));
                player.setLevel(resultSet.getInt("level"));
                player.setDiamond(resultSet.getInt("diamonds"));
                players.add(player);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return players;
    }
    public boolean save() {
        if (this.id == null) {
            System.out.println("Player ID not set. Cannot save.");
            return false;
        }

        String updateQuery = "UPDATE Player SET username = ?, password = ?, age = ?, level = ?, diamonds = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);
            preparedStatement.setInt(3, this.age);
            preparedStatement.setInt(4, this.level);
            preparedStatement.setInt(5, this.diamond);
            preparedStatement.setInt(6, this.id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player data updated successfully!");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deletefile() {
        String fileName = "User.txt";
        File file = new File(fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("File " + fileName + " has been deleted successfully.");
            } else {
                System.out.println("Failed to delete " + fileName);
            }
        } else {
            System.out.println("File " + fileName + " does not exist.");
        }
    }
}
