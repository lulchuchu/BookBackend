package com.example.library.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookHomeDto {
    private int id;
    private String title;
    private String cover;
    private float price;
    private String authorName;

}
