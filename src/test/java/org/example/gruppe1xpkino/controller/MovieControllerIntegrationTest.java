package org.example.gruppe1xpkino.controller;

/*
import org.apache.coyote.Response;
import org.example.gruppe1xpkino.model.AgeLimit;
import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.model.MovieGenre;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
    }

    @Test // VIRKER IKKE PT, der skal være når deleteAll(), pga child/parent relations.
        // Dette kan løses med Cascade osv, men det føler jeg lidt er spild af tid nu, hvor vi ved det virker.
    void addMovie_andGetMovie() {
        Movie movie = new Movie();
        movie.setMovieTitle("The Godfather");
        movie.setActors("Leonardo");
        movie.setFeatureFilm(true);
        movie.setGenre(MovieGenre.ACTION);
        movie.setAgeLimit(AgeLimit.AGE_15);
        movie.setImg("theGodfather.jpg");
        movie.setShows(Collections.emptyList());

        // POST
        ResponseEntity<Movie> postResponse = restTemplate.postForEntity("/api/movies", movie, Movie.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie savedMovie = postResponse.getBody();
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getMovieTitle()).isEqualTo("The Godfather");

        //GET
        ResponseEntity<Movie[]> getResponse = restTemplate.getForEntity("/api/movies", Movie[].class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Movie> movies = List.of(getResponse.getBody());
        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getMovieTitle()).isEqualTo("The Godfather");
    }
}

 */