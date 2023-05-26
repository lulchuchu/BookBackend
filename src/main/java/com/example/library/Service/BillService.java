package com.example.library.Service;

import com.example.library.model.DTO.BillDTO;
import com.example.library.model.Entity.User;

import java.util.List;

public interface BillService {
    List<BillDTO> getAllBillInCart(User user);

    List<BillDTO> getAllBillPaid(User user);

    BillDTO addToCart(User user, BillDTO bill);

    void removeFromCart(User user, Integer billId);

    void pay(User user, Integer billId);
}
