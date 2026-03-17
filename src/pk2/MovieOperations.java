package pk2;

import pk1.Movie;
import java.util.List;

public interface MovieOperations {

    boolean addMovie(Movie movie);

    boolean removeMovie(String movieId);


    boolean editMovie(String movieId);

    List<Movie> searchMovie(String title);

    void displayAll();

    void displayByCategory(pk1.Category category);
}
