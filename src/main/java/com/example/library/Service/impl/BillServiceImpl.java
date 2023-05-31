package com.example.library.Service.impl;

import com.example.library.Exception.NotFoundException;
import com.example.library.Exception.ResourceException;
import com.example.library.Repository.BillRepo;
import com.example.library.Repository.BookRepo;
import com.example.library.Service.BillService;
import com.example.library.model.DTO.BillDTO;
import com.example.library.model.Entity.Bill;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<Bill> bills = billRepo.findByUserIdAndIsPaidIsFalse(user.getId(), Sort.by(Sort.Direction.DESC, "timeCreated"));
        return bills.stream().map(bill -> {
            Book book = bill.getBook();
            BillDTO billDto = new BillDTO(bill.getId(), bill.getTimeCreated(), bill.getQuantity(), bill.getTotal(),
                    bill.getUser().getId(), bill.getUser().getUsername(), book.getId(),
                    bill.getBook().getTitle(), bill.getBook().getCover(), bill.getIsPaid(),  book.getPrice(), book.getAuthor().getName());
            return billDto;
        }).toList();
    }

    @Override
    public List<BillDTO> getAllBillPaid(User user) {
        List<Bill> bills = billRepo.findByUserIdAndIsPaidIsTrue(user.getId(), Sort.by(Sort.Direction.DESC, "timeCreated"));
        return bills.stream().map(bill -> {
            Book book = bill.getBook();
            BillDTO billDto = new BillDTO(bill.getId(), bill.getTimeCreated(), bill.getQuantity(), bill.getTotal(),
                    bill.getUser().getId(), bill.getUser().getUsername(), book.getId(),
                    bill.getBook().getTitle(), bill.getBook().getCover(), bill.getIsPaid(),  book.getPrice(), book.getAuthor().getName());
            return billDto;
        }).toList();
    }

    @Override
    public BillDTO addToCart(User user, BillDTO bill) {

        Optional<Book> book = bookRepo.findById(bill.getBookId());
        if (book.isEmpty()) {
            throw new NotFoundException("Book not found");
        }
        if(book.get().getQuantity() < bill.getQuantity()){
            throw new ResourceException("Not enough book in stock, only " +book.get().getQuantity()+ " left");
        }
        float total = book.get().getPrice() * bill.getQuantity();
        Bill created;
        if(billRepo.findByUserId(user.getId()).stream().anyMatch(b -> b.getBook().getId() == bill.getBookId() && !b.getIsPaid())){
            Bill oldBill = billRepo.findByBookIdAndIsPaidFalse(bill.getBookId());
            oldBill.setQuantity(oldBill.getQuantity() + bill.getQuantity());
            oldBill.setTotal(oldBill.getTotal() + total);
            oldBill.setTimeCreated(LocalDateTime.now());
            created = billRepo.saveAndFlush(oldBill);
        }else{
            Bill newBill = new Bill(LocalDateTime.now(), bill.getQuantity(), total, user, book.get(), false);
            created = billRepo.saveAndFlush(newBill);
        }

        BillDTO billDto = new BillDTO(created.getId(), created.getTimeCreated(), created.getQuantity(), created.getTotal(),
                created.getUser().getId(), created.getUser().getUsername(), book.get().getId(),
                created.getBook().getTitle(), created.getBook().getCover(), created.getIsPaid(), book.get().getPrice(), book.get().getAuthor().getName());
        return billDto;
    }

    @Override
    public void removeFromCart(User user, Integer billId) {
        billRepo.deleteById(billId);
    }

    @Override
    public void pay(User user, Integer billId) {
        Optional<Bill> bill = billRepo.findById(billId);
        if (bill.isEmpty()) {
            throw new NotFoundException("Bill not found");
        }
        Book book = bill.get().getBook();
        if(book.getQuantity() < bill.get().getQuantity()){
            throw new ResourceException("Not enough book in stock, only " +book.getQuantity()+ " left");
        }
//        if (book.getQuantity() >= bill.get().getQuantity()) {
        book.setQuantity(book.getQuantity() - bill.get().getQuantity());
        book.setSold(book.getSold() + bill.get().getQuantity());
        bill.get().setIsPaid(true);

        bookRepo.save(book);
        billRepo.save(bill.get());
    }

    @Override
    public void cancelPay(User user, Integer billId) {
        Optional<Bill> bill = billRepo.findById(billId);
        if (bill.isEmpty()) {
            throw new NotFoundException("Bill not found");
        }
        Book book = bill.get().getBook();
        book.setQuantity(book.getQuantity() + bill.get().getQuantity());
        book.setSold(book.getSold() - bill.get().getQuantity());
        billRepo.deleteById(billId);
    }

    @Override
    public void changeQuantity(User user, Integer billId, Integer quantity) {
        Optional<Bill> bill = billRepo.findById(billId);
        if (bill.isEmpty()) {
            throw new NotFoundException("Bill not found");
        }
        Book book = bill.get().getBook();
        if(book.getQuantity() < quantity){
            throw new ResourceException("Not enough book in stock, only " +book.getQuantity()+ " left");
        }
        float total = book.getPrice() * quantity;
        bill.get().setQuantity(quantity);
        bill.get().setTotal(total);
        billRepo.save(bill.get());
    }
}
