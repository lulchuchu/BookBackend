package com.example.library.model.DTO;

import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private int id;
    private int score;
    private String content;
    private LocalDateTime date;
    private int bookId;
    private String bookName;
    private int userId;
    private String userName;
}
