package chess.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.factory.PieceFactory;

public class Board {
    private Piece[][] board;
    private Map<Position, Piece> piecePositions;

    public Board() {
        // Initialize board to null
        board = new Piece[8][8];
        piecePositions = new HashMap<>();
        initializeBoard();
    }
    
    public void initializeBoard() {
        // Initialize white pieces
        placePiece(new Position(7, 0), PieceFactory.createPiece(PieceType.ROOK, Color.WHITE));
        placePiece(new Position(7, 1), PieceFactory.createPiece(PieceType.KNIGHT, Color.WHITE));
        placePiece(new Position(7, 2), PieceFactory.createPiece(PieceType.BISHOP, Color.WHITE));
        placePiece(new Position(7, 3), PieceFactory.createPiece(PieceType.QUEEN, Color.WHITE));
        placePiece(new Position(7, 4), PieceFactory.createPiece(PieceType.KING, Color.WHITE));
        placePiece(new Position(7, 5), PieceFactory.createPiece(PieceType.BISHOP, Color.WHITE));
        placePiece(new Position(7, 6), PieceFactory.createPiece(PieceType.KNIGHT, Color.WHITE));
        placePiece(new Position(7, 7), PieceFactory.createPiece(PieceType.ROOK, Color.WHITE));
        
        for (int i = 0; i < 8; i++) {
            placePiece(new Position(6, i), PieceFactory.createPiece(PieceType.PAWN, Color.WHITE));
        }
        
        // Initialize black pieces
        placePiece(new Position(0, 0), PieceFactory.createPiece(PieceType.ROOK, Color.BLACK));
        placePiece(new Position(0, 1), PieceFactory.createPiece(PieceType.KNIGHT, Color.BLACK));
        placePiece(new Position(0, 2), PieceFactory.createPiece(PieceType.BISHOP, Color.BLACK));
        placePiece(new Position(0, 3), PieceFactory.createPiece(PieceType.QUEEN, Color.BLACK));
        placePiece(new Position(0, 4), PieceFactory.createPiece(PieceType.KING, Color.BLACK));
        placePiece(new Position(0, 5), PieceFactory.createPiece(PieceType.BISHOP, Color.BLACK));
        placePiece(new Position(0, 6), PieceFactory.createPiece(PieceType.KNIGHT, Color.BLACK));
        placePiece(new Position(0, 7), PieceFactory.createPiece(PieceType.ROOK, Color.BLACK));
        
        for (int i = 0; i < 8; i++) {
            placePiece(new Position(1, i), PieceFactory.createPiece(PieceType.PAWN, Color.BLACK));
        }
    }
    
    public void placePiece(Position pos, Piece piece) {
        board[pos.getRow()][pos.getCol()] = piece;
        piecePositions.put(pos, piece);
    }
    
    public void removePiece(Position pos) {
        board[pos.getRow()][pos.getCol()] = null;
        piecePositions.remove(pos);
    }
    
    public Piece getPiece(Position pos) {
        return board[pos.getRow()][pos.getCol()];
    }
    
    public boolean isOccupied(Position pos) {
        return getPiece(pos) != null;
    }
    
    public boolean isOccupiedBySameColor(Position pos, Color color) {
        Piece piece = getPiece(pos);
        return piece != null && piece.getColor() == color;
    }
    
    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece != null) {
            // Remove captured piece if any
            Piece capturedPiece = getPiece(to);
            if (capturedPiece != null) {
                piecePositions.remove(to);
            }
            
            // Move the piece
            board[from.getRow()][from.getCol()] = null;
            board[to.getRow()][to.getCol()] = piece;
            
            // Update piece positions map
            piecePositions.remove(from);
            piecePositions.put(to, piece);
            
            piece.setMoved(true);
        }
    }
    
    public Position findKing(Color color) {
        for (Map.Entry<Position, Piece> entry : piecePositions.entrySet()) {
            if (entry.getValue().getType() == PieceType.KING && entry.getValue().getColor() == color) {
                return entry.getKey();
            }
        }
        return new Position(-1, -1); // Invalid position if not found
    }
    
    public List<Position> getAllPiecesOfColor(Color color) {
        List<Position> pieces = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : piecePositions.entrySet()) {
            if (entry.getValue().getColor() == color) {
                pieces.add(entry.getKey());
            }
        }
        return pieces;
    }

    public void display() {
        final int cellW = 3;  // cell width

        // — horizontal border —
        Runnable printBorder = () -> {
            System.out.print("  +");
            for (int i = 0; i < 8; ++i)
                System.out.print("-".repeat(cellW) + "+");
            System.out.println();
        };

        // — top border —
        printBorder.run();

        // — column labels inside the grid —
        System.out.print("  |");
        for (char f = 'a'; f <= 'h'; ++f) {
            int pad = (cellW - 1) / 2;
            System.out.print(" ".repeat(pad) + f + " ".repeat(cellW - 1 - pad) + "|");
        }
        System.out.println();

        // — border under labels —
        printBorder.run();

        // — each rank of pieces —
        for (int rank = 8; rank >= 1; --rank) {
            int row = 8 - rank;
            System.out.print(rank + " |");

            for (int file = 0; file < 8; ++file) {
                Piece p = board[row][file];
                String s = p != null ? p.toString() : "  ";  // two spaces if empty

                // center a 2-char string in cellW
                int pad = (cellW - 2) / 2;
                System.out.print(" ".repeat(pad) + s + " ".repeat(cellW - 2 - pad) + "|");
            }

            System.out.println(" " + rank);
            printBorder.run();
        }

        // — bottom labels inside the grid —
        System.out.print("  |");
        for (char f = 'a'; f <= 'h'; ++f) {
            int pad = (cellW - 1) / 2;
            System.out.print(" ".repeat(pad) + f + " ".repeat(cellW - 1 - pad) + "|");
        }
        System.out.println();

        // — final border —
        printBorder.run();
    }
}

