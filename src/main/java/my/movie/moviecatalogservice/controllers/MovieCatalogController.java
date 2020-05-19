package my.movie.moviecatalogservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public List<MovieCatalog> getCatalogs(@PathVariable("userId") final String userId){
        final UserRating userRating = restTemplate.getForObject("http://movie-rating-service/ratingdata/users/" + userId, UserRating.class);

        return userRating.getRatings().stream().map(rating -> {
            final Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

//            final Movie movie = webClientBuilder.build().
//                    get().
//                    uri("http://localhost:8082/movies/" + rating.getMovieId()).
//                    retrieve().
//                    bodyToMono(Movie.class).
//                    block();
            return new MovieCatalog(movie.getName(), "Desc1", rating.getRating());
        }).collect(Collectors.toList());

    }
}
