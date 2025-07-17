package chess.strategy;

import java.util.List;

import chess.mediator.User;

public interface MatchingStrategy {
    User findMatch(User user, List<User> waitingUsers);
}
