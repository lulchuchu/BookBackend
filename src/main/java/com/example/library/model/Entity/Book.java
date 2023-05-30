package com.example.library.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Book(String title, String cover, String description, LocalDate releaseDate, int pages, float price, Author author, int quantity, List<Category> categories) {
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.price = price;
        this.author = author;
        this.quantity = quantity;
        this.categories = categories;
    }
}
