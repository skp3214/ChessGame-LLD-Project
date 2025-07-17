package chess;

import chess.enums.GameStatus;
import chess.mediator.Match;
import chess.mediator.User;
import chess.model.Message;
import chess.model.Position;
public class ChessSystemDemo {
    // Method to demonstrate Scholar's Mate (4-move checkmate)
    public static void demonstrateScholarsMate() {
        System.out.println("\n=== Scholar's Mate Demo (4-move checkmate) ===");
        
        User aditya = new User("DEMO_1", "Aditya");
        User rohit = new User("DEMO_2", "Rohit");
        
        Match demoMatch = new Match("DEMO_MATCH", aditya, rohit);
        demoMatch.getBoard().display();
        
        // Proper Scholar's Mate sequence with correct coordinates
        System.out.println("\nMove 1: White e2-e4");
        demoMatch.makeMove(new Position(6, 4), new Position(4, 4), aditya); // e2-e4
        
        System.out.println("\nMove 1: Black e7-e5");
        demoMatch.makeMove(new Position(1, 4), new Position(3, 4), rohit); // e7-e5
        
        System.out.println("\nMove 2: White Bf1-c4 (targeting f7)");
        demoMatch.makeMove(new Position(7, 5), new Position(4, 2), aditya); // Bf1-c4
        
        System.out.println("\nMove 2: Black Nb8-c6 (developing)");
        demoMatch.makeMove(new Position(0, 1), new Position(2, 2), rohit); // Nb8-c6
        
        System.out.println("\nMove 3: White Qd1-h5 (attacking f7 and h7)");
        demoMatch.makeMove(new Position(7, 3), new Position(3, 7), aditya); // Qd1-h5 (row 3, col 7 = h5)
        
        System.out.println("\nMove 3: Black Ng8-f6?? (defending h7 but exposing f7)");
        demoMatch.makeMove(new Position(0, 6), new Position(2, 5), rohit); // Ng8-f6
        
        System.out.println("\nMove 4: White Qh5xf7# (Checkmate!)");
        demoMatch.makeMove(new Position(3, 7), new Position(1, 5), aditya); // Qh5xf7#
        
        if (demoMatch.getStatus() != GameStatus.COMPLETED) {
            System.out.println("Note: Checkmate detection may need refinement for this position.");
        }
        
        // Demonstrate chat functionality
        System.out.println("\n=== Testing Chat Functionality ===");
        aditya.send(new Message(aditya.getId(), "Good game!"));
        rohit.send(new Message(rohit.getId(), "Thanks, that was a quick one!"));
    }
}

