package com.example.PLAGIARISM_SERVICE.mapper;

import com.example.PLAGIARISM_SERVICE.dto.PlagiarismCheckResponse;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismMatchResponse;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlagiarismMapper {

    @Mapping(
            target = "passed",
            expression =
                    "java(plagiarismCheck.getResult() != null && plagiarismCheck.getResult() == com.example.PLAGIARISM_SERVICE.enums.SimilarityResult.PASSED)"
    )
    @Mapping(
            target = "totalMatches",
            expression =
                    "java(plagiarismCheck.getMatches() == null ? 0 : plagiarismCheck.getMatches().size())"
    )
    @Mapping(
            target = "status",
            expression =
                    "java(plagiarismCheck.getStatus() == null ? null : plagiarismCheck.getStatus().name())"
    )
    PlagiarismCheckResponse toResponse(
            PlagiarismCheck plagiarismCheck
    );

    PlagiarismMatchResponse toMatchResponse(
            PlagiarismMatch plagiarismMatch
    );
}