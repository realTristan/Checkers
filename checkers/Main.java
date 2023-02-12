package checkers;
import java.util.Scanner;

class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Create a new board
        Board board = new Board();
        board.print();

        // Ask the user to enter a move
        int turnSwaps = 0;
        while (true) {
            if (turnSwaps % 2 == 0) {
                System.out.print("(Black) ");
            } else {
                System.out.print("(White) ");
            }
            System.out.print("Enter a move (e.g C-2 > D-3): ");
            Move move = new Move(sc.nextLine(), board.board);
            if (move.isValid()) {
                move.move(turnSwaps % 2 == 0 ? 1 : 2);
                turnSwaps++;
            }
            
            // Print the board
            board.print();
        }
    }
}