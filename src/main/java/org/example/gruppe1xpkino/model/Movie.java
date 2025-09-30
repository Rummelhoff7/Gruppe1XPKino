package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private MovieGenre genre;
    private AgeLimit ageLimit;
    private String actors;
    @OneToMany(mappedBy = "movie")
    private List<Show> shows;
}
