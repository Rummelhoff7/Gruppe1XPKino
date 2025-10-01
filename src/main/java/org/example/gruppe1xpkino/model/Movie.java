package org.example.gruppe1xpkino.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String img;
    private String movieTitle;
    private String actors;
    private boolean featureFilm;

    @Enumerated(EnumType.STRING) // Man specificere i JPA
    private MovieGenre genre;

    @Enumerated(EnumType.STRING) // Man specificere i JPA
    private AgeLimit ageLimit;


    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Show> shows = new ArrayList<>();

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Movie(int id, String img, String movieTitle, String actors, boolean featureFilm, MovieGenre genre, AgeLimit ageLimit, List<Show> shows) {
        this.id = id;
        this.img = img;
        this.movieTitle = movieTitle;
        this.actors = actors;
        this.featureFilm = featureFilm;
        this.genre = genre;
        this.ageLimit = ageLimit;
        this.shows = shows;
    }

    public Movie(String img, String movieTitle, String actors, boolean featureFilm, MovieGenre genre, AgeLimit ageLimit, List<Show> shows) {
        this.img = img;
        this.movieTitle = movieTitle;
        this.actors = actors;
        this.featureFilm = featureFilm;
        this.genre = genre;
        this.ageLimit = ageLimit;
        this.shows = shows;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", actors='" + actors + '\'' +
                ", featureFilm=" + featureFilm +
                ", genre=" + genre +
                ", ageLimit=" + ageLimit +
                ", shows=" + shows +
                '}';
    }
}
