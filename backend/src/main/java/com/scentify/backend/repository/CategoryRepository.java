package com.scentify.backend.repository;

import com.scentify.backend.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {

    private List<Category> categories = new ArrayList<>();

    public List<Category> findAll() {
        return categories;
    }

    public Optional<Category> findById(String id) {
        return categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst();
    }

    public Category save(Category category) {
        categories.add(category);
        return category;
    }

    public void deleteById(String id) {
        categories.removeIf(category -> category.getCategoryId().equals(id));
    }

    public Category update(String id, Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(id)) {
                categories.set(i, updatedCategory);
                return updatedCategory;
            }
        }
        return null;
    }
}