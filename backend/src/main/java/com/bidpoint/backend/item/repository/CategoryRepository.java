package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
}
