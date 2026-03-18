package pk2;

import pk1.Category;
import pk1.Movie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseManager implements MovieOperations {

    protected Map<Category, List<Movie>> movieCollection;


    public BaseManager() {
        movieCollection = new HashMap<>();

        // Initialize empty ArrayList for each category
        movieCollection.put(Category.WATCHED, new ArrayList<>());
        movieCollection.put(Category.WATCHLIST, new ArrayList<>());
        movieCollection.put(Category.FAVOURITE, new ArrayList<>());

        System.out.println("✅ Movie collection initialized with 3 categories.");
    }

    protected Movie findMovieById(String movieId) {
        // Loop through all categories
        for (Category category : Category.values()) {
            List<Movie> movies = movieCollection.get(category);

            // Loop through movies in this category
            for (Movie movie : movies) {
                if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                    return movie;  // Found it!
                }
            }
        }
        return null;  // Not found in any category
    }


    protected Category findCategoryByMovieId(String movieId) {
        for (Category category : Category.values()) {
            List<Movie> movies = movieCollection.get(category);

            for (Movie movie : movies) {
                if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                    return category;  // Found which category it's in
                }
            }
        }
        return null;  // Movie not found
    }


    protected int getTotalMovieCount() {
        int total = 0;
        for (List<Movie> movies : movieCollection.values()) {
            total += movies.size();
        }
        return total;
    }


    protected boolean movieExists(String movieId) {
        return findMovieById(movieId) != null;
    }

    protected List<Movie> getMoviesByCategory(Category category) {
        return movieCollection.get(category);
    }

    protected void displayHeader(String title) {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("  " + title);
        System.out.println("═".repeat(70));
    }

    protected void displaySeparator() {
        System.out.println("─".repeat(70));
    }

}
