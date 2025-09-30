package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @OneToMany(mappedBy = "reservation")
    private List<Ticket> tickets;
}
