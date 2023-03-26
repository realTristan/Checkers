package checkers;

public class Board {
    public Piece[][] board = new Piece[8][8];
    public static final String[] ROWS = {"A", "B", "C", "D", "E", "F", "G", "H"};

    // Constructor
    public Board() {
        fillBase();
        fillBlacks();
        fillWhites();
    }

    // Fill the board with base squares
    private void fillBase() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Piece("■", 0);
                if (j % 2 == 0) {
                    if (i % 2 == 0) {
                        board[i][j].placeholder = " ";
                    }
                } else {
                    if (i % 2 != 0) {
                        board[i][j].placeholder = " ";
                    }
                }
            }
        }
    }

    // Fill the bottom of the playing board with Black pieces (1s)
    private void fillBlacks() {
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j % 2 == 0) {
                    if (i == 6) {
                        board[i][j + 1] = new Piece("1", 1);
                    } else {
                        board[i][j] = new Piece("1", 1);
                    }
                }
            }
        }
    }

    // Fill the top of the playing board with White pieces (2s)
    private void fillWhites() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j % 2 == 0) {
                    if (i == 1) {
                        board[i][j] = new Piece("2", 2);
                    } else {
                        board[i][j + 1] = new Piece("2", 2);
                    }
                }
            }
        }
    }

    // Print the board
    public void print() {
        // Print the column numbers
        System.out.print("\t");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println("\n");
 
        // Print the board
        for (int i = 0; i < board.length; i++) {
            System.out.print(ROWS[i] + "\t");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j].placeholder + " ");
            }
            System.out.print("\t" + ROWS[i]);
            System.out.println();
        }
        System.out.println();
    }
}
