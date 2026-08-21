package com.example.PLAGIARISM_SERVICE.repository;

import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlagiarismMatchRepository extends JpaRepository<PlagiarismMatch, Long> {

    List<PlagiarismMatch> findByPlagiarismCheckId(Long plagiarismCheckId);

    Page<PlagiarismMatch> findByPlagiarismCheckId(
            Long checkId,
            Pageable pageable
    );

}