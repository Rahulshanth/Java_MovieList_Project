package pk1;


import java.util.ArrayList;
import java.util.List;

public class User {

    // Private fields - ENCAPSULATION
    private String userId;          // Unique user ID
    private String username;        // Display name

    // COMPOSITION - User HAS-A List of Transactions
    // This list belongs to the User. If User is deleted, all transactions are gone.
    private List<Transaction> transactionHistory;

    // Static counter for auto-generating user IDs
    private static int idCounter = 100;


    /**
     * CONSTRUCTOR - Creates a new User
     * @param username - Display name of the user
     */
    public User(String username) {
        this.userId = "U" + (++idCounter);  // Auto-generate: U101, U102...
        this.username = username;
        this.transactionHistory = new ArrayList<>();  // Initialize empty list
    }


    // GETTERS
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * COMPOSITION METHOD - Add a transaction to this user's history
     * This demonstrates the HAS-A relationship
     * @param transaction - The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        System.out.println("✅ Transaction logged: " + transaction.getActionType()
                + " on Movie " + transaction.getMovieId());
    }


    /**
     * Get the full transaction history
     * @return List of all transactions
     */
    public List<Transaction> getHistory() {
        return transactionHistory;
    }


    /**
     * Display all transactions in a formatted way
     */
    public void displayHistory() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("📜 TRANSACTION HISTORY FOR USER: " + username + " [" + userId + "]");
        System.out.println("=".repeat(70));

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < transactionHistory.size(); i++) {
                System.out.println((i + 1) + ". " + transactionHistory.get(i));
            }
        }
        System.out.println("=".repeat(70));
    }


    /**
     * Get count of each action type
     */
    public void displayStatistics() {
        int addCount = 0, editCount = 0, removeCount = 0;

        for (Transaction t : transactionHistory) {
            switch (t.getActionType()) {
                case ADD:
                    addCount++;
                    break;
                case EDIT:
                    editCount++;
                    break;
                case REMOVE:
                    removeCount++;
                    break;
            }
        }

        System.out.println("\n📊 USER STATISTICS:");
        System.out.println("   Movies Added   : " + addCount);
        System.out.println("   Movies Edited  : " + editCount);
        System.out.println("   Movies Removed : " + removeCount);
        System.out.println("   Total Actions  : " + transactionHistory.size());
    }


    @Override
    public String toString() {
        return String.format("User [%s] %s - %d transactions",
                userId, username, transactionHistory.size());
    }
}
