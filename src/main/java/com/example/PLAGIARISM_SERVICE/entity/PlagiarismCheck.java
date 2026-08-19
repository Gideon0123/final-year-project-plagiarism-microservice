package com.example.PLAGIARISM_SERVICE.entity;

import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plagiarism_checks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlagiarismCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long paperId;

    @Column(nullable = false)
    private Long authorId;

    @OneToMany(
            mappedBy = "plagiarismCheck",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PlagiarismMatch> matches = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckStatus status;

    @Column(nullable = false)
    @Builder.Default
    private Double similarityPercentage = 0.0;

    @Enumerated(EnumType.STRING)
    private SimilarityResult result;

    @Column(length = 1000)
    private String summary;

    @Column(nullable = false)
    private Double thresholdPercentage;

    @Lob
    private String report;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @Column(length = 2000)
    private String errorMessage;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
