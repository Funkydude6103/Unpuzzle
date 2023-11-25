package Client.Test;

import Client.Model.GameBoard;
import Client.Model.Level;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
//Only Works If the Level Table is Empty
    @org.junit.jupiter.api.Test
    void saveToDatabase() {
        Level level=new Level();
        GameBoard gameBoard=new GameBoard();
        gameBoard.setMoves(3);
        level.setGameBoard(gameBoard);
        level.saveToDatabase();
    }

    @org.junit.jupiter.api.Test
    void loadFromDatabase() {
        Level level=Level.loadFromDatabase(1);
        GameBoard b=level.getGameBoard();
        assertEquals(b.getMoves(),3);
    }
}