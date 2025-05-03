package org.attractor.java_share_hub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.java_share_hub.dto.CategoryDto;
import org.attractor.java_share_hub.exception.ResourceNotFoundException;
import org.attractor.java_share_hub.model.Category;
import org.attractor.java_share_hub.repository.CategoryRepository;
import org.attractor.java_share_hub.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findByName(String name) {
        log.info("Find Category by name: {}", name);
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(Long id) {
        log.info("Find Category by id: {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public List<CategoryDto> findAll() {
        log.info("Find Category all");
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        log.info("Find Category by id: {}", id);
        return categoryRepository.existsById(id);
    }

}
