package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategory(Long categoryId);
    Category getCategory(String categoryName);
    List<Category> getAllCategories();
}
