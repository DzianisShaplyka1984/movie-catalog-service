package my.movie.moviecatalogservice.model.entity;

public class Movie {
    private String movieId;
    private String title;
    private String overview;

    public Movie() {
    }

    public Movie(final String movieId, final String title, final String overview) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(final String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }
}