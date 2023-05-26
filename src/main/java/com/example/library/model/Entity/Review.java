package com.example.library.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    @Column(length = 10000000)
    private String content;
    private LocalDateTime date;
    @ManyToOne
    @JsonIgnore
    private Book book;
    @ManyToOne
    private User user;
}
