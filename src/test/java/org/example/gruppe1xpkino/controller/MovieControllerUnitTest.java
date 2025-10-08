package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
@SpringBootTest full spring test. Tester alt sammen. (controller, repositories, config, db.
@DataJpaTest slice test = tester kun JPA repositories. H2 database
@WebMvcTest: slice test = tester kun "web layer" (controller, request mappings, json, etc.
@Test: unit test, i guess.


Det her er unit test

De ville være smart at lave WebMvcTest her også.

Starter med at mocke repositories
 */


@ExtendWith(MockitoExtension.class)
class MovieControllerUnitTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieController movieController;

    @Test
    void getAllMovies_andReturnMovies() {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie(), new Movie()));

        List<Movie> movies = movieController.getAllMovies();

        assertNotNull(movies);
        assertEquals(2, movies.size());

        verify(movieRepository,times(1)).findAll();
    }

    @Test
    void addMovie_savesAndReturnMovie() {
        Movie movie = new Movie();
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie savedMovie = movieController.addMovie(movie);

        assertNotNull(savedMovie);
        assertEquals(movie, savedMovie);
        verify(movieRepository,times(1)).save(movie);
    }
}