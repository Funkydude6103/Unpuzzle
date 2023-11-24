package Client.Test;

import Client.Model.GameBoard;
import Client.Model.Level;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @org.junit.jupiter.api.Test
    void saveToDatabase() {
        Level level=new Level();
//        level.setGameBoard(new GameBoard("hello"));
        level.saveToDatabase();
    }

    @org.junit.jupiter.api.Test
    void loadFromDatabase() {
        Level level=Level.loadFromDatabase(1);
        GameBoard b=level.getGameBoard();
//        assertEquals(b.getName(),"hello");
    }
}