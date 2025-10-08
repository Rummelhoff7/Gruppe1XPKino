package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.ShowDTO;
import org.example.gruppe1xpkino.model.SoldTicketList;
import org.example.gruppe1xpkino.model.Ticket;
import org.example.gruppe1xpkino.model.TicketInfoDTO;
import org.example.gruppe1xpkino.repository.*;
import org.example.gruppe1xpkino.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;


import java.time.LocalDate;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping ("/api")
public class InspectorRestController {
    private final ShowRepository showRepository;
    private final CustomerRepository customerRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final SweetsRepository sweetsRepository;
    private final TicketService ticketService;

    public InspectorRestController(ShowRepository showRepository, CustomerRepository customerRepository, SeatRepository seatRepository, ReservationRepository reservationRepository, TicketRepository ticketRepository, MovieRepository movieRepository, SweetsRepository sweetsRepository, TicketService ticketService) {
        this.showRepository = showRepository;
        this.customerRepository = customerRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.movieRepository = movieRepository;
        this.sweetsRepository = sweetsRepository;
        this.ticketService = ticketService;
    }

    @GetMapping("/clean")
    public Map<String, String> getCleanStatus() {
        return Map.of("status", "CLEAN");
    }

    @GetMapping("/tickets")
    public List<Map<String, Object>> getTickets() {
        return List.of(
                Map.of("id", 1, "price", 120.0),
                Map.of("id", 2, "price", 90.0)
        );
    }

    @GetMapping("/tickets/by-date")
    public List<SoldTicketList> getTicketsByDate(@RequestParam("dato") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dato) {
        return ticketService.findTicketsByDate(dato);
    }


    @PostMapping("/gem-dato")
    public ResponseEntity<String> gemDato(@RequestBody Map<String, String> body) {
        String isoDate = body.get("showingTime");

        try {
            OffsetDateTime offsetTime = OffsetDateTime.parse(isoDate); // ISO 8601
            LocalDateTime showingTime = offsetTime.toLocalDateTime();

            // Her kan du f.eks. gemme showingTime i en database, session eller hvor du vil
            System.out.println("Modtaget og gemt: " + showingTime);

            return ResponseEntity.ok("Dato gemt");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Ugyldig dato");
        }
    }

    @GetMapping("/tickets/by-date-time")
    public List<TicketInfoDTO> getTicketsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Ticket> tickets = ticketRepository.findByReservation_Show_ShowingTimeBetween(startOfDay, endOfDay);

        return tickets.stream().map(t -> new TicketInfoDTO(t)).collect(toList());


    }

}




