package com.example.PLAGIARISM_SERVICE.mapper;

import com.example.PLAGIARISM_SERVICE.dto.PlagiarismCheckResponse;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismMatchResponse;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlagiarismMapper {

    PlagiarismCheckResponse toResponse(PlagiarismCheck plagiarismCheck);

    PlagiarismMatchResponse toMatchResponse(PlagiarismMatch plagiarismMatch);
}