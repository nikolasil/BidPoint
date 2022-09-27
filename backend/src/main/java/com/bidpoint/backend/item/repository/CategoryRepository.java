package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
}
