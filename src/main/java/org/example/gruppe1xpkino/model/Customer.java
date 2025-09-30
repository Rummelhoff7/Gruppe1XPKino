package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

import java.util.List;

@Entity //I tvivl om vi ville bruge den
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phoneNumber;
    private int age;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

}
