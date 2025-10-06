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
public class SalesRestController {

    private final ShowRepository showRepository;
    private final CustomerRepository customerRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;
    private final SweetsRepository sweetsRepository;

    public SalesRestController(ShowRepository showRepository, CustomerRepository customerRepository, SeatRepository seatRepository, ReservationRepository reservationRepository, TicketRepository ticketRepository, MovieRepository movieRepository, SweetsRepository sweetsRepository) {
        this.showRepository = showRepository;
        this.customerRepository = customerRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.movieRepository = movieRepository;
        this.sweetsRepository = sweetsRepository;
    }

    @GetMapping("sweets")
    public ResponseEntity<List<Sweets>> getSweets() {
        List<Sweets> sweets = sweetsRepository.findAll();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable int id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        ShowDTO showDTO = new ShowDTO(
                show.getId(),
                show.getMovie().getMovieTitle(),
                show.getShowingTime(),
                show.getTheater().getTheaterName(),
                show.getMovie().getAgeLimit()
        );

        return ResponseEntity.ok(showDTO);
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
                        show.getTheater().getTheaterName(),
                        show.getMovie().getAgeLimit()
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
        // Get show
        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

    //  Create or get the customer
        Customer customer = new Customer();
        customer.setName(bookingRequest.getCustomer().getName());
        customer.setAge(bookingRequest.getCustomer().getAge());
        customer.setPhoneNumber(bookingRequest.getCustomer().getPhoneNumber());
        customerRepository.save(customer);

//      Create seat
        Seat seat = new Seat();
        seat.setSeatRow(bookingRequest.getSeat().getSeatRow());
        seat.setSeatNumber(bookingRequest.getSeat().getSeatNumber());
        seat.setTheater(show.getTheater());
        seatRepository.save(seat);

//      Create reservation
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setShow(show);
        reservationRepository.save(reservation);

//      Create ticket
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setReservation(reservation);
        ticket.setSeat(seat);
        ticket.setTheater(show.getTheater());

        // check age limit
        if(ticket.getCustomer().getAge() < show.getMovie().getAgeLimit().getMinAge()){
            throw new IllegalArgumentException("Customer is too young for this movie.");
        }


//      Set ticket price
        int price = 100;

        if (ticket.getTheater().getTheaterName().equals("big")) {
            price += 50;
        } else if (ticket.getTheater().getTheaterName().equals("small")) {
            price += 25;
        }

        if (show.getMovie().isFeatureFilm()) {
            price += 20;
        } else if (!show.getMovie().isFeatureFilm()) {
            price += 10;
        }

        ticket.setPrice(price);


        // check for om der allerede er købt en billet
        if (ticketRepository.existsByReservation_Show_IdAndSeat_SeatRowAndSeat_SeatNumber(
                show.getId(),
                seat.getSeatRow(),
                seat.getSeatNumber())) {
            throw new IllegalArgumentException("Seat already taken for this show.");
        }else {
            ticketRepository.save(ticket);
        }

        return ResponseEntity.ok(Map.of("ticketId", ticket.getId()));
    }
}