package pk1;

public class Movie implements Comparable <Movie>{

    private String movieId;
    private String title;
    private String director;
    private int releaseYear;
    private double rating;
    private String language;
    private Category category;

    private static int ID = 001;

    @Override
    public int compareTo(Movie other) {
        // Sort by rating in DESCENDING order (5 stars first, 1 star last)
        return 0;
                //Double.compare(other.rating, this.rating);
    }
}
