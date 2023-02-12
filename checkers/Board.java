package checkers;

public class Board {
    public int[][] board = new int[8][8];
    public static final String[] ROWS = {"A", "B", "C", "D", "E", "F", "G", "H"};
    public static final int[] COLUMNS = {1, 2, 3, 4, 5, 6, 7, 8};

    // Constructor
    public Board() {
        fillBlacks();
        fillWhites();
    }

    // Fill the bottom of the playing board with black pieces (1s)
    public int[][] fillBlacks() {
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j % 2 == 0) {
                    if (i == 6) {
                        board[i][j + 1] = 1;
                    } else {
                        board[i][j] = 1;
                    }
                }
            }
        }
        return board;
    }

    // Fill the top of the playing board with white pieces (2s)
    public int[][] fillWhites() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j % 2 == 0) {
                    if (i == 1) {
                        board[i][j] = 2;
                    } else {
                        board[i][j + 1] = 2;
                    }
                }
            }
        }
        return board;
    }

    // Print the board
    public void print() {
        // Print the column numbers
        System.out.print("\t");
        for (int i = 0; i < COLUMNS.length; i++) {
            System.out.print(COLUMNS[i] + " ");
        }
        System.out.println("\n");

        // Print the board
        for (int i = 0; i < board.length; i++) {
            System.out.print(ROWS[i] + "\t");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\t" + ROWS[i]);
            System.out.println();
        }
        System.out.println();
    }
}
