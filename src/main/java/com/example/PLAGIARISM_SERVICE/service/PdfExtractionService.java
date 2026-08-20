package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfExtractionService {

    public String extractText(
            byte[] fileContent
    ) {
        try (PDDocument document = Loader.loadPDF(fileContent)
        ) {
            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);

        } catch (Exception ex) {
            log.error("Failed to extract text from PDF document", ex);
            throw new BadRequestException("Failed to extract PDF text");
        }
    }
}