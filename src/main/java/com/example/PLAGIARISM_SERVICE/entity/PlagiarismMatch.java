package com.example.PLAGIARISM_SERVICE.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plagiarism_matches")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlagiarismMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "plagiarism_check_id",
            nullable = false
    )
    private PlagiarismCheck plagiarismCheck;

    @Column(nullable = false)
    private Long sourcePaperId;

    @Column(nullable = false)
    private Double similarityPercentage;

    @Lob
    private String matchingText;

    @Lob
    private String sourceExcerpt;

    private Integer sourcePage;

    private Integer targetPage;
}
