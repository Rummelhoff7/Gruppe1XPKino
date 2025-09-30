package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
