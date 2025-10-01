package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Movie;
import org.example.gruppe1xpkino.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping()
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /*
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie newMovie = movieRepository.save(movie);
        return ResponseEntity.ok(newMovie);
    }

     */

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

}
