package chess.mediator;

import chess.model.Message;

public interface ChatMediator {
    void sendMessage(Message message, User user);
    void addUser(User user);
    void removeUser(User user);
}
