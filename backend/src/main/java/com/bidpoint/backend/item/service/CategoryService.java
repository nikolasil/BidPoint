package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategory(Long categoryId);
    Category getCategory(String categoryName);
    List<Category> getAllCategories();
}
