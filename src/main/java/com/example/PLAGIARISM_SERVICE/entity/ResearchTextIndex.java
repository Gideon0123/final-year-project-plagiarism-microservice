package com.example.PLAGIARISM_SERVICE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "research_text_index"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResearchTextIndex {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private Long paperId;

    private String title;

    private Long authorId;

    @Column(
            columnDefinition = "LONGTEXT"
    )
    private String rawText;

    @Column(
            columnDefinition = "LONGTEXT"
    )
    private String normalizedText;

    private Integer tokenCount;

    private LocalDateTime indexedAt;

    private LocalDateTime updatedAt;
}