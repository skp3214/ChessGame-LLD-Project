package chess.factory;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Bishop;
import chess.model.King;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.Piece;
import chess.model.Queen;
import chess.model.Rook;

public class PieceFactory {
    public static Piece createPiece(PieceType type, Color color) {
        switch (type) {
            case KING: return new King(color);
            case QUEEN: return new Queen(color);
            case ROOK: return new Rook(color);
            case BISHOP: return new Bishop(color);
            case KNIGHT: return new Knight(color);
            case PAWN: return new Pawn(color);
            default: return null;
        }
    }
}
