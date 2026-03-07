package com.kefessan.playstationet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kefessan.playstationet.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
boolean existsByName(String name);

}
