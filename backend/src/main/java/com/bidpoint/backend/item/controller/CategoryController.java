package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ConversionService conversionService;

    @PostMapping("/{categoryName}")
    public ResponseEntity<Category> createCategory(@PathVariable String categoryName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService.createCategory(
                        conversionService.convert(
                                categoryName,
                                Category.class
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<Category> getCategory(@RequestParam(name = "categoryId",required = true) Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories().stream().map(Category::getName).collect(Collectors.toList()));
    }
}
