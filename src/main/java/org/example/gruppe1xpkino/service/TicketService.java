package org.example.gruppe1xpkino.service;


import org.example.gruppe1xpkino.model.Show;
import org.example.gruppe1xpkino.model.SoldTicketList;
import org.example.gruppe1xpkino.model.Ticket;
import org.example.gruppe1xpkino.repository.ShowRepository;
import org.example.gruppe1xpkino.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
    public class TicketService {

        @Autowired
        private ShowRepository showRepository;

        @Autowired
        private TicketRepository ticketRepository;

    public List<SoldTicketList> findTicketsByDate(LocalDateTime dateTime) {
        LocalDate startOfDay = dateTime.toLocalDate();
        LocalDateTime start = startOfDay.atStartOfDay();
        LocalDateTime end = startOfDay.plusDays(1).atStartOfDay();

        // Find tickets hvor tilknyttede shows vises mellem start og end
        List<Ticket> tickets = ticketRepository.findByReservation_Show_ShowingTimeBetween(start, end);

        // Konverter til DTO
        return tickets.stream().map(this::toDTO).collect(Collectors.toList());
    }


    private SoldTicketList toDTO(Ticket ticket) {
        SoldTicketList dto = new SoldTicketList();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());

        // Seat kan godt være null, tjek for det
        if (ticket.getSeat() != null) {
            dto.setSeat(ticket.getSeat().getId());
        } else {
            dto.setSeat(-1); // eller anden default-værdi
        }

        return dto;
    }}


