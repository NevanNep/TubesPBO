package com.scentify.backend.service;

import com.scentify.backend.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class categoryService {

    private List<Category> categories = new ArrayList<>();

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryById(String id) {
        Optional<Category> category = categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst();
        return category.orElse(null);
    }

    public Category createCategory(Category category) {
        categories.add(category);
        return category;
    }

    public Category updateCategory(String id, Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(id)) {
                categories.set(i, updatedCategory);
                return updatedCategory;
            }
        }
        return null;
    }

    public boolean deleteCategory(String id) {
        return categories.removeIf(category -> category.getCategoryId().equals(id));
    }
}