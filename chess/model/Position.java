package chess.model;

import java.util.Objects;

public class Position {
    private int row;
    private int col;

    public Position() {
        row = 0;
        col = 0;
    }
    
    public Position(int r, int c) {
        row = r;
        col = c;
    }
    
    public int getRow() { 
        return row; 
    }
    public int getCol() { 
        return col; 
    }
    
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return row == other.row && col == other.col;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    public int compareTo(Position other) {
        if (row != other.row) return Integer.compare(row, other.row);
        return Integer.compare(col, other.col);
    }
    
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
    
    // Convert to chess notation (e.g., e4, f7)
    public String toChessNotation() {
        char file = (char)('a' + col);
        char rank = (char)('8' - row);
        return "" + file + rank;
    }
}
