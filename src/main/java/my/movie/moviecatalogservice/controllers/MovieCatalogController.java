package my.movie.moviecatalogservice.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import my.movie.moviecatalogservice.model.entity.Movie;
import my.movie.moviecatalogservice.model.entity.MovieCatalog;
import my.movie.moviecatalogservice.model.entity.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    private final RestTemplate restTemplate;

    @Autowired
    public MovieCatalogController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalogs")
    public List<MovieCatalog> getCatalogs(@PathVariable("userId") final String userId){
        final UserRating userRating = restTemplate.getForObject("http://movie-rating-service/ratingdata/users/" + userId, UserRating.class);

        return userRating.getRatings().stream().map(rating -> {
            final Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

//            final Movie movie = webClientBuilder.build().
//                    get().
//                    uri("http://movie-info-service/movies/" + rating.getMovieId()).
//                    retrieve().
//                    bodyToMono(Movie.class).
//                    block();
            return new MovieCatalog(movie.getTitle(), movie.getOverview(), rating.getRating());
        }).collect(Collectors.toList());

    }

    public List<MovieCatalog> getFallbackCatalogs(@PathVariable("userId") final String userId){
        return Collections.singletonList(new MovieCatalog("No movie", "", 0));
    }
}
