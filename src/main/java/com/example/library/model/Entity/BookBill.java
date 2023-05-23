package com.example.library.model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class BookBill {
    @Id
    private int id;

    @Id
    @ManyToOne
    private Book book;

    @Id
    @ManyToOne
    private Bill bill;

    private int quantity;
}
