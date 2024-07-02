// A class to represent a game of ConnectFour that implements the 
// AbstractStrategyGame interface.

import java.util.*;

public class ConnectFour implements AbstractStrategyGame {
    private char[][] board;
    private boolean isRedTurn;

    // Constructs a new ConnectFour game.
    public ConnectFour() {
        board = new char[][]{{'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'}};
        isRedTurn = true;
    }

    // Returns whether or not the game is over.
    public boolean isGameOver() {
        return getWinner() >= 0;
    }

    // Returns the index of the winner of the game.
    // 1 if player 1 (R), 2 if player 2 (Y), 0 if a tie occurred,
    // and -1 if the game is not over.
    public int getWinner() {
        // check row i
        for (int i = 0; i < board.length; i++) {
            int tempCol = 0;
            while (tempCol < board[0].length - 3) {
                if (board[i][tempCol] == board[i][tempCol + 1] &&
                    board[i][tempCol] == board[i][tempCol + 2] &&
                    board[i][tempCol] == board[i][tempCol + 3] && board[i][tempCol] != '-') {
                    return board[i][tempCol] == 'R' ? 1 : 2;
                }
                tempCol++;
            }
        }

        // check col i
        for (int i = 0; i < board[0].length; i++) {
            int tempRow = 0;
            while (tempRow < board.length - 3) {
                if (board[tempRow][i] == board[tempRow + 1][i] &&
                    board[tempRow][i] == board[tempRow + 2][i] && 
                    board[tempRow][i] == board[tempRow + 3][i] && board[tempRow][i] != '-') {
                    return board[tempRow][i] == 'R' ? 1 : 2;
                }
                tempRow++;
            }
        }

        // check diagonals
        for (int i = 0; i < board.length - 3; i++) {
            int tempCol = 0;
            int tempCol2 = 6;
            while(tempCol < board[0].length - 3) {
                if (board[i][tempCol] == board[i + 1][tempCol + 1] && 
                    board[i][tempCol] == board[i + 2][tempCol + 2] && 
                    board[i][tempCol] == board[i + 3][tempCol + 3] && board[i][tempCol] != '-') {
                    return board[i][tempCol] == 'R' ? 1 : 2;
                }
                tempCol++;
            }
            while(tempCol2 >= board[0].length - 4) {
                if (board[i][tempCol2] == board[i + 1][tempCol2 - 1] &&
                    board[i][tempCol2] == board[i + 2][tempCol2 - 2] &&
                    board[i][tempCol2] == board[i + 3][tempCol2 - 3] && board[i][tempCol2] != '-') {
                    return board[i][tempCol2] == 'R' ? 1 : 2;
                }
                tempCol2--;
            }
        }

        // check for tie
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    // unfilled space; game not over
                    return -1;
                }
            }
        }
        // it's a tie!
        return 0;
    }

    // Returns the index of which player's turn it is.
    // 1 if player 1 (R), 2 if player 2 (Y), -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        if (isRedTurn) {
            return 1;
        }
        return 2;
    }
    
    // Given the input, places a R or a Y where
    // the player specifies.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [5, 6] for both rows and cols.
    public void makeMove(Scanner input) {
        char currPlayer = isRedTurn ? 'R' : 'Y';

        System.out.print("Column? ");
        int col = input.nextInt();

        makeMove(col, currPlayer);
        isRedTurn = !isRedTurn;
    }

    // Private helper method for makeMove.
    // Given a col, as well as player index,
    // places a R or a Y in that col with the next empty row.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds, already occupied, or the column is all filled.
    // Board bounds are [5, 6] for both rows and cols.
    private void makeMove(int col, char player) {
        if (col < 0 || col >= board[0].length) {
                throw new IllegalArgumentException("Invalid board position at column: " + col);
        }

        
        int usedRow = board.length;
        for(int i = board.length - 1; i >= 0; i--) {
            if(board[i][col] != '-') {
                usedRow = i;
            }
        }

        if(usedRow == 0) {
            throw new IllegalArgumentException("This column is already used: " + col);
        }

        board[usedRow - 1][col] = player;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is Red(R) and goes first. Choose where to play by entering a row and\n";
        result += "column number, where (0, 0) is the upper left and (5, 6) is the lower right.\n";
        result += "Spaces show as a - are empty. The game ends when one player marks four spaces\n";
        result += "in a row, in which case that player wins, or when the board is full, in which\n";
        result += "case the game ends in a tie.";
        return result;
    }

    // Returns a String representation of the current state of the board.
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }
}
