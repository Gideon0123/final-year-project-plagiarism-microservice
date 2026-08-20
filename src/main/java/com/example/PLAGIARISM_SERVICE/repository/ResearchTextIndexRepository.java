package com.example.PLAGIARISM_SERVICE.repository;

import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearchTextIndexRepository
        extends JpaRepository<ResearchTextIndex, Long> {

    Optional<ResearchTextIndex> findByPaperId(
            Long paperId
    );

    boolean existsByPaperId(
            Long paperId
    );

    List<ResearchTextIndex> findAllByPaperIdNot(
            Long paperId
    );
}