package checkers;
import java.util.Arrays;

public class Move {
    // Positions
    public int startRow;
    public int startColumn;
    public int endRow;
    public int endColumn;

    // Piece Values
    public int startingPieceValue;
    public int endingPieceValue;

    // Board
    int[][] board;

    // Constructor
    public Move(String move, int[][] _board) {
        board = _board;

        // Check if the move is in the correct format
        if (!move.matches("[A-H]-[1-8] > [A-H]-[1-8]")) {
            System.out.println("Invalid move format!");
            return;
        }

        // Split the move by > and then by -
        String[] splitMove = move.split(" > ");
        String[] start = splitMove[0].split("-");
        String[] end = splitMove[1].split("-");

        // Get the starting row and column of the piece
        startRow = Arrays.asList(Board.ROWS).indexOf(start[0]);
        startColumn = Integer.parseInt(start[1]);

        // Get the ending row and column of the piece
        endRow = Arrays.asList(Board.ROWS).indexOf(end[0]);
        endColumn = Integer.parseInt(end[1]);

        // Get the starting and ending piece values
        startingPieceValue = board[startRow][startColumn - 1];
        endingPieceValue = board[endRow][endColumn - 1];
    }


    // Check if the move is valid
    public boolean isValid() {
        // Check if the starting piece is a 0
        if (startingPieceValue == 0) {
            System.out.println("There is no piece at that location!");
            return false;
        }

        // Check if the ending piece is a 0
        if (endingPieceValue != 0) {
            System.out.println("There is already a piece at that location!");
            return false;
        }

        // Check if the piece is moving forward (for white pieces)
        if (startingPieceValue == 2 && endRow < startRow) {
            System.out.println("You can't move backwards!");
            return false;
        }

        // Check if the piece is moving forward (for black pieces)
        if (startingPieceValue == 1 && endRow > startRow) {
            System.out.println("You can't move backwards!");
            return false;
        }

        // Check if trying to move to the same row
        if (endRow == startRow) {
            System.out.println("You can't move that way!");
            return false;
        }
        return true;
    }

    // Function to move the piece
    public boolean move(int player) {
        if (startingPieceValue != player) {
            System.out.println("It's " + (player == 1 ? "black" : "white") + "'s turn!");
            return false;
        }

        // Move the piece by 1 space
        if (jump(1, false)) {
            return true;
        }

        // Store the middle row of a two space jump
        int middleValue;

        // If moving left and black piece
        if (startingPieceValue == 1 && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleValue = board[startRow - 1][startColumn - 2];
        } 

        // If moving left and white piece
        else if (startingPieceValue == 2 && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleValue = board[startRow + 1][startColumn - 2];
        }
        
        // If moving right and black piece
        else if (startingPieceValue == 1 && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleValue = board[startRow - 1][startColumn];
        }

        // If moving right and white piece
        else if (startingPieceValue == 2 && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleValue = board[startRow + 1][startColumn];
        }

        // Else, return the board
        else {
            System.out.println("You can't move that way!");
            return false;
        }
        
        // If there's an opposite piece in the middle of the row
        if (middleValue == startingPieceValue) {
            System.out.println("You can't jump over your own piece!");
            return false;
        }

        // Else, move the piece by 2 and take the middle piece
        return jump(2, true);
    }

    // Move the piece
    private boolean jump(int jumpSpaces, boolean takePiece) {
        // If moving left...
        if (endColumn == startColumn - jumpSpaces && (endRow == startRow - jumpSpaces || endRow == startRow + jumpSpaces)) {
            // endingPieceValue = startingPieceValue;
            board[endRow][endColumn - 1] = startingPieceValue;

            // Set the starting piece value to 0
            board[startRow][startColumn - 1] = 0;

            // If taking a piece for white
            if (takePiece && startingPieceValue == 2) {
                board[startRow + 1][startColumn - 2] = 0;
            } 

            // If taking a piece for black
            else if (takePiece && startingPieceValue == 1) {
                board[startRow - 1][startColumn - 2] = 0;
            }
            return true;
        }

        // If moving right...
        else if (endColumn == startColumn + jumpSpaces && (endRow == startRow + jumpSpaces || endRow == startRow - jumpSpaces)) {
            // endingPieceValue = startingPieceValue;
            board[endRow][endColumn - 1] = startingPieceValue;

            // Set the starting piece value to 0
            board[startRow][startColumn - 1] = 0;

            // If taking a piece for white
            if (takePiece && startingPieceValue == 2) {
                board[startRow + 1][startColumn] = 0;
            }

            // If taking a piece for black
            else if (takePiece && startingPieceValue == 1) {
                board[startRow - 1][startColumn] = 0;
            }
            return true;
        }
        return false;
    }
}
