package checkers;

public class Piece {
    public String placeholder;
    public int value;
    public boolean isKing;

    // Constructor
    public Piece(String string, int _value) {
        placeholder = string;
        value = _value;
        isKing = false;
    }
}
