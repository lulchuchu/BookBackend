package com.example.library.Service.impl;

import com.example.library.Repository.BillRepo;
import com.example.library.Repository.BookRepo;
import com.example.library.Service.BillService;
import com.example.library.model.DTO.BillDTO;
import com.example.library.model.Entity.Bill;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepo billRepo;
    private final BookRepo bookRepo;

    public BillServiceImpl(BillRepo billRepo, BookRepo bookRepo) {
        this.billRepo = billRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<BillDTO> getAllBillInCart(User user) {
        List<Bill> bills = billRepo.findByUserIdAndIsPaidIsFalse(user.getId());
        return bills.stream().map(bill -> {
            Book book = bill.getBook();
            BillDTO billDto = new BillDTO(bill.getTimeCreated(), bill.getQuantity(), bill.getTotal(),
                    bill.getUser().getId(), bill.getUser().getUsername(), book.getId(),
                    bill.getBook().getTitle(), bill.getBook().getCover(), bill.getIsPaid(), book.getAuthor().getName(), book.getPrice());
            return billDto;
        }).toList();
    }

    @Override
    public List<BillDTO> getAllBillPaid(User user) {
        List<Bill> bills = billRepo.findByUserIdAndIsPaidIsTrue(user.getId());
        return bills.stream().map(bill -> {
            Book book = bill.getBook();
            BillDTO billDto = new BillDTO(bill.getTimeCreated(), bill.getQuantity(), bill.getTotal(),
                    bill.getUser().getId(), bill.getUser().getUsername(), book.getId(),
                    bill.getBook().getTitle(), bill.getBook().getCover(), bill.getIsPaid(), book.getAuthor().getName(), book.getPrice());
            return billDto;
        }).toList();
    }

    @Override
    public BillDTO addToCart(User user, BillDTO bill) {
        Book book = bookRepo.findById(bill.getBookId()).get();
        float total = book.getPrice() * bill.getQuantity();
        Bill newBill = new Bill(LocalDateTime.now(), bill.getQuantity(), total, user, book, false);
        billRepo.save(newBill);
        BillDTO billDto = new BillDTO(newBill.getTimeCreated(), newBill.getQuantity(), newBill.getTotal(),
                newBill.getUser().getId(), newBill.getUser().getUsername(), book.getId(),
                newBill.getBook().getTitle(), newBill.getBook().getCover(), newBill.getIsPaid(), book.getAuthor().getName(), book.getPrice());
        return billDto;
    }

    @Override
    public void removeFromCart(User user, Integer billId) {
        billRepo.deleteById(billId);
    }

    @Override
    public void pay(User user, Integer billId) {
        Bill bill = billRepo.findById(billId).get();
        bill.setIsPaid(true);
        billRepo.save(bill);
    }

}
