package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.*;
import org.example.gruppe1xpkino.repository.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
public class ShowRestController {

    private final ShowRepository showRepository;
    private final CustomerRepository customerRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;

    public ShowRestController(ShowRepository showRepository, CustomerRepository customerRepository, TheaterRepository theaterRepository, SeatRepository seatRepository, ReservationRepository reservationRepository, TicketRepository ticketRepository) {
        this.showRepository = showRepository;
        this.customerRepository = customerRepository;
        this.theaterRepository = theaterRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/by-date")
    public List<ShowDTO> getShowsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Show> shows = showRepository.findByShowingTimeBetween(startOfDay, endOfDay);

        // Convert to DTO to avoid sending full entity graphs
        return shows.stream()
                .map(show -> new ShowDTO(
                        show.getId(), // showId
                        show.getMovie().getMovieTitle(),
                        show.getShowingTime(),
                        show.getTheater().getTheaterName()
                ))
                .toList();
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketInfoDTO> getTicketInfo(@PathVariable int id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return ResponseEntity.ok(new TicketInfoDTO(ticket));
    }

    @PostMapping("/reservations/book")
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest bookingRequest) {
        // 1. Get the show (to access theater, etc.)
        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

// 2. Create or get the customer
        Customer customer = new Customer();
        customer.setName(bookingRequest.getCustomer().getName());
        customer.setAge(bookingRequest.getCustomer().getAge());
        customer.setPhoneNumber(bookingRequest.getCustomer().getPhoneNumber());
        customerRepository.save(customer);

// 3. Create the seat
        Seat seat = new Seat();
        seat.setSeatRow(bookingRequest.getSeat().getSeatRow());
        seat.setSeatNumber(bookingRequest.getSeat().getSeatNumber());
        seat.setTheater(show.getTheater()); // ✅ from show
        seatRepository.save(seat);

// ✅ 4. Now create the reservation — this is the missing step in your case
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setShow(show);
        reservationRepository.save(reservation);

// 5. Now create the ticket
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setReservation(reservation); // ✅ should now be resolved
        ticket.setSeat(seat);
        ticket.setTheater(show.getTheater());
        ticket.setPrice(100); // example
        ticketRepository.save(ticket);

        return ResponseEntity.ok(Map.of("ticketId", ticket.getId()));
    }
}