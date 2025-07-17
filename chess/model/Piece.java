package chess.model;

import java.util.List;

import chess.enums.Color;
import chess.enums.PieceType;

public abstract class Piece {
    protected Color color;
    protected PieceType type;
    protected boolean hasMoved;

    public Piece(Color c, PieceType t) {
        color = c;
        type = t;
        hasMoved = false;
    }
    
    public Color getColor() { 
        return color; 
    }
    public PieceType getType() { 
        return type; 
    }
    public boolean getHasMoved() { 
        return hasMoved; 
    }
    public void setMoved(boolean moved) { 
        hasMoved = moved; 
    }
    
    public abstract List<Position> getPossibleMoves(Position currentPos, Board board);
    public abstract String getSymbol();
    
    public String toString() {
        String colorStr = (color == Color.WHITE) ? "W" : "B";
        return colorStr + getSymbol();
    }
}
