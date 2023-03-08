package com.learnjava.apiClient;

import com.learnjava.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class MoviesClientTest {

    WebClient webClient;
    MoviesClient moviesClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();
        moviesClient = new MoviesClient(webClient);
    }

    @RepeatedTest(10)
    void retrieveMovie() {
        // given
        startTimer();
        Long movieInfoId = 1L;

        // when
        Movie movie = moviesClient.retrieveMovie(movieInfoId);
        System.out.println("movie: " + movie);
        timeTaken();

        // then
        assertNotNull(movie);
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assertEquals(1, movie.getReviewList().size());
    }

    @RepeatedTest(10)
    void retrieveMovie_CF() {
        // given
        Long movieInfoId = 1L;
        startTimer();

        // when
        Movie movie = moviesClient.retrieveMovie_CF(movieInfoId)
                        .join();
        System.out.println("movie: " + movie);
        timeTaken();

        // then
        assertNotNull(movie);
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assertEquals(1, movie.getReviewList().size());
    }

    @RepeatedTest(10)
    void retrieveMovies() {
        // given
        startTimer();
        List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        // when
        List<Movie> movies = moviesClient.retrieveMovies(movieInfoId);
        timeTaken();

        // then
        assertNotNull(movies);
        assertEquals(7, movies.size());
    }

    @RepeatedTest(10)
    void retrieveMovies_CF() {
        // given
        startTimer();
        List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        // when
        List<Movie> movies = moviesClient.retrieveMovies_CF(movieInfoId);
        timeTaken();

        // then
        assertNotNull(movies);
        assertEquals(7, movies.size());
    }

    @RepeatedTest(10)
    void retrieveMovies_CF_allOf() {
        // given
        startTimer();
        List<Long> movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        // when
        List<Movie> movies = moviesClient.retrieveMovies_CF_allOf(movieInfoId);
        timeTaken();

        // then
        assertNotNull(movies);
        assertEquals(7, movies.size());
    }
}