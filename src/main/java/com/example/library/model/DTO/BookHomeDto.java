package com.example.library.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookHomeDto {
    private int id;
    private String title;
    private String cover;
    private float price;
    private String authorName;
    private int quantity;
    private int sold;
    private LocalDate releaseDate;
}
