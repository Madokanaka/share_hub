package org.attractor.java_share_hub.repository;

import org.attractor.java_share_hub.model.Category;
import org.attractor.java_share_hub.model.FileEntity;
import org.attractor.java_share_hub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Page<FileEntity> findByOwner(User owner, Pageable pageable);

    Page<FileEntity> findByOwnerAndCategory(User owner, Category category, Pageable pageable);

    Page<FileEntity> findByIsPublicTrue(Pageable pageable);

    Optional<FileEntity> findByDownloadKey(String key);

    Page<FileEntity> findByCategoryAndIsPublicTrue(Category category, Pageable pageable);
}
