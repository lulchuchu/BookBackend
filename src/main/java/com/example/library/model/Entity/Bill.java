package com.example.library.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime timeCreated;
    private int quantity;
    private float total;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private Book book;
    private Boolean isPaid;

    public Bill(LocalDateTime timeCreated, int quantity, float total, User user, Book book, Boolean isPaid) {
        this.timeCreated = timeCreated;
        this.quantity = quantity;
        this.total = total;
        this.user = user;
        this.book = book;
        this.isPaid = isPaid;
    }
}
