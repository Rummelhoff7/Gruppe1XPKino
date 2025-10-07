package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByMovieTitleIgnoreCase(String movieTitle);
}
