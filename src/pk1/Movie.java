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
    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public String getLanguage() {
        return language;
    }

    public Category getCategory() {
        return category;
    }


    // SETTERS - Modify private fields from outside (WRITE)
    // No setter for movieId because it should never change after creation

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return
                "\n" +
                        " ID       : " + movieId+ "\n" +
                        " Title    : " + title+ "\n" +
                        "Director : " + director + "\n" +
                        "Year     : " + releaseYear+ "\n" +
                        "Rating   : " + rating + "\n" +
                        "Language :" + language + "\n" +
                        "Category : " + category ;

    }
}
