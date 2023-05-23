package com.example.library.model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime timeCreated;
    private int total;

    @ManyToOne
    private User user;

    @OneToMany
    private List<BookBill> bookBills;

}
