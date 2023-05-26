package com.example.library.Service.impl;
import com.example.library.Repository.CategoryRepo;
import com.example.library.Service.CategoryService;
import com.example.library.model.DTO.BookHomeDto;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> showAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if(categories.isEmpty())
            throw new RuntimeException("No categories found");
        return categories;
    }

    @Override
    public List<Book> showBooksByCategory(String categoryy) {
        Optional<Category> category = categoryRepo.findByName(categoryy);

        if(category.isEmpty())
            throw new RuntimeException("Category not found");

        List<Book> books = category.get().getBooks();
        if (books.isEmpty()) {
            throw new RuntimeException("No books found");
        }

        return books;
    }

}
