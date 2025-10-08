package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    boolean existsByReservation_Show_IdAndSeat_SeatRowAndSeat_SeatNumber(
            int showId, int seatRow, int seatNumber);
    List<Ticket> findByReservationId(int reservationId);
    List<Ticket> findByReservation_Show_ShowingTimeBetween(LocalDateTime start, LocalDateTime end);




}

