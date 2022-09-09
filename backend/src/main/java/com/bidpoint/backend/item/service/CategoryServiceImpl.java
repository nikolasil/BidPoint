package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
   private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(UUID categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    @Override
    public Category getCategory(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
