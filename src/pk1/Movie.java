package pk1;

public class Movie implements Comparable <Movie>{

    @Override
    public int compareTo(Movie other) {
        // Sort by rating in DESCENDING order (5 stars first, 1 star last)
        return 0;
                //Double.compare(other.rating, this.rating);
    }
}
