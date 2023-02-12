package checkers;
import java.util.Arrays;

public class Move {
    public boolean canMoveLeft;
    public boolean canMoveRight;
    public int startRow;
    public int startColumn;
    public int endRow;
    public int endColumn;
    public int startingPieceValue;
    public int endingPieceValue;
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

        // Can the pieces move left and right?
        canMoveLeft = (startColumn != 1 && endColumn == startColumn - 1) || (startColumn != 2 && endColumn == startColumn - 2);
        canMoveRight = (startColumn != 8 && endColumn == startColumn + 1) || (startColumn != 7 && endColumn == startColumn + 2);

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
    public int[][] move(int player) {
        if (startingPieceValue != player) {
            System.out.println("It's " + (player == 1 ? "black" : "white") + "'s turn!");
            return board;
        }

        // Move the piece by 1 space
        if (jump(1, false)) {
            return board;
        }

        // Store the middle row of a two space jump
        int[] middleRow;

        // If moving left and black piece
        if (startingPieceValue == 1 && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleRow = Arrays.copyOfRange(board[startRow - 1], endColumn - 1, startColumn);
        } 

        // If moving left and white piece
        else if (startingPieceValue == 2 && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleRow = Arrays.copyOfRange(board[startRow + 1], endColumn - 1, startColumn);
        }
        
        // If moving right and black piece
        else if (startingPieceValue == 1 && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleRow = Arrays.copyOfRange(board[startRow - 1], startColumn - 1, endColumn);
        }

        // If moving right and white piece
        else if (startingPieceValue == 2 && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middleRow = Arrays.copyOfRange(board[startRow + 1], startColumn - 1, endColumn);
        }

        // Else, return the board
        else {
            System.out.println("You can't move that way!");
            return board;
        }
        
        // If there's an opposite piece in the middle of the row
        if (middleRow[1] == startingPieceValue) {
            System.out.println("You can't jump over your own piece!");
            return board;
        }

        // Else, move the piece by 2 and take the middle piece
        jump(2, true);
        return board;
    }

    // Move the piece
    private boolean jump(int jumpSpaces, boolean takePiece) {
        // If canMoveLeft and is moving left...
        if (canMoveLeft && endColumn == startColumn - jumpSpaces && (endRow == startRow - jumpSpaces || endRow == startRow + jumpSpaces)) {
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

            // Return whether moved
            return true;
        }

        // If canMoveRight and is moving right...
        else if (canMoveRight && endColumn == startColumn + jumpSpaces && (endRow == startRow + jumpSpaces || endRow == startRow - jumpSpaces)) {
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

            // Return whether moved
            return true;
        }
        return false;
    }
}
