package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Theater findByTheaterName(String theaterName);
}
