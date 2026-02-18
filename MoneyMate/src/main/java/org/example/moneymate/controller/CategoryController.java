package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.CategoryDTO;
import org.example.moneymate.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Add category (JWT required)
    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO dto) {
        return categoryService.createCategory(dto);
    }


}
