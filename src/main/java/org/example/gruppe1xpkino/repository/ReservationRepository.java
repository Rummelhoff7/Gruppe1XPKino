package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
