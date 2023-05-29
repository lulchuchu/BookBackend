package com.example.library.model.DTO;

import com.example.library.model.Entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDTO {
    private int id;
    private String title;
    private String cover;
    private String description;
    private LocalDate releaseDate;
    private int pages;
    private float price;
    private String authorName;
    private int quantity;
    List<Category> categories;
    private int sold;
}
