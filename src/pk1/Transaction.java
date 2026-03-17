package pk1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single transaction (action) performed on a movie.
 * Every ADD/EDIT/REMOVE operation creates a Transaction record.
 * Demonstrates: Enum, Encapsulation, toString formatting
 */
public class Transaction {

    // Private fields - ENCAPSULATION
    private String transactionId;      // Unique transaction ID
    private String movieId;            // Which movie was affected
    private ActionType actionType;     // What action was performed (ADD, EDIT, REMOVE)
    private LocalDateTime timestamp;   // When the action happened

    // Static counter for auto-generating unique transaction IDs
    private static int idCounter = 5000;


    /**
     * Enum for Action Types - Only 3 allowed actions
     */
    public enum ActionType {
        ADD,     // Movie added to collection
        EDIT,    // Movie details modified
        REMOVE   // Movie removed from collection
    }


    /**
     * CONSTRUCTOR - Creates a new Transaction
     * @param movieId - ID of the movie being affected
     * @param actionType - Type of action (ADD/EDIT/REMOVE)
     */
    public Transaction(String movieId, ActionType actionType) {
        this.transactionId = "TXN" + (++idCounter);  // Auto-generate: TXN5001, TXN5002...
        this.movieId = movieId;
        this.actionType = actionType;
        this.timestamp = LocalDateTime.now();  // Capture current date and time
    }


    // GETTERS - No setters because transactions should never be modified after creation
    public String getTransactionId() {
        return transactionId;
    }

    public String getMovieId() {
        return movieId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Get formatted timestamp for display (e.g., "01-Mar-2026 10:30 AM")
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
        return timestamp.format(formatter);
    }


    /**
     * toString() - Clean display format for console output
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Movie: %s | Action: %s",
                transactionId,
                getFormattedTimestamp(),
                movieId,
                actionType
        );
    }
}
