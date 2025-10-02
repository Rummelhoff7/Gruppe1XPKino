package org.example.gruppe1xpkino.model;

import java.time.LocalDateTime;

public class ShowDTO {
    private int showId;
    private String movieTitle;
    private LocalDateTime showingTime;
    private String theaterName;

    // constructor
    public ShowDTO(int showId, String movieTitle, LocalDateTime showingTime, String theaterName) {
        this.showId = showId;
        this.movieTitle = movieTitle;
        this.showingTime = showingTime;
        this.theaterName = theaterName;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDateTime getShowingTime() {
        return showingTime;
    }

    public void setShowingTime(LocalDateTime showingTime) {
        this.showingTime = showingTime;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
}
