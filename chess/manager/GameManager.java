package chess.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.enums.GameStatus;
import chess.mediator.Match;
import chess.mediator.User;
import chess.model.Message;
import chess.model.Position;
import chess.strategy.MatchingStrategy;
import chess.strategy.ScoreBasedMatching;

public class GameManager {
    private static GameManager instance;
    private Map<String, Match> activeMatches; // matchId --> Match
    private List<User> waitingUsers;
    private MatchingStrategy matchingStrategy;
    private int matchCounter;
    
    private GameManager() {
        activeMatches = new HashMap<>();
        waitingUsers = new ArrayList<>();
        matchingStrategy = new ScoreBasedMatching(100); // 100 points tolerance
        matchCounter = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    public void requestMatch(User user) {
        System.out.println(user.getName() + " is looking for a match...");
        
        User opponent = matchingStrategy.findMatch(user, waitingUsers);
        
        if (opponent != null) {
            // Remove opponent from waiting list
            waitingUsers.remove(opponent);
            
            String matchId = "MATCH_" + (++matchCounter);
            Match match = new Match(matchId, user, opponent);
            activeMatches.put(matchId, match);
            
            System.out.println("Match found! " + user.getName() + " vs " + opponent.getName());
            match.getBoard().display();
        } 
        else {
            waitingUsers.add(user);
            System.out.println(user.getName() + " added to waiting list.");
        }
    }
    
    public void makeMove(String matchId, Position from, Position to, User player) {
        if (activeMatches.containsKey(matchId)) {
            Match match = activeMatches.get(matchId);
            match.makeMove(from, to, player);
            
            if (match.getStatus() == GameStatus.COMPLETED) {
                activeMatches.remove(matchId);
                System.out.println("Match " + matchId + " completed and removed from active matches.");
            }
        }
    }
    
    public void quitMatch(String matchId, User player) {
        if (activeMatches.containsKey(matchId)) {
            Match match = activeMatches.get(matchId);
            match.quitGame(player);
            activeMatches.remove(matchId);
        }
    }
    
    public void sendChatMessage(String matchId, String message, User user) {
        if (activeMatches.containsKey(matchId)) {
            Match match = activeMatches.get(matchId);
            Message msg = new Message(user.getId(), message);
            match.sendMessage(msg, user);
        }
    }
    
    public Match getMatch(String matchId) {
        if (activeMatches.containsKey(matchId)) {
            return activeMatches.get(matchId);
        }
        return null;
    }
    
    public void displayActiveMatches() {
        System.out.println("\n=== Active Matches ===");
        for (Map.Entry<String, Match> entry : activeMatches.entrySet()) {
            Match match = entry.getValue();
            System.out.println("Match " + match.getMatchId() + ": " 
                 + match.getWhitePlayer().getName() + " vs " 
                 + match.getBlackPlayer().getName());
        }
        System.out.println("Total active matches: " + activeMatches.size());
        System.out.println("Users waiting: " + waitingUsers.size());
    }
}

