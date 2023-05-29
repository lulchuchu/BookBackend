package com.example.library.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String title;
    private String cover;
    @Column(length = 100000000)

    private String description;
    private LocalDate releaseDate;
    private int pages;
    private float price;

    @ManyToOne
    private Author author;
    private int quantity;
    private int sold;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "category_id"}))
    List<Category> categories;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Bill> bills;
}
