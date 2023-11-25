package Client.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameBoard implements Serializable
{
    private int moves;
    private Tile[][] board;

    public Tile[][] getBoard() {
        return board;
    }
    public Tile[][] deepCopyBoard() {
        if (board == null) {
            return null; // Or handle as required for your use case
        }
        Tile[][] copiedBoard = new Tile[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                copiedBoard[i][j] = board[i][j]; // Assuming Tile implements Cloneable
            }
        }
        return copiedBoard;
    }


    public GameBoard()
    {
        moves=0;
        board=new Tile[10][10];
        for (int i=0;i<10;i++)
        {
            Arrays.fill(board[i], null);

        }
    }
    public static int generateNumber(int gameLevel) {
        final int initialNumber = 20; // Number for level 1
        final int incrementPerLevel = 5; // Increment for each level

        // Generate the number based on the provided cases
        int generatedNumber = initialNumber + ((gameLevel - 1) * incrementPerLevel);

        // Ensure the generated number doesn't exceed 100
        int result = Math.min(generatedNumber, 100); // Cap at 100

        return result;
    }
    public void updateCheckArray(Boolean check[][][], int i, int j, Tile tile) {
        if (tile instanceof Up) {
            // Make all check[][][1] false above the Up tile
            for (int k = i - 1; k >= 0; k--) {
                check[k][j][1] = false;
                check[k][j][5] = false;
                check[k][j][6] = false;
            }
        } else if (tile instanceof Down) {
            // Make all check[][][0] false below the Down tile
            for (int k = i + 1; k < 10; k++) {
                check[k][j][0] = false;
                check[k][j][5] = false;
                check[k][j][6] = false;
            }
        } else if (tile instanceof Left) {
            // Make all check[][][3] false to the left of the Left tile
            for (int k = j - 1; k >= 0; k--) {
                check[i][k][3] = false;
                check[i][k][5] = false;
                check[i][k][6] = false;
                check[i][k][1]=false;
            }
        } else if (tile instanceof Right) {
            // Make all check[][][2] false to the right of the Right tile
            for (int k = j + 1; k < 10; k++) {
                check[i][k][2] = false;
                check[i][k][5] = false;
                check[i][k][6] = false;
                check[i][k][1]=false;
            }
        } else if (tile instanceof Rotatory) {
            // Make all check[][][2] false to the right, [1] above, [0] below, [3] to the left of the Rotatory tile
            for (int k = j + 1; k < 10; k++) {
                check[i][k][2] = false; // Right
                check[i][k][5] = false; // Right
            }
            for (int k = i - 1; k >= 0; k--) {
                check[k][j][1] = false; // Up
                check[k][j][5] = false; // Right
            }
            for (int k = i + 1; k < 10; k++) {
                check[k][j][0] = false; // Down
                check[k][j][5] = false; // Right
            }
            for (int k = j - 1; k >= 0; k--) {
                check[i][k][3] = false; // Left
                check[i][k][5] = false; // Right
            }
        }
        else if (tile instanceof Wall) {
            // Make all check[][][2] false to the right, [1] above, [0] below, [3] to the left of the Rotatory tile
            for (int k = j + 1; k < 10; k++) {
                check[i][k][2] = false; // Right
                check[i][k][5] = false; // Right
            }
            for (int k = i - 1; k >= 0; k--) {
                check[k][j][1] = false; // Up
                check[k][j][5] = false; // Right
            }
            for (int k = i + 1; k < 10; k++) {
                check[k][j][0] = false; // Down
                check[k][j][5] = false; // Right
            }
            for (int k = j - 1; k >= 0; k--) {
                check[i][k][3] = false; // Left
                check[i][k][5] = false; // Right
            }
        }
    }

    public Tile getRandomTile(ArrayList<Tile> tiles) {
        if (tiles.isEmpty()) {
            return null; // If the list is empty, return null
        }
        Random random = new Random();
        int randomIndex = random.nextInt(tiles.size()); // Generate a random index

        return tiles.get(randomIndex); // Return the tile at the random index
    }
    public ArrayList<Tile> validTiles(Boolean check[][][],int i,int j)
    {
        ArrayList<Tile> tiles=new ArrayList<>();
        for (int k=0;k<7;k++)
        {
            if(check[i][j][k]) {
                switch (k) {
                    case 0 -> tiles.add(new Up("up"));
                    case 1 -> tiles.add(new Down("down"));
                    case 2 -> tiles.add(new Left("left"));
                    case 3 -> tiles.add(new Right("right"));
                    case 4 -> tiles.add(new Blade("blade"));
                    case 5 -> tiles.add(new Rotatory("rotate"));
                    case 6 -> tiles.add(new Wall("wall"));
                    default -> tiles.add(null);
                }
            }
        }
        return tiles;
    }
    public void solve_ableBoardGenerator(int level)
    {
        //0 position for up check
        //1 position for down check
        //2 position for left check
        //3 position for right check
        //4 position for blade check
        //5 position for rotate check
        //6 position for wall check
        Boolean check[][][]=new Boolean[10][10][7];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 7; k++) {
                    // Initialize all positions to true
                    check[i][j][k] = true;
                    // Set positions on the border (position 5) to false
                    if (i == 0 || j == 0 || i == 9 || j == 9) {
                        if (k == 5) {
                            check[i][j][k] = false;
                        }
                    }
                }
            }
        }
        int number=generateNumber(level);
        int checker_if_number_reached=0;
        int i=0;
        int j=0;
        int checker_for_deadlock=0;
        while (checker_if_number_reached<=number)
        {
            Tile tile=getRandomTile(validTiles(check,i,j));
            board[i][j]=tile;
            updateCheckArray(check,i,j,tile);
            ArrayList<Tile> checker_list=validTiles(check,i,j);
            checker_if_number_reached++;
            if(checker_list.size()==1)
            {
                checker_for_deadlock++;
                checker_if_number_reached--;
            }
            //Base Case
            j++;
            if(i==10 && j==10)
            {
                break;
            }
            if(checker_for_deadlock==3)
            {
                break;
            }
            if(j==10)
            {
                j=0;
                i++;
            }
            if(i==10)
            {
                break;
            }
        }
        movesGenerator(level);
    }
    public void printBoard()
    {
        for (int i=0;i<10;i++)
        {
            for (int j=0;j<10;j++)
            {
                if(board[i][j]!=null)
                System.out.print(board[i][j].getName()+"  ");
                else
                    System.out.print("null ");
            }
            System.out.println();
        }
        System.out.println(moves);
    }
    public void printBoard(Tile[][] board_)
    {
        for (int i=0;i<10;i++)
        {
            for (int j=0;j<10;j++)
            {
                if(board_[i][j]!=null)
                    System.out.print(board_[i][j].getName()+"  ");
                else
                    System.out.print("null ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
    public Boolean empty()
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                if(board[i][j] instanceof Up)
                {
                    return false;
                }
                if(board[i][j] instanceof Down)
                {
                    return false;
                }
                if(board[i][j] instanceof Left)
                {
                     return false;
                }
                if(board[i][j] instanceof Right)
                {
                    return false;
                }

            }
        }
        return true;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void movesGenerator(int level)
    {
        int moves_=0;
        Tile [][] board_=deepCopyBoard();
        int check=0;
        while (!empty())
        {
            printBoard(board_);
            for (int i=0;i<10;i++)
            {
                for(int j=0;j<10;j++)
                {
                    if(moveOnGivenBoard(i,j,board_))
                    {
                        moves_++;
                    }
                }
            }
            check++;
            if(check==5)
            {
                break;
            }
            printBoard(board_);
        }
        moves_= moves_+(int)(0.005*moves_);
        if(check==5)
        {
            moves_=moves_+5;
        }
        setMoves(moves_);
    }
    public boolean move(int i,int j)
    {
        boolean check=true;
        if(board[i][j] instanceof Up)
        {
            if(i+1==1)
            {
                board[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=i-1;k>=0;k--)
                {
                    if(board[k][j]!=null) {
                        if(k!=i-1) {
                            if(board[k][j] instanceof Blade)
                            {
                                board[i][j]=null;
                                return true;
                            }
                            board[k+1][j]=new Up("up");
                            board[i][j]=null;
                            return true;
                        }
                        if(board[k][j] instanceof Blade)
                        {
                            board[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board[i][j]=null;
                return true;
            }
        }
        if(board[i][j] instanceof Down)
        {
            if(i+1==10)
            {
                board[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=i+1;k<10;k++)
                {
                    if(board[k][j]!=null) {
                        if(k!=i+1) {
                            if(board[k][j] instanceof Blade)
                            {
                                board[i][j]=null;
                                return true;
                            }
                            board[k-1][j]=new Down("down");
                            board[i][j]=null;
                            return true;
                        }
                        if(board[k][j] instanceof Blade)
                        {
                            board[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board[i][j]=null;
                return true;
            }

        }
        if(board[i][j] instanceof Left)
        {
            if(j+1==1)
            {
                board[i][j]=null;
                return true;
            }
            else
            {
                int k;
                int l=i;
                for (k=j-1;k>=0;k--)
                {
                    if(board[i][k]!=null) {
                        if(k!=j-1) {
                            if(board[i][k] instanceof Blade)
                            {
                                board[i][j]=null;
                                return true;
                            }
                            board[i][k+1]=new Left("left");
                            board[i][j]=null;
                            return true;
                        }
                        if(board[i][k] instanceof Blade)
                        {
                            board[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board[i][j]=null;
                return true;
            }
        }
        if(board[i][j] instanceof Right)
        {
            if(j+1==10)
            {
                board[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=j+1;k<10;k++)
                {
                    if(board[i][k]!=null) {
                        if(k!=j+1) {
                            if(board[i][k] instanceof Blade)
                            {
                                board[i][j]=null;
                                return true;
                            }
                            board[i][k-1]=new Right("right");
                            board[i][j]=null;
                            return true;
                        }
                        if(board[i][k] instanceof Blade)
                        {
                            board[i][j]=null;
                            return true;
                        }

                        return false;
                    }
                }
                board[i][j]=null;
                return true;
            }
        }
        if(board[i][j] instanceof Rotatory)
        {
            if (i < 0 || i >= 10 || j < 0 || j >= 10) {
                return false;
            }
            else
            {
                if (i - 1 >= 0 && i + 1 < 10 && j - 1 >= 0 && j + 1 < 10) {
                    Tile temp = board[i - 1][j];
                    board[i - 1][j] = board[i][j - 1];//Top becomes left
                    Tile temp2 = board[i][j + 1];
                    board[i][j + 1] = temp;//Right becomes Top
                    Tile temp3 = board[i + 1][j];
                    board[i + 1][j] = temp2;//Down becomes Right
                    board[i][j - 1] = temp3;//left becomes Down
                    return true;
                }
                else
                {
                    return false;
                }
            }

        }

        return false;
    }

    public boolean moveOnGivenBoard(int i,int j,Tile[][] board_)
    {
        boolean check=true;
        if(board_[i][j] instanceof Up)
        {
            if(i+1==1)
            {
                board_[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=i-1;k>=0;k--)
                {
                    if(board_[k][j]!=null) {
                        if(k!=i-1) {
                            if(board_[k][j] instanceof Blade)
                            {
                                board_[i][j]=null;
                                return true;
                            }
                            board_[k+1][j]=new Up("up");
                            board_[i][j]=null;
                            return true;
                        }
                        if(board_[k][j] instanceof Blade)
                        {
                            board_[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board_[i][j]=null;
                return true;
            }
        }
        if(board_[i][j] instanceof Down)
        {
            if(i+1==10)
            {
                board_[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=i+1;k<10;k++)
                {
                    if(board_[k][j]!=null) {
                        if(k!=i+1) {
                            if(board_[k][j] instanceof Blade)
                            {
                                board_[i][j]=null;
                                return true;
                            }
                            board_[k-1][j]=new Down("down");
                            board_[i][j]=null;
                            return true;
                        }
                        if(board_[k][j] instanceof Blade)
                        {
                            board_[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board_[i][j]=null;
                return true;
            }

        }
        if(board_[i][j] instanceof Left)
        {
            if(j+1==1)
            {
                board_[i][j]=null;
                return true;
            }
            else
            {
                int k;
                int l=i;
                for (k=j-1;k>=0;k--)
                {
                    if(board_[i][k]!=null) {
                        if(k!=j-1) {
                            if(board_[i][k] instanceof Blade)
                            {
                                board_[i][j]=null;
                                return true;
                            }
                            board_[i][k+1]=new Left("left");
                            board_[i][j]=null;
                            return true;
                        }
                        if(board_[i][k] instanceof Blade)
                        {
                            board_[i][j]=null;
                            return true;
                        }
                        return false;
                    }
                }
                board_[i][j]=null;
                return true;
            }
        }
        if(board_[i][j] instanceof Right)
        {
            if(j+1==10)
            {
                board_[i][j]=null;
                return true;
            }
            else
            {
                int k=0;
                int l=i;
                for (k=j+1;k<10;k++)
                {
                    if(board_[i][k]!=null) {
                        if(k!=j+1) {
                            if(board_[i][k] instanceof Blade)
                            {
                                board_[i][j]=null;
                                return true;
                            }
                            board_[i][k-1]=new Right("right");
                            board_[i][j]=null;
                            return true;
                        }
                        if(board_[i][k] instanceof Blade)
                        {
                            board_[i][j]=null;
                            return true;
                        }

                        return false;
                    }
                }
                board_[i][j]=null;
                return true;
            }
        }
        if(board_[i][j] instanceof Rotatory)
        {
            if (i < 0 || i >= 10 || j < 0 || j >= 10) {
                return false;
            }
            else
            {
                if (i - 1 >= 0 && i + 1 < 10 && j - 1 >= 0 && j + 1 < 10) {
                    Tile temp = board_[i - 1][j];
                    board_[i - 1][j] = board_[i][j - 1];//Top becomes left
                    Tile temp2 = board_[i][j + 1];
                    board_[i][j + 1] = temp;//Right becomes Top
                    Tile temp3 = board_[i + 1][j];
                    board_[i + 1][j] = temp2;//Down becomes Right
                    board_[i][j - 1] = temp3;//left becomes Down
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

}
