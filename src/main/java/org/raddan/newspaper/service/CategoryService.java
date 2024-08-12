package org.raddan.newspaper.service;

import jakarta.transaction.Transactional;
import org.raddan.newspaper.config.updater.EntityFieldUpdater;
import org.raddan.newspaper.dto.CategoryDto;
import org.raddan.newspaper.exception.custom.CategoryAlreadyExistsException;
import org.raddan.newspaper.exception.custom.CategoryNotFoundException;
import org.raddan.newspaper.model.Category;
import org.raddan.newspaper.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Dudkin
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityFieldUpdater fieldUpdater;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           EntityFieldUpdater fieldUpdater) {
        this.categoryRepository = categoryRepository;
        this.fieldUpdater = fieldUpdater;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("Category not found");
        }

        return categories;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Transactional
    public Category create(CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(dto.getName().trim());
        if (optionalCategory.isPresent()) {
            throw new CategoryAlreadyExistsException("Category with that name already exists");
        }

        Category category = Category.builder()
                .name(dto.getName().trim())
                .build();

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(String name, CategoryDto dto) {
        Category category = getCategoryByName(name);
        fieldUpdater.update(category, dto);
        return categoryRepository.save(category);
    }

    @Transactional
    public String delete(String name) {
        Category category = getCategoryByName(name);
        categoryRepository.delete(category);
        return "Category '" + name + "' has been deleted";
    }

    public List<Category> getCategoriesByName(List<String> categoryNames) {
        List<Category> categories = categoryRepository.findByNameIn(categoryNames);
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("Category not found");
        }

        return categories;
    }

}
