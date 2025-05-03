package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.CategoryDto;
import org.attractor.java_share_hub.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByName(String name);

    Category findById(Long id);

    List<CategoryDto> findAll();

    boolean existsById(Long id);
}
