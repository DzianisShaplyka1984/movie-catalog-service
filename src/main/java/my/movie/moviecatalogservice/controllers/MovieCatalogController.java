package my.movie.moviecatalogservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.movie.moviecatalogservice.model.entity.MovieCatalog;
import my.movie.moviecatalogservice.model.entity.UserRating;
import my.movie.moviecatalogservice.services.MovieCatalogService;
import my.movie.moviecatalogservice.services.RatingDataService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    private final RatingDataService ratingDataService;

    private final MovieCatalogService movieCatalogService;

    @Autowired
    public MovieCatalogController(final RatingDataService ratingDataService, final MovieCatalogService movieCatalogService) {
        this.ratingDataService = ratingDataService;
        this.movieCatalogService = movieCatalogService;
    }

    @RequestMapping("/{userId}")
    public List<MovieCatalog> getCatalogs(@PathVariable("userId") final String userId){
        final UserRating userRating = ratingDataService.getUserRating(userId);

        return userRating.getRatings().stream().map(rating -> {
//            final Movie movie = webClientBuilder.build().
//                    get().
//                    uri("http://movie-info-service/movies/" + rating.getMovieId()).
//                    retrieve().
//                    bodyToMono(Movie.class).
//                    block();
            return movieCatalogService.getMovieCatalog(rating);
        }).collect(Collectors.toList());

    }
}
