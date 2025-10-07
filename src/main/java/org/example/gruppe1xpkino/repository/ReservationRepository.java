package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    <S extends Reservation> S save(S s);
    List<Reservation> findByShowId(int showId);
}
