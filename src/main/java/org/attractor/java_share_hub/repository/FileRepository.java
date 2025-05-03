package org.attractor.java_share_hub.repository;

import org.attractor.java_share_hub.model.FileEntity;
import org.attractor.java_share_hub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(User owner);
    List<FileEntity> findByIsPublicTrue();
    Optional<FileEntity> findByDownloadKey(String key);
}
