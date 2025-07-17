package chess.model;

import java.util.ArrayList;
import java.util.List;

import chess.enums.Color;
import chess.enums.PieceType;

public class King extends Piece {
    public King(Color color) { 
        super(color, PieceType.KING); 
    }
    
    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        
        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(currentPos.getRow() + directions[i][0], currentPos.getCol() + directions[i][1]);
            if (newPos.isValid() && !board.isOccupiedBySameColor(newPos, this.color)) {
                moves.add(newPos);
            }
        }
        return moves;
    }
    
    @Override
    public String getSymbol() { 
        return "K"; 
    }
}
