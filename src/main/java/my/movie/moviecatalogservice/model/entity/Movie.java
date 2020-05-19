package my.movie.moviecatalogservice.model.entity;

public class Movie {
    private String movieId;
    private String name;

    public Movie() {
    }

    public Movie(final String movieId, final String name) {
        this.movieId = movieId;
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(final String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
