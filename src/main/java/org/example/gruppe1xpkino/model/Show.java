package org.example.gruppe1xpkino.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shows") // "show" is a reserved keyword in MySQL
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate showingStartDate;  // Start of the screening period
    private LocalDate showingEndDate;    // End of the screening period
    private LocalTime showingTime;       // Daily time the movie is shown

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    @JsonIgnore
    private Theater theater;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent infinite recursion in JSON serialization
    private List<Reservation> reservations;

    // Default constructor
    public Show() {}

    // Convenience constructor
    public Show(Movie movie, Theater theater, LocalDate showingStartDate, LocalDate showingEndDate, LocalTime showingTime) {
        this.movie = movie;
        this.theater = theater;
        this.showingStartDate = showingStartDate;
        this.showingEndDate = showingEndDate;
        this.showingTime = showingTime;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getShowingStartDate() { return showingStartDate; }
    public void setShowingStartDate(LocalDate showingStartDate) { this.showingStartDate = showingStartDate; }

    public LocalDate getShowingEndDate() { return showingEndDate; }
    public void setShowingEndDate(LocalDate showingEndDate) { this.showingEndDate = showingEndDate; }

    public LocalTime getShowingTime() { return showingTime; }
    public void setShowingTime(LocalTime showingTime) { this.showingTime = showingTime; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public Theater getTheater() { return theater; }
    public void setTheater(Theater theater) { this.theater = theater; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
}
