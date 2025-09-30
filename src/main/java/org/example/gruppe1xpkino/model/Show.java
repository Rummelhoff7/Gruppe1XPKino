package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn
    private Theater theater;

    @OneToMany(mappedBy = "show")
    private List<Reservation> reservations;

}
