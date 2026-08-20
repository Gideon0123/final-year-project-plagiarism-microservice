package com.example.PLAGIARISM_SERVICE.mapper;

import com.example.PLAGIARISM_SERVICE.dto.ResearchTextIndexResponse;
import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface ResearchTextIndexMapper {

    ResearchTextIndexResponse toResponse(
            ResearchTextIndex entity
    );
}
