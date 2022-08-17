package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.item.dto.CategoryInputDto;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryInputDto category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService.createCategory(
                        conversionService.convert(
                                category,
                                Category.class
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<Category> getCategory(@RequestParam(name = "categoryId",required = true) Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
    }
}
