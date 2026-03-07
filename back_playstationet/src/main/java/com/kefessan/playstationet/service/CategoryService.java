package com.kefessan.playstationet.service;

import org.springframework.stereotype.Service;

import com.kefessan.playstationet.model.Category;
import com.kefessan.playstationet.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository; 

    public Category createCategory(Category category) {


    if (categoryRepository.existsByName(category.getName())) {
        throw new RuntimeException("Category already exists");
    }

    return categoryRepository.save(category);
}

}
