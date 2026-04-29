package io.github.guennhatking.libra_auction.controllers;

import io.github.guennhatking.libra_auction.services.CategoryService;
import io.github.guennhatking.libra_auction.viewmodels.response.CategoryResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ServerAPIResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ServerAPIResponse<List<CategoryResponse>> getCategories() {
        return ServerAPIResponse.success(categoryService.getCategories());
    }
}
