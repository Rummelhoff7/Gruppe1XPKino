package org.example.gruppe1xpkino.model;

import java.time.LocalDateTime;

public class TicketInfoDTO {

    private int ticketId;
    private String customerName;
    private int seatRow;
    private int seatNumber;
    private String movieTitle;
    private String theaterName;
    private LocalDateTime showingTime;
    private double price;

    public TicketInfoDTO(Ticket ticket) {
        this.ticketId = ticket.getId();
        this.customerName = ticket.getCustomer().getName();
        this.seatRow = ticket.getSeat().getSeatRow();
        this.seatNumber = ticket.getSeat().getSeatNumber();
        this.movieTitle = ticket.getReservation().getShow().getMovie().getMovieTitle();
        this.theaterName = ticket.getTheater().getTheaterName();
        this.showingTime = ticket.getReservation().getShow().getShowingTime();
        this.price = ticket.getPrice();
    }

    // Add getters if needed for serialization
    public int getTicketId() { return ticketId; }
    public String getCustomerName() { return customerName; }
    public int getSeatRow() { return seatRow; }
    public int getSeatNumber() { return seatNumber; }
    public String getMovieTitle() { return movieTitle; }
    public String getTheaterName() { return theaterName; }
    public LocalDateTime getShowingTime() { return showingTime; }
    public double getPrice() { return price; }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public void setShowingTime(LocalDateTime showingTime) {
        this.showingTime = showingTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
