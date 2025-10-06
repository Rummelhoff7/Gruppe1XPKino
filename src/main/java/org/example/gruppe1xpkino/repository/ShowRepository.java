package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    List<Show> findByShowingTimeBetween(LocalDateTime start, LocalDateTime end);
}


