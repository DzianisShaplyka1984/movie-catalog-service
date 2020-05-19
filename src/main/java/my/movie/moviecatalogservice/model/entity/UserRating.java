package my.movie.moviecatalogservice.model.entity;

import java.util.List;

public class UserRating {
    private List<Rating> ratings;

    public UserRating() {
    }

    public UserRating(final List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Rating> ratings) {
        this.ratings = ratings;
    }
}
