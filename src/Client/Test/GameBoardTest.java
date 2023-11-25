package Client.Test;

import Client.Model.GameBoard;
import Client.Model.Level;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    @org.junit.jupiter.api.Test
    void gameBoardTestWholeClass() {
        int i,j;
        GameBoard g=new GameBoard();
        g.solve_ableBoardGenerator(4);
        while (true) {
            g.printBoard();
            Scanner scanner = new Scanner(System.in);
            i = scanner.nextInt();
            j = scanner.nextInt();
            g.move(i,j);
        }
    }

}