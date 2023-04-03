package com.example.library.model.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Review {
    @Id
    private int id;
    private int score;
    private int content;
    @ManyToOne
    private Book book;
}
