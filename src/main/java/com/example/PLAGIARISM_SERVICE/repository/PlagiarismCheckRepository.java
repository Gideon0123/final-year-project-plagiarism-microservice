package com.example.PLAGIARISM_SERVICE.repository;

import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlagiarismCheckRepository extends JpaRepository<PlagiarismCheck, Long> {

    List<PlagiarismCheck> findByPaperId(Long paperId);

    Optional<PlagiarismCheck> findTopByPaperIdOrderByCreatedAtDesc(Long paperId);

    Page<PlagiarismCheck> findAll(Pageable pageable);

    List<PlagiarismCheck> findByAuthorId(Long authorId);

    long countByStatus(CheckStatus status);

    Page<PlagiarismCheck> findByPaperId(Long paperId, Pageable pageable);

    Optional<PlagiarismCheck> findFirstByPaperIdOrderByCreatedAtDesc(
            Long paperId
    );
}