package pk2;

import pk1.Category;
import pk1.Movie;
import pk1.Transaction;
import pk1.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieManager extends BaseManager {


    private User currentUser;

    private Scanner scanner;

    public MovieManager(User currentUser) {
        super(); // calls BaseManager() — initializes the HashMap
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        System.out.println("🎬 MovieManager ready for user: " + currentUser.getUsername());
    }

    @Override
    public boolean addMovie(Movie movie) {

        displayHeader("➕  ADD A NEW MOVIE");

        try {
            // ── Step 1: Collect details from the console ──
            System.out.print("  Enter Title      : ");
            String title = scanner.nextLine().trim();

            System.out.print("  Enter Director   : ");
            String director = scanner.nextLine().trim();

            System.out.print("  Enter Year       : ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Enter Rating (1-5): ");
            double rating = Double.parseDouble(scanner.nextLine().trim());

            // Validate rating range
            if (rating < 1.0 || rating > 5.0) {
                System.out.println("❌ Rating must be between 1.0 and 5.0");
                return false;
            }

            System.out.print("  Enter Language   : ");
            String language = scanner.nextLine().trim();

            // ── Step 2: Pick a Category ──
            System.out.println("\n  Choose Category:");
            System.out.println("  1 → WATCHED");
            System.out.println("  2 → WATCHLIST");
            System.out.println("  3 → FAVOURITE");
            System.out.print("  Your choice      : ");
            int catChoice = Integer.parseInt(scanner.nextLine().trim());

            Category category;
            switch (catChoice) {
                case 1: category = Category.WATCHED;   break;
                case 2: category = Category.WATCHLIST; break;
                case 3: category = Category.FAVOURITE; break;
                default:
                    System.out.println("❌ Invalid category choice.");
                    return false;
            }

            //Create the Movie object ──
            Movie newMovie = new Movie(title, director, year, rating, language, category);

            // Add to the HashMap under the right category ──
            movieCollection.get(category).add(newMovie);

            // Log the transaction ──
            Transaction t = new Transaction(newMovie.getMovieId(), Transaction.ActionType.ADD);
            currentUser.addTransaction(t);

            // Confirm to user ──
            System.out.println("\n✅ Movie added successfully!");
            System.out.println(newMovie);   // uses Movie.toString()
            displaySeparator();

            return true;

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input — please enter numbers where required.");
            return false;
        }
    }

    @Override
    public void displayAll() {

        displayHeader("🎬  YOUR COMPLETE MOVIE COLLECTION");

        int totalMovies = getTotalMovieCount();   // from BaseManager

        if (totalMovies == 0) {
            System.out.println("  No movies in your collection yet. Start adding some! 🍿");
            displaySeparator();
            return;
        }

        System.out.println("  Total movies: " + totalMovies);
        displaySeparator();

        for (Category category : Category.values()) {

            List<Movie> movies = movieCollection.get(category);

            System.out.println("\n  📂 " + category + " (" + movies.size() + " movies)");
            System.out.println("  " + "─".repeat(50));

            if (movies.isEmpty()) {
                System.out.println("  (no movies here yet)");
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.println("  " + (i + 1) + ".");
                    System.out.println(movies.get(i));  // Movie.toString()
                    System.out.println("  " + "─".repeat(50));
                }
            }
        }

        displaySeparator();
    }

    @Override
    public void displayByCategory(Category category) {

        displayHeader("📂  MOVIES IN CATEGORY: " + category);

        List<Movie> movies = movieCollection.get(category);

        if (movies.isEmpty()) {
            System.out.println("  No movies in this category yet.");
        } else {
            System.out.println("  Total: " + movies.size() + " movie(s)");
            displaySeparator();
            for (int i = 0; i < movies.size(); i++) {
                System.out.println("  " + (i + 1) + ".");
                System.out.println(movies.get(i));
                displaySeparator();
            }
        }
    }

    @Override
    public boolean removeMovie(String movieId) {
        System.out.println("🔧 removeMovie() — coming on Day 5");
        return false;
    }

    @Override
    public boolean editMovie(String movieId) {
        System.out.println("🔧 editMovie() — coming on Day 5");
        return false;
    }

    @Override
    public List<Movie> searchMovie(String title) {
        System.out.println("🔧 searchMovie() — coming on Day 5");
        return new ArrayList<>();
    }
}
