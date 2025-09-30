package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String movieTitle;
    private int durationInMinutes;

    @Enumerated(EnumType.STRING) // Man specificere i JPA
    private MovieGenre genre;

    @Enumerated(EnumType.STRING) // Man specificere i JPA
    private AgeLimit ageLimit;

    private String actors;
    private boolean featureFilm;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public AgeLimit getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(AgeLimit ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public boolean isFeatureFilm() {
        return featureFilm;
    }

    public void setFeatureFilm(boolean featureFilm) {
        this.featureFilm = featureFilm;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
