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
            // Get the move from the user
            System.out.print((turnSwaps % 2 == 0 ? "[Black (1)]" : "[White (2)]") + " Enter a move (e.g F-1 > E-2): ");
            Move move = new Move(sc.nextLine(), board);

            // Clear the terminal
            System.out.print("\033\143");

            // Check if the move is valid
            if (move.isValid(turnSwaps % 2 == 0 ? 1 : 2)) {
                if (move.move()) {
                    turnSwaps++;
                }
            }
            
            // Print the board
            board.print();
        }
    }
}