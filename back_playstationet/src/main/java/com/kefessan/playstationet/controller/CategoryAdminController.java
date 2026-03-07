package com.kefessan.playstationet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kefessan.playstationet.dto.CategoryResponseDTO;
import com.kefessan.playstationet.model.Category;
import com.kefessan.playstationet.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryRepository.save(category));
    }

    @GetMapping
public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {

    List<CategoryResponseDTO> categories = categoryRepository.findAll()
            .stream()
            .map(this::mapToDto)
            .toList();

    return ResponseEntity.ok(categories);
}
@GetMapping("/{id}")
public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {

    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    return ResponseEntity.ok(mapToDto(category));
}
@PutMapping("/{id}")
public ResponseEntity<Category> updateCategory(
        @PathVariable Long id,
        @RequestBody Category updatedCategory) {

    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    category.setName(updatedCategory.getName());

    return ResponseEntity.ok(categoryRepository.save(category));
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

    if (!categoryRepository.existsById(id)) {
        throw new RuntimeException("Category not found");
    }

    categoryRepository.deleteById(id);

    return ResponseEntity.noContent().build();
}
private CategoryResponseDTO mapToDto(Category category) {
    return CategoryResponseDTO.builder()
            .idCategory(category.getIdCategory())
            .name(category.getName())
            .build();
}
}