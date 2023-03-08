package com.learnjava.apiClient;

import com.learnjava.domain.movie.Review;
import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MoviesClient {
    private final WebClient webClient;

    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId) {
        // MovieInfo
        MovieInfo movieInfo = invokeMovieInfoService(movieInfoId);

        // Review
        List<Review> reviews = invokeReviewsService(movieInfoId);

        return new Movie(movieInfo, reviews);
    }

    public List<Movie> retrieveMovies(List<Long> movieInfoIds) {
        // MovieInfo
        return movieInfoIds
                .stream()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());
    }

    public List<Movie> retrieveMovies_CF(List<Long> movieInfoIds) {
        // MovieInfo
        List<CompletableFuture<Movie>> movieFutures = movieInfoIds
                .stream()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());

        return movieFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<Movie> retrieveMovies_CF_allOf(List<Long> movieInfoIds) {
        // MovieInfo
        List<CompletableFuture<Movie>> movieFutures = movieInfoIds
                .stream()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(
                movieFutures.toArray(new CompletableFuture[movieFutures.size()]));

        return cfAllOf.thenApply(v ->
                        movieFutures
                                .stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()))
                .join();
    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {
        // MovieInfo
        CompletableFuture<MovieInfo> movieInfoCompletableFuture = CompletableFuture.supplyAsync(
                () -> invokeMovieInfoService(movieInfoId));

        // Review
        CompletableFuture<List<Review>> reviewListCompletableFuture = CompletableFuture.supplyAsync(
                () -> invokeReviewsService(movieInfoId));

        return movieInfoCompletableFuture
                .thenCombine(reviewListCompletableFuture, Movie::new);
    }

    private MovieInfo invokeMovieInfoService(Long movieInfoId) {
        String moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";

        return webClient
                .get()
                .uri(moviesInfoUrlPath, movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
    }

    private List<Review> invokeReviewsService(Long movieInfoId) {
        String reviewsUrlPath = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toString();

        return webClient
                .get()
                .uri(reviewsUrlPath)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }
}
