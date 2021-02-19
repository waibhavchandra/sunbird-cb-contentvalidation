package org.sunbird.contentvalidation.repo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContentValidationRepoServiceImplTest {

    @InjectMocks
    ContentValidationRepoServiceImpl contentValidationRepoService;

    @Mock
    PdfDocValidationRepository pdfDocValidationRepository;

    final String contentId = "contentId";

    final String fileName = "sample.pdf";

    PdfDocValidationResponse pdfDocValidationResponse = new PdfDocValidationResponse();

    @Test
    void insertStartData(){
        pdfDocValidationResponse.setTotal_pages(1);
        when(pdfDocValidationRepository.findProgressByContentIdAndPdfFileName(anyString(), anyString())).thenReturn(pdfDocValidationResponse);
        when(pdfDocValidationRepository.save(any(PdfDocValidationResponse.class))).thenReturn(pdfDocValidationResponse);
        contentValidationRepoService.insertStartData(contentId, fileName);
        assertEquals(0, pdfDocValidationResponse.getTotal_pages().intValue());
    }

    @Test
    void insertStartDataFirstTime(){
        pdfDocValidationResponse.setTotal_pages(1);
        when(pdfDocValidationRepository.findProgressByContentIdAndPdfFileName(anyString(), anyString())).thenReturn(null);
        when(pdfDocValidationRepository.save(any(PdfDocValidationResponse.class))).thenReturn(pdfDocValidationResponse);
        contentValidationRepoService.insertStartData(contentId, fileName);
        assertEquals(1, pdfDocValidationResponse.getTotal_pages().intValue());
    }

    @Test
    void updateContentValidationResult(){
        PdfDocValidationResponse pdfResponse = new PdfDocValidationResponse();
        pdfResponse.setPrimaryKey(PdfDocValidationResponsePrimaryKey.builder().contentId(contentId).pdfFileName(fileName).build());
        pdfResponse.setCompleted(false);
        when(pdfDocValidationRepository.findProgressByContentIdAndPdfFileName(anyString(), anyString())).thenReturn(pdfResponse);
        when(pdfDocValidationRepository.save(any(PdfDocValidationResponse.class))).thenReturn(pdfResponse);
        contentValidationRepoService.updateContentValidationResult(pdfResponse, false);
        assertFalse(pdfResponse.isCompleted());
    }
}
