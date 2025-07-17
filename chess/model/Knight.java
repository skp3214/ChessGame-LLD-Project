package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.enums.Color;
import chess.enums.PieceType;

public class Knight extends Piece {
    public Knight(Color color) { 
        super(color, PieceType.KNIGHT); 
    }
    
    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] knightMoves = {{-2,-1}, {-2,1}, {-1,-2}, {-1,2}, {1,-2}, {1,2}, {2,-1}, {2,1}};
        
        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(currentPos.getRow() + knightMoves[i][0], currentPos.getCol() + knightMoves[i][1]);
            if (newPos.isValid() && !board.isOccupiedBySameColor(newPos, this.color)) {
                moves.add(newPos);
            }
        }
        return moves;
    }
    
    @Override
    public String getSymbol() { 
        return "N"; 
    }
}
