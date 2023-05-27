package com.example.library.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private int id;
    private LocalDateTime timeCreated;
    private int quantity;
    private float total;
    private Integer userId;
    private String user;
    private Integer bookId;
    private String bookTitle;
    private String bookCover;
    private Boolean isPaid;
    private float bookPrice;
    private String bookAuthor;

    public BillDTO(LocalDateTime timeCreated, int quantity, float total, Integer userId, String user, int bookId, String bookTitle, String bookCover, Boolean isPaid, String bookAuthor, float bookPrice) {
        this.timeCreated = timeCreated;
        this.quantity = quantity;
        this.total = total;
        this.userId = userId;
        this.user = user;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.isPaid = isPaid;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
    }
}
