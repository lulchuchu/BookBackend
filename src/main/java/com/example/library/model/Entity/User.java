package com.example.library.model.Entity;

import com.example.library.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String address;

    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
    private List<Book> boughtBook;
}
