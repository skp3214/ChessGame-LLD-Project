package chess;

import chess.manager.GameManager;
import chess.mediator.User;


// Main class to run the chess system
public class Chess {
    public static void main(String[] args) {
        System.out.println("=== Chess System with Design Patterns Demo ===");
        
        // Test Scholar's Mate
        ChessSystemDemo.demonstrateScholarsMate();
        
        // Demonstrate Game Manager functionality
        System.out.println("\n=== Game Manager Demo ===");
        GameManager gm = GameManager.getInstance();
        
        User saurav = new User("USER_1", "Saurav");
        User manish = new User("USER_2", "Manish");
        User abhishek = new User("USER_3", "Abishek");
        
        System.out.println("\nUsers: " + saurav.toString() + ", " + manish.toString() + ", " + abhishek.toString());
        
        // Request matches
        gm.requestMatch(saurav);
        gm.requestMatch(manish);  // Should create a match
        gm.requestMatch(abhishek); // Should go to waiting list
        
        gm.displayActiveMatches();
    }
}