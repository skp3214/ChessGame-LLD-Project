package chess.rules;

import chess.enums.Color;
import chess.model.Board;
import chess.model.Move;

public interface ChessRules {
    boolean isValidMove(Move move, Board board);
    boolean isInCheck(Color color, Board board);
    boolean isCheckmate(Color color, Board board);
    boolean isStalemate(Color color, Board board);
    boolean wouldMoveCauseCheck(Move move, Board board, Color kingColor);
}
