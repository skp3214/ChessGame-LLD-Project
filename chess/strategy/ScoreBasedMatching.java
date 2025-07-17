package chess.strategy;

import java.util.List;

import chess.mediator.User;

public class ScoreBasedMatching implements MatchingStrategy {
    private int scoreTolerance;

    public ScoreBasedMatching(int tolerance) {
        scoreTolerance = tolerance;
    }
    
    @Override
    public User findMatch(User user, List<User> waitingUsers) {
        User bestMatch = null;
        int bestScoreDiff = Integer.MAX_VALUE;
        
        for (User waitingUser : waitingUsers) {
            if (!waitingUser.getId().equals(user.getId())) {
                int scoreDiff = Math.abs(waitingUser.getScore() - user.getScore());
                if (scoreDiff <= scoreTolerance && scoreDiff < bestScoreDiff) {
                    bestMatch = waitingUser;
                    bestScoreDiff = scoreDiff;
                }
            }
        }
        return bestMatch;
    }
}
