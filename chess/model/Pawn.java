package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.enums.Color;
import chess.enums.PieceType;

public class Pawn extends Piece {
    public Pawn(Color color) { 
        super(color, PieceType.PAWN); 
    }
    
    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? -1 : 1;
        
        // Forward move
        Position oneStep = new Position(currentPos.getRow() + direction, currentPos.getCol());
        if (oneStep.isValid() && !board.isOccupied(oneStep)) {
            moves.add(oneStep);
            
            // Double move from starting position
            if (!hasMoved) {
                Position twoStep = new Position(currentPos.getRow() + 2*direction, currentPos.getCol());
                if (twoStep.isValid() && !board.isOccupied(twoStep)) {
                    moves.add(twoStep);
                }
            }
        }
        
        // Diagonal captures
        Position leftCapture = new Position(currentPos.getRow() + direction, currentPos.getCol() - 1);
        Position rightCapture = new Position(currentPos.getRow() + direction, currentPos.getCol() + 1);
        
        if (leftCapture.isValid() && board.isOccupied(leftCapture) && 
            !board.isOccupiedBySameColor(leftCapture, this.color)) {
            moves.add(leftCapture);
        }
        
        if (rightCapture.isValid() && board.isOccupied(rightCapture) && 
            !board.isOccupiedBySameColor(rightCapture, this.color)) {
            moves.add(rightCapture);
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() { 
        return "P"; 
    }
}

