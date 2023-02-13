package checkers;
import java.util.Arrays;

public class Move {
    // Positions
    public int startRow;
    public int startColumn;
    public int endRow;
    public int endColumn;

    // Pieces
    public Piece startingPiece;
    public Piece endingPiece;

    // Board
    Piece[][] board;

    // Constructor
    public Move(String move, Piece[][] _board) {
        board = _board;

        // Check if the move is in the correct format
        if (!move.matches("[A-H]-[1-8] > [A-H]-[1-8]")) {
            System.out.println("\nInvalid move format!\n");
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
        startingPiece = board[startRow][startColumn - 1];
        endingPiece = board[endRow][endColumn - 1];
    }


    // Check if the move is valid
    public boolean isValid(int player) {
        // Check if the starting piece is null
        if (startingPiece == null) {
            System.out.println("\nInvalid Move!\n");
            return false;
        }

        // Check if the starting piece is a 0
        if (startingPiece.value == 0) {
            System.out.println("\nThere is no piece at that location!\n");
            return false;
        }

        // Check player's turn
        if (startingPiece.value != player) {
            System.out.println("It's " + (player == 1 ? "black" : "white") + "'s turn!");
            return false;
        }


        // Check if the ending piece is a 0
        if (endingPiece.value != 0) {
            System.out.println("\nThere is already a piece at that location!\n");
            return false;
        }

        // Check if the piece is moving forward (for white pieces)
        if (startingPiece.value == 2 && !startingPiece.isCrowned && endRow < startRow) {
            System.out.println("\nYou can't move backwards!\n");
            return false;
        }

        // Check if the piece is moving forward (for black pieces)
        if (startingPiece.value == 1 && !startingPiece.isCrowned && endRow > startRow) {
            System.out.println("\nYou can't move backwards!\n");
            return false;
        }

        // Check if trying to move to the same row
        if (endRow == startRow) {
            System.out.println("\nYou can't move that way!\n");
            return false;
        }
        return true;
    }

    // Function to move the piece
    public boolean move() {
        // Move the piece by 1 space
        if (jump(1, false)) {
            return true;
        }

        // Store the middle row of a two space jump
        Piece middlePiece;

        // If moving left and black piece
        if (endRow < startRow && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middlePiece = board[startRow - 1][startColumn - 2];
        } 

        // If moving left and white piece
        else if (endRow > startRow && endColumn == startColumn - 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middlePiece = board[startRow + 1][startColumn - 2];
        }
        
        // If moving right and black piece
        else if (endRow < startRow && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middlePiece = board[startRow - 1][startColumn];
        }

        // If moving right and white piece
        else if (endRow > startRow && endColumn == startColumn + 2 && (endRow == startRow + 2 || endRow == startRow - 2)) {
            middlePiece = board[startRow + 1][startColumn];
        }

        // Else, return the board
        else {
            System.out.println("\nYou can't move that way!\n");
            return false;
        }
        
        // If there's the same piece in the middle of the jump
        if (middlePiece.value == startingPiece.value) {
            System.out.println("\nYou can't jump over your own piece!\n");
            return false;
        }

        // Else, move the piece by 2 and take the middle piece
        return jump(2, true);
    }

    // Function to check if the piece is a king
    private void checkKing() {
        // If the white piece made it to the other side of the board
        if (endRow == 7 && startingPiece.value == 2) {
            startingPiece.isCrowned = true;
            startingPiece.placeholder = "Q";
        }

        // If the black piece made it to the other side of the board
        else if (endRow == 0 && startingPiece.value == 1) {
            startingPiece.isCrowned = true;
            startingPiece.placeholder = "K";
        }
    }

    // Move the piece
    private boolean jump(int jumpSpaces, boolean takePiece) {
        // If moving left...
        if (endColumn == startColumn - jumpSpaces && (endRow == startRow - jumpSpaces || endRow == startRow + jumpSpaces)) {
            // endingPieceValue = startingPieceValue;
            board[endRow][endColumn - 1] = startingPiece;

            // Set the starting piece value to 0
            board[startRow][startColumn - 1] = new Piece("■", 0);

            // If taking a piece for white
            if (takePiece && endRow > startRow) {
                board[startRow + 1][startColumn - 2] = new Piece("■", 0);
            } 

            // If taking a piece for black
            else if (takePiece && endRow < startRow) {
                board[startRow - 1][startColumn - 2] = new Piece("■", 0);
            }

            // Check if the piece will become a king
            checkKing();

            // Then return true
            return true;
        }

        // If moving right...
        else if (endColumn == startColumn + jumpSpaces && (endRow == startRow + jumpSpaces || endRow == startRow - jumpSpaces)) {
            // endingPieceValue = startingPieceValue;
            board[endRow][endColumn - 1] = startingPiece;

            // Set the starting piece value to 0
            board[startRow][startColumn - 1] = new Piece("■", 0);

            // If taking a piece for white
            if (takePiece && endRow > startRow) {
                board[startRow + 1][startColumn] = new Piece("■", 0);
            }

            // If taking a piece for black
            else if (takePiece && endRow < startRow) {
                board[startRow - 1][startColumn] = new Piece("■", 0);
            }

            // Check if the piece will become a king
            checkKing();

            // Then return true
            return true;
        }

        // Check if the piece will become a king
        checkKing();

        // Then return false
        return false;
    }
}
