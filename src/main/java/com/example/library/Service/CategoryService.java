package com.example.library.Service;

import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> showAllCategories();
    public List<Book> showBooksByCategory(Integer categoryId);
}
