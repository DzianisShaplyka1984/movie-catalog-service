package my.movie.moviecatalogservice.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import my.movie.moviecatalogservice.model.entity.Rating;
import my.movie.moviecatalogservice.model.entity.UserRating;

@Service
public class RatingDataService {
    private final RestTemplate restTemplate;

    @Autowired
    public RatingDataService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackRating")
    public UserRating getUserRating(final String userId) {
        return restTemplate.getForObject("http://movie-rating-service/ratingdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackRating(final String userId) {
        return new UserRating(Collections.singletonList(new Rating("0", 0)));
    }
}
