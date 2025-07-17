package chess.model;

public class Move {
    private Position from;
    private Position to;
    private Piece piece;
    private Piece capturedPiece;

    public Move() {
        piece = null;
        capturedPiece = null;
    }
    
    public Move(Position f, Position t, Piece p, Piece captured) {
        from = f;
        to = t;
        piece = p;
        capturedPiece = captured;
    }
    
    public Position getFrom() { 
        return from; 
    }
    public Position getTo() { 
        return to; 
    }
    public Piece getPiece() { 
        return piece; 
    }
    public Piece getCapturedPiece() { 
        return capturedPiece; 
    }
}
