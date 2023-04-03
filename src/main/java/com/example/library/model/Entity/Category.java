package com.example.library.model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    private int id;
    private String name;

    @ManyToMany
    private List<Book> books;
}
