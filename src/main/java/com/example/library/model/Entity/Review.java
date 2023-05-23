package com.example.library.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    private int id;
    private int score;
    private int content;
    private LocalDateTime date;
    @ManyToOne
    @JsonIgnore
    private Book book;
    @ManyToOne
    private User user;
}
