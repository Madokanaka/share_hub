package org.attractor.java_share_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private Long id;
    private String filename;
    private boolean isPublic;
    private String downloadKey;
    private int downloadCount;
    private LocalDateTime uploadDate;
    private String ownerEmail;
    private String categoryName;
}
