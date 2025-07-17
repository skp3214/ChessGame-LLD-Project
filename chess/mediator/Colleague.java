package chess.mediator;

import chess.model.Message;

public abstract class Colleague {
    protected ChatMediator mediator;
    
    public Colleague() { 
        mediator = null; 
    }
    public abstract void send(Message message);
    public abstract void receive(Message message);
    public void setMediator(ChatMediator med) { 
        mediator = med; 
    }
    public ChatMediator getMediator() { 
        return mediator; 
    }
}
