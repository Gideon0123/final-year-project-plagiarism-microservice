package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.exceptions.BadRequestException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class DocxExtractionService {

    public String extractText(
            byte[] fileContent
    ) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
                XWPFDocument document = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document)
        ) {
            return extractor.getText();

        } catch (Exception ex) {
            log.error("Failed to extract text from DOCX document", ex);
            throw new BadRequestException("Failed to extract DOCX text");
        }
    }
}