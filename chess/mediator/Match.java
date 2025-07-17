package chess.mediator;

import java.util.ArrayList;
import java.util.List;

import chess.enums.Color;
import chess.enums.GameStatus;
import chess.model.Board;
import chess.model.Message;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Position;
import chess.rules.ChessRules;
import chess.rules.StandardChessRules;

public class Match implements ChatMediator {
    private String matchId;
    private User whitePlayer;
    private User blackPlayer;
    private Board board;
    private ChessRules rules;
    private Color currentTurn;
    private GameStatus status;
    private List<Move> moveHistory;
    private List<Message> chatHistory;

    public Match(String mId, User white, User black) {
        matchId = mId;
        whitePlayer = white;
        blackPlayer = black;
        board = new Board();
        rules = new StandardChessRules();
        currentTurn = Color.WHITE;
        status = GameStatus.IN_PROGRESS;
        moveHistory = new ArrayList<>();
        chatHistory = new ArrayList<>();
        
        // Set mediator for both users
        whitePlayer.setMediator(this);
        blackPlayer.setMediator(this);
        
        System.out.println("Match started between " + whitePlayer.getName() + " (White) and " 
             + blackPlayer.getName() + " (Black)");
    }
    
    public boolean makeMove(Position from, Position to, User player) {
        if (status != GameStatus.IN_PROGRESS) {
            System.out.println("Game is not in progress!");
            return false;
        }
        
        Color playerColor = getPlayerColor(player);
        if (playerColor != currentTurn) {
            System.out.println("It's not your turn!");
            return false;
        }
        
        Piece piece = board.getPiece(from);
        if (piece == null || piece.getColor() != playerColor) {
            System.out.println("Invalid piece selection!");
            return false;
        }
        
        Move move = new Move(from, to, piece, board.getPiece(to));
        
        if (!rules.isValidMove(move, board)) {
            System.out.println("Invalid move!");
            return false;
        }
        
        // Execute move
        board.movePiece(from, to);
        moveHistory.add(move);
        
        System.out.println(player.getName() + " moved " + piece.getSymbol() 
             + " from " + from.toChessNotation() + " to " + to.toChessNotation());
        
        board.display();
        
        // Check game end conditions
        Color opponentColor = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
        if (rules.isCheckmate(opponentColor, board)) {
            endGame(player, "checkmate");
            return true;
        } 
        else if (rules.isStalemate(opponentColor, board)) {
            endGame(player, "stalemate");
            return true;
        } 
        else {
            currentTurn = opponentColor;
            if (rules.isInCheck(opponentColor, board)) {
                System.out.println(getPlayerByColor(opponentColor).getName() + " is in check!");
            }
        }
        
        return true;
    }
    
    public void quitGame(User player) {
        User opponent = (player == whitePlayer) ? blackPlayer : whitePlayer;
        endGame(opponent, "quit");
        player.decrementScore(50); // Penalty for quitting
        System.out.println(player.getName() + " quit the game. Score decreased by 50.");
    }
    
    public void endGame(User winner, String reason) {
        status = GameStatus.COMPLETED;
        
        if (winner != null) {
            User loser = (winner == whitePlayer) ? blackPlayer : whitePlayer;
            winner.incrementScore(30);
            loser.decrementScore(20);
            System.out.println("Game ended - " + winner.getName() + " wins by " + reason + "!");
            System.out.println("Score update: " + winner.getName() + " +30, " + loser.getName() + " -20");
        } 
        else {
            System.out.println("Game ended in " + reason + "! No score change.");
        }
    }
    
    public Color getPlayerColor(User player) {
        return (player == whitePlayer) ? Color.WHITE : Color.BLACK;
    }
    
    public User getPlayerByColor(Color color) {
        return (color == Color.WHITE) ? whitePlayer : blackPlayer;
    }
    
    // Mediator Pattern implementation
    @Override
    public void sendMessage(Message message, User user) {
        chatHistory.add(message);
        
        User recipient = (user == whitePlayer) ? blackPlayer : whitePlayer;
        recipient.receive(message);
        System.out.println("Chat in match " + matchId + " - " + message.getContent());
    }
    
    @Override
    public void addUser(User user) {
        // Not applicable for chess match (always 2 users)
    }
    
    @Override
    public void removeUser(User user) {
        quitGame(user);
    }
    
    public String getMatchId() { 
        return matchId; 
    }
    public GameStatus getStatus() { 
        return status; 
    }
    public User getWhitePlayer() { 
        return whitePlayer; 
    }
    public User getBlackPlayer() { 
        return blackPlayer; 
    }
    public Board getBoard() { 
        return board; 
    }
}

