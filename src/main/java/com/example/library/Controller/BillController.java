package com.example.library.Controller;

import com.example.library.Security.UserDetail;
import com.example.library.Service.BillService;
import com.example.library.model.DTO.BillDTO;
import com.example.library.model.Entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getAllBookInCart(Authentication auth){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        List<BillDTO> bills = billService.getAllBillInCart(userDetail.getUser());
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBillPaid(Authentication auth){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        List<BillDTO> bills = billService.getAllBillPaid(userDetail.getUser());
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/addtocart")
    public ResponseEntity<?> addToCart(Authentication auth, @RequestBody BillDTO bill){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        BillDTO bills = billService.addToCart(userDetail.getUser(), bill);
        return ResponseEntity.ok(bill);
    }

    @PostMapping("/removefromcart")
    public ResponseEntity<?> removeFromCart(Authentication auth, @RequestParam Integer billId){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        billService.removeFromCart(userDetail.getUser(), billId);
        return ResponseEntity.ok("Remove successfully");
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(Authentication auth, @RequestParam Integer billId){
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        billService.pay(userDetail.getUser(), billId);
        return ResponseEntity.ok("Pay successfully");
    }
}
