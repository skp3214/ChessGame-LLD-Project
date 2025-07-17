package chess.model;

public class Message {
    private String senderId;
    private String content;
    private long timestamp;

    public Message(String sId, String msg) {
        senderId = sId;
        content = msg;
        timestamp = System.currentTimeMillis();
    }
    
    public String getSenderId() { 
        return senderId; 
    }
    public String getContent() { 
        return content; 
    }
    public long getTimestamp() { 
        return timestamp; 
    }
    
    @Override
    public String toString() {
        return "[" + senderId + "]: " + content;
    }
}
