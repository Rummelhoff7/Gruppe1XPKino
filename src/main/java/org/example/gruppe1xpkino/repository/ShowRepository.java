package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    // Fetch shows between two specific times (existing)
    List<Show> findByShowingTimeBetween(LocalDateTime start, LocalDateTime end);

    // Fetch shows by movie ID (existing)
    List<Show> findByMovieId(int movieId);

    // Fetch all shows where today is within the screening period (new method)
    List<Show> findByShowingStartDateLessThanEqualAndShowingEndDateGreaterThanEqual(LocalDate start, LocalDate end);
}
