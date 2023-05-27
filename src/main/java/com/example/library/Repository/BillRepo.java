package com.example.library.Repository;

import com.example.library.model.Entity.Bill;
import com.example.library.model.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer>{

    List<Bill> findByUserId(Integer userId);
    List<Bill> findByUserIdAndIsPaidIsFalse(Integer userId);
    List<Bill> findByUserIdAndIsPaidIsTrue(Integer userId);
    Bill findByBookIdAndIsPaidFalse(Integer bookId);

}
