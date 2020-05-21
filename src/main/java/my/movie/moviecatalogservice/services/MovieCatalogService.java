package my.movie.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import my.movie.moviecatalogservice.model.entity.Movie;
import my.movie.moviecatalogservice.model.entity.MovieCatalog;
import my.movie.moviecatalogservice.model.entity.Rating;

@Service
public class MovieCatalogService {
    private final RestTemplate restTemplate;

    @Autowired
    public MovieCatalogService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogs")
    public MovieCatalog getMovieCatalog(final Rating rating) {
        final Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new MovieCatalog(movie.getTitle(), movie.getOverview(), rating.getRating());
    }

    private MovieCatalog getFallbackCatalogs(final Rating rating){
        return new MovieCatalog("No movie", "", 0);
    }
}
