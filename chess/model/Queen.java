package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.enums.Color;
import chess.enums.PieceType;

public class Queen extends Piece {
    public Queen(Color color) { 
        super(color, PieceType.QUEEN); 
    }
    
    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        
        for (int d = 0; d < 8; d++) {
            for (int i = 1; i < 8; i++) {
                Position newPos = new Position(currentPos.getRow() + directions[d][0]*i, currentPos.getCol() + directions[d][1]*i);
                if (!newPos.isValid()) break;

                if (board.isOccupiedBySameColor(newPos, this.color)) break;

                moves.add(newPos);
                if (board.isOccupied(newPos)) break; // Stop after capturing
            }
        }
        return moves;
    }
    
    @Override
    public String getSymbol() { 
        return "Q"; 
    }
}
