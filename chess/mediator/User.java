package chess.mediator;

import chess.model.Message;

public class User extends Colleague {
    private String id;
    private String name;
    private int score;

    public User(String userId, String userName) {
        super();
        id = userId;
        name = userName;
        score = 1000; // Starting score
    }
    
    public String getId() { 
        return id; 
    }
    public String getName() { 
        return name; 
    }
    public int getScore() { 
        return score; 
    }
    
    public void incrementScore(int points) {
        score += points;
    }
    
    public void decrementScore(int points) {
        score -= points;
    }
    
    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
    
    // Implement Colleague interface
    @Override
    public void send(Message message) {
        if (mediator != null) {
            mediator.sendMessage(message, this);
        }
    }
    
    @Override
    public void receive(Message message) {
        System.out.println("User " + name + " received message from " + message.getSenderId() + ": " + message.getContent());
    }
}

