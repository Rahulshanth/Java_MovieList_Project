package pk2;

import pk1.Category;
import pk1.Movie;
import pk1.Transaction;
import pk1.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MovieManager extends BaseManager {

    private User currentUser;
    private Scanner scanner;

    // ═══════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════

    public MovieManager(User currentUser) {
        super();
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        System.out.println(" MovieManager ready for user: " + currentUser.getUsername());
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 4 — addMovie()
    // ═══════════════════════════════════════════════════════

    @Override
    public boolean addMovie(Movie movie) {

        displayHeader("  ADD A NEW MOVIE");

        try {
            System.out.print("  Enter Title       : ");
            String title = scanner.nextLine().trim();

            System.out.print("  Enter Director    : ");
            String director = scanner.nextLine().trim();

            System.out.print("  Enter Year        : ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("  Enter Rating (1-5): ");
            double rating = Double.parseDouble(scanner.nextLine().trim());

            if (rating < 1.0 || rating > 5.0) {
                System.out.println(" Rating must be between 1.0 and 5.0");
                return false;
            }

            System.out.print("  Enter Language    : ");
            String language = scanner.nextLine().trim();

            Category category = pickCategory();
            if (category == null) return false;

            Movie newMovie = new Movie(title, director, year, rating, language, category);
            movieCollection.get(category).add(newMovie);

            Transaction t = new Transaction(newMovie.getMovieId(), Transaction.ActionType.ADD);
            currentUser.addTransaction(t);

            System.out.println("\n Movie added successfully!");
            System.out.println(newMovie);
            displaySeparator();
            return true;

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input — please enter numbers where required.");
            return false;
        }
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 4 — displayAll()
    // ═══════════════════════════════════════════════════════

    @Override
    public void displayAll() {

        displayHeader("  YOUR COMPLETE MOVIE COLLECTION");

        int total = getTotalMovieCount();

        if (total == 0) {
            System.out.println("  No movies yet. Start adding some! 🍿");
            displaySeparator();
            return;
        }

        System.out.println("  Total movies: " + total);
        displaySeparator();

        for (Category category : Category.values()) {
            List<Movie> movies = movieCollection.get(category);

            System.out.println("\n  " + category + " (" + movies.size() + " movies)");
            System.out.println("  " + "─".repeat(50));

            if (movies.isEmpty()) {
                System.out.println("  (empty)");
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.println("  " + (i + 1) + ".");
                    System.out.println(movies.get(i));
                    System.out.println("  " + "─".repeat(50));
                }
            }
        }
        displaySeparator();
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 4 — displayByCategory()
    // ═══════════════════════════════════════════════════════

    @Override
    public void displayByCategory(Category category) {

        displayHeader(" MOVIES IN: " + category);
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

    // ═══════════════════════════════════════════════════════
    //  DAY 5 — searchMovie()
    //  Searches by: title (partial), category, language
    // ═══════════════════════════════════════════════════════

    @Override
    public List<Movie> searchMovie(String keyword) {

        displayHeader("🔍  SEARCH MOVIES");

        System.out.println("  Search by:");
        System.out.println("  1 → Title (partial match)");
        System.out.println("  2 → Category");
        System.out.println("  3 → Language");
        System.out.print("  Your choice: ");

        List<Movie> results = new ArrayList<>();

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {

                // ── Search by title ──
                case 1: {
                    System.out.print("  Enter title keyword: ");
                    String titleKey = scanner.nextLine().trim().toLowerCase();

                    for (Category cat : Category.values()) {
                        for (Movie m : movieCollection.get(cat)) {
                            // .contains() — partial match, case-insensitive
                            if (m.getTitle().toLowerCase().contains(titleKey)) {
                                results.add(m);
                            }
                        }
                    }
                    break;
                }

                // ── Search by category ──
                case 2: {
                    Category cat = pickCategory();
                    if (cat != null) {
                        results.addAll(movieCollection.get(cat));
                    }
                    break;
                }

                // ── Search by language ──
                case 3: {
                    System.out.print("  Enter language: ");
                    String langKey = scanner.nextLine().trim().toLowerCase();

                    for (Category cat : Category.values()) {
                        for (Movie m : movieCollection.get(cat)) {
                            if (m.getLanguage().toLowerCase().contains(langKey)) {
                                results.add(m);
                            }
                        }
                    }
                    break;
                }

                default:
                    System.out.println(" Invalid choice.");
                    return results;
            }

        } catch (NumberFormatException e) {
            System.out.println(" Invalid input.");
            return results;
        }

        // ── Display results ──
        displaySeparator();
        if (results.isEmpty()) {
            System.out.println("  No movies found matching your search.");
        } else {
            System.out.println("  Found " + results.size() + " movie(s):\n");
            for (int i = 0; i < results.size(); i++) {
                System.out.println("  " + (i + 1) + ".");
                System.out.println(results.get(i));
                System.out.println("  " + "─".repeat(50));
            }
        }
        displaySeparator();
        return results;
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 5 — removeMovie()
    //  Finds by unique ID, removes from correct category list
    // ═══════════════════════════════════════════════════════

    @Override
    public boolean removeMovie(String movieId) {

        displayHeader("  REMOVE A MOVIE");

        // ── Step 1: Find which category it lives in ──
        // findCategoryByMovieId() is in BaseManager — searches all 3 lists
        Category category = findCategoryByMovieId(movieId);

        if (category == null) {
            System.out.println(" No movie found with ID: " + movieId);
            return false;
        }

        // ── Step 2: Get the actual Movie object ──
        Movie target = findMovieById(movieId);

        // ── Step 3: Confirm before deleting ──
        System.out.println("  Found: " + target.getTitle() + " [" + movieId + "]");
        System.out.print("  Are you sure you want to remove it? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("   Remove cancelled.");
            return false;
        }

        // ── Step 4: Remove from the ArrayList in the HashMap ──
        movieCollection.get(category).remove(target);

        // ── Step 5: Log the transaction ──
        Transaction t = new Transaction(movieId, Transaction.ActionType.REMOVE);
        currentUser.addTransaction(t);

        System.out.println(" \"" + target.getTitle() + "\" removed successfully.");
        displaySeparator();
        return true;
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 5 — editMovie()
    //  Editable fields: Title, Director, Rating, Category
    // ═══════════════════════════════════════════════════════

    @Override
    public boolean editMovie(String movieId) {

        displayHeader("✏️  EDIT A MOVIE");

        // ── Step 1: Find the movie ──
        Movie target = findMovieById(movieId);

        if (target == null) {
            System.out.println("❌ No movie found with ID: " + movieId);
            return false;
        }

        System.out.println("  Editing: " + target.getTitle() + " [" + movieId + "]");
        System.out.println("  (Press ENTER to keep the current value)\n");

        // ── Step 2: Edit Title ──
        System.out.print("  New Title [" + target.getTitle() + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            target.setTitle(newTitle);
        }

        // ── Step 3: Edit Director ──
        System.out.print("  New Director [" + target.getDirector() + "]: ");
        String newDirector = scanner.nextLine().trim();
        if (!newDirector.isEmpty()) {
            target.setDirector(newDirector);
        }

        // ── Step 4: Edit Rating ──
        System.out.print("  New Rating [" + target.getRating() + "]: ");
        String newRatingStr = scanner.nextLine().trim();
        if (!newRatingStr.isEmpty()) {
            try {
                double newRating = Double.parseDouble(newRatingStr);
                if (newRating < 1.0 || newRating > 5.0) {
                    System.out.println("⚠️  Rating out of range — keeping old value.");
                } else {
                    target.setRating(newRating);
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Invalid rating — keeping old value.");
            }
        }

        // ── Step 5: Edit Category (needs special handling) ──
        System.out.println("\n  Current Category: " + target.getCategory());
        System.out.print("  Change category? (yes/no): ");
        String changeCat = scanner.nextLine().trim().toLowerCase();

        if (changeCat.equals("yes")) {
            Category oldCategory = findCategoryByMovieId(movieId);
            Category newCategory = pickCategory();

            if (newCategory != null && newCategory != oldCategory) {
                // Remove from old list, add to new list
                movieCollection.get(oldCategory).remove(target);
                target.setCategory(newCategory);
                movieCollection.get(newCategory).add(target);
                System.out.println("  📂 Moved to: " + newCategory);
            }
        }

        // ── Step 6: Log the transaction ──
        Transaction t = new Transaction(movieId, Transaction.ActionType.EDIT);
        currentUser.addTransaction(t);

        System.out.println("\n Movie updated successfully!");
        System.out.println(target);
        displaySeparator();
        return true;
    }

    // ═══════════════════════════════════════════════════════
    //  DAY 6 — SORTING  (3 sort options)
    //  Uses Comparator — separate from Comparable in Movie.java
    //  Comparable = natural order (rating desc)
    //  Comparator = custom orders (title, year)
    // ═══════════════════════════════════════════════════════

    /**
     * SORT MENU — lets user pick which sort to apply
     * Sorts across ALL categories combined into one view
     */
    public void sortAndDisplay() {

        displayHeader("📊  SORT YOUR COLLECTION");

        System.out.println("  Sort by:");
        System.out.println("  1 → Rating      (highest first) — uses Comparable");
        System.out.println("  2 → Title       (A → Z)         — uses Comparator");
        System.out.println("  3 → Year        (newest first)  — uses Comparator");
        System.out.print("  Your choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            // ── Gather all movies from all categories into one flat list ──
            List<Movie> allMovies = getAllMoviesFlat();

            if (allMovies.isEmpty()) {
                System.out.println("  No movies to sort yet.");
                return;
            }

            switch (choice) {

                // ── Sort by rating — uses Movie's own compareTo() (Comparable) ──
                case 1:
                    Collections.sort(allMovies); // calls compareTo() → rating DESC
                    displaySortedList(allMovies, "⭐  SORTED BY RATING (Highest First)");
                    break;

                // ── Sort by title — Comparator defined RIGHT HERE (lambda) ──
                case 2:
                    // Comparator.comparing() extracts the sort key (title string)
                    // String comparison is A→Z by default
                    allMovies.sort(Comparator.comparing(m -> m.getTitle().toLowerCase()));
                    displaySortedList(allMovies, "🔤  SORTED BY TITLE (A → Z)");
                    break;

                // ── Sort by year — Comparator with reversed order (newest first) ──
                case 3:
                    // Integer.compare(b, a) instead of (a, b) gives DESCENDING order
                    allMovies.sort((m1, m2) -> Integer.compare(m2.getReleaseYear(), m1.getReleaseYear()));
                    displaySortedList(allMovies, "📅  SORTED BY YEAR (Newest First)");
                    break;

                default:
                    System.out.println("❌ Invalid choice.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
        }
    }

    /**
     * Helper — collect every movie from all 3 category lists
     * into one single flat ArrayList for sorting
     */
    private List<Movie> getAllMoviesFlat() {
        List<Movie> all = new ArrayList<>();
        for (Category cat : Category.values()) {
            all.addAll(movieCollection.get(cat));
        }
        return all;
    }

    /**
     * Helper — display a sorted list with a custom header title
     */
    private void displaySortedList(List<Movie> movies, String title) {
        displaySeparator();
        System.out.println("  " + title);
        displaySeparator();
        for (int i = 0; i < movies.size(); i++) {
            System.out.println("  " + (i + 1) + ".");
            System.out.println(movies.get(i));
            System.out.println("  " + "─".repeat(50));
        }
        displaySeparator();
    }

    // ═══════════════════════════════════════════════════════
    //  SHARED HELPER — pickCategory()
    //  Reused by add, edit, search — avoids copy-pasting the menu
    // ═══════════════════════════════════════════════════════

    private Category pickCategory() {
        System.out.println("\n  Choose Category:");
        System.out.println("  1 → WATCHED");
        System.out.println("  2 → WATCHLIST");
        System.out.println("  3 → FAVOURITE");
        System.out.print("  Your choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1: return Category.WATCHED;
                case 2: return Category.WATCHLIST;
                case 3: return Category.FAVOURITE;
                default:
                    System.out.println("Invalid category.");
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }
    }
}
