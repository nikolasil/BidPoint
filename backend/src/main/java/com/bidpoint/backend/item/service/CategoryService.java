package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategory(UUID categoryId);
    Category getCategory(String categoryName);
    List<Category> getAllCategories();
}
