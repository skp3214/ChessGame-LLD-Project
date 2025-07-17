package chess.rules;

import java.util.List;

import chess.enums.Color;
import chess.model.Board;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Position;

public class StandardChessRules implements ChessRules {
    @Override
    public boolean isValidMove(Move move, Board board) {
        Piece piece = move.getPiece();
        List<Position> possibleMoves = piece.getPossibleMoves(move.getFrom(), board);
        
        // Check if the target position is in possible moves
        boolean validDestination = false;
        for (Position pos : possibleMoves) {
            if (pos.equals(move.getTo())) {
                validDestination = true;
                break;
            }
        }
        
        if (!validDestination) {
            return false;
        }
        
        // Check if move would put own king in check
        return !wouldMoveCauseCheck(move, board, piece.getColor());
    }
    
    @Override
    public boolean wouldMoveCauseCheck(Move move, Board board, Color kingColor) {
        // Create a temporary copy to simulate the move safely
        Piece movingPiece = board.getPiece(move.getFrom());
        Piece capturedPiece = board.getPiece(move.getTo());
        
        if (movingPiece == null) return true; // Invalid move
        
        // Temporarily execute the move
        board.removePiece(move.getFrom());
        if (capturedPiece != null) {
            board.removePiece(move.getTo());
        }
        board.placePiece(move.getTo(), movingPiece);
        
        // Check if king is in check after the move
        boolean inCheck = isInCheck(kingColor, board);
        
        // Undo the move
        board.removePiece(move.getTo());
        board.placePiece(move.getFrom(), movingPiece);
        if (capturedPiece != null) {
            board.placePiece(move.getTo(), capturedPiece);
        }
        
        return inCheck;
    }
    
    @Override
    public boolean isInCheck(Color color, Board board) {
        Position kingPos = board.findKing(color);
        if (kingPos.getRow() == -1) return false; // King not found
        
        Color opponentColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        List<Position> opponentPieces = board.getAllPiecesOfColor(opponentColor);
        
        for (Position pos : opponentPieces) {
            Piece piece = board.getPiece(pos);
            List<Position> moves = piece.getPossibleMoves(pos, board);
            for (Position targetPos : moves) {
                if (targetPos.equals(kingPos)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isCheckmate(Color color, Board board) {
        if (!isInCheck(color, board)) return false;
        
        List<Position> pieces = board.getAllPiecesOfColor(color);
        for (Position pos : pieces) {
            Piece piece = board.getPiece(pos);
            List<Position> moves = piece.getPossibleMoves(pos, board);
            
            for (Position targetPos : moves) {
                Move move = new Move(pos, targetPos, piece, board.getPiece(targetPos));
                if (isValidMove(move, board)) {
                    return false; // Found a valid move, not checkmate
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean isStalemate(Color color, Board board) {
        if (isInCheck(color, board)) return false;
        
        List<Position> pieces = board.getAllPiecesOfColor(color);
        for (Position pos : pieces) {
            Piece piece = board.getPiece(pos);
            List<Position> moves = piece.getPossibleMoves(pos, board);
            
            for (Position targetPos : moves) {
                Move move = new Move(pos, targetPos, piece, board.getPiece(targetPos));
                if (isValidMove(move, board)) {
                    return false; // Found a valid move, not stalemate
                }
            }
        }
        return true;
    }
}

