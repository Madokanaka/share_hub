package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.model.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByName(String name);

    Category findById(Long id);
}
