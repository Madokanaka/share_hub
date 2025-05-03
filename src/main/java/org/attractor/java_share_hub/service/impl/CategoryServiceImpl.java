package org.attractor.java_share_hub.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.java_share_hub.exception.ResourceNotFoundException;
import org.attractor.java_share_hub.model.Category;
import org.attractor.java_share_hub.repository.CategoryRepository;
import org.attractor.java_share_hub.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
