package com.example.library.model.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author {
    @Id
    private int id;
    private String name;
    @OneToMany
    private List<Book> books;
}
