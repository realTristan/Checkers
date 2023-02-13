package checkers;

public class Piece {
    public String placeholder;
    public int value;
    public boolean isKing;

    // Constructor
    public Piece(String _placeholder, int _value) {
        placeholder = _placeholder;
        value = _value;
        isKing = false;
    }
}
