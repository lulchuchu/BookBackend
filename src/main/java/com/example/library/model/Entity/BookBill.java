package com.example.library.model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "book_bill", uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "bill_id"}))
public class BookBill {
    @Id
    private int id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Bill bill;

    private int quantity;
}
