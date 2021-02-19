package org.sunbird.contentvalidation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.model.*;
import org.sunbird.contentvalidation.repo.ContentValidationRepoServiceImpl;
import org.sunbird.contentvalidation.repo.PdfDocValidationRepository;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.service.ContentProviderService;
import org.sunbird.contentvalidation.util.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentValidationServiceImplTest {

    @Mock
    private Configuration configuration;

    @Mock
    private CommonUtils commonUtils;

    @Mock
    private OutboundRequestHandlerServiceImpl outboundRequestHandlerService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    ContentProviderService contentProviderService;

    @Spy
    ContentValidationRepoServiceImpl contentValidationRepoService;

    @Mock
    PdfDocValidationRepository pdfRepo;

    @InjectMocks
    private ContentValidationServiceImpl contentValidationService;

    final String profanityText = "1. Scumbag 2. Shit 3. Badmashi 4. Bad (Context*) 5. lazy fools 6. Liars 7. bloody liar 8. bloody\n" +
            "Chair 9. bloody fellow 10. Damn 11. Deceive 12. Darling 13. dacoits 14. bucket of shit";
    final String profanityServiceHost = "http://52.173.240.27:4001";
    final String profanityServicePath = "/checkProfanity";
    final String pdfDownloadURL = "https://igot.blob.core.windows.net/content/content/do_113155331519225856127/artifact/sample.pdf";
    final String fileName = "sample.pdf";

    InputStream inputStream;

    HashMap<String, ProfanityCategorial> possibleCategorial;

    ProfanityResponseWrapper profanityResponseWrapper;

    Profanity profanityResourse;

    @BeforeAll
    void loadConfig() throws IOException {
        possibleCategorial = new HashMap<>();
        HashMap<String, String> details = new HashMap<>();
        ProfanityCategorial profanityCategorial = new ProfanityCategorial();
        profanityCategorial.setCount(1);
        details.put("offensive", "severe");
        profanityCategorial.setDetails(details);
        possibleCategorial.put("Scumbag", profanityCategorial);
        profanityResponseWrapper = new ProfanityResponseWrapper();
        profanityResponseWrapper.setFileName(fileName);
        ClassPathResource classProfanityResource = new ClassPathResource("profanityResponse.json");
        InputStream profanityResourceInputStream = classProfanityResource.getInputStream();
        String jsonValue;
        try (final Reader reader = new InputStreamReader(profanityResourceInputStream)) {
            jsonValue = CharStreams.toString(reader);
        }
        ObjectMapper mapper1 = new ObjectMapper();
        profanityResourse = mapper1.readValue(jsonValue, Profanity.class);
    }

    @BeforeEach
    void refreshInputStream() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("offensive_images.pdf");
        inputStream = classPathResource.getInputStream();
    }


    @Test
    void getProfanityCheckForText() {
        HashMap<String, Object> requestObject = new HashMap<>();
        requestObject.put("text", profanityText);
        Profanity profanity = new Profanity();
        profanity.setText_original(profanityText);
        profanity.setText_tagged(profanityText);
        profanity.setPossible_profanity(new ArrayList<>());
        given(configuration.getProfanityServiceHost()).willReturn(profanityServiceHost);
        given(configuration.getProfanityServicePath()).willReturn(profanityServicePath);
        given(outboundRequestHandlerService.fetchResultUsingPost(profanityServiceHost.concat(profanityServicePath), requestObject)).willReturn(profanity);
        when(mapper.convertValue(any(), eq(Profanity.class))).thenReturn(profanity);
        assertEquals(contentValidationService.getProfanityCheckForText(profanityText).getText_original(), profanityText);
        assertEquals(contentValidationService.getProfanityCheckForText(profanityText).getText_tagged(), profanityText);
        assertTrue(contentValidationService.getProfanityCheckForText(profanityText).getPossible_profanity().isEmpty());
    }

    @Test
    void validatePdfContentTest() throws IOException {
        ContentPdfValidation contentPdfValidation = new ContentPdfValidation();
        PdfDocValidationResponse pdfDocValidationResponse = new PdfDocValidationResponse();
        Profanity profanity = new Profanity();
        profanity.setPossible_profanity_categorical(possibleCategorial);
        ProfanityClassification classification = new ProfanityClassification();
        classification.setProbability(1.0);
        profanity.setOverall_text_classification(classification);
        pdfDocValidationResponse.setCompleted(true);
        contentPdfValidation.setPdfDownloadUrl(pdfDownloadURL);
        contentPdfValidation.setContentId("do_113155331519225856127");
        contentPdfValidation.setFileName("sample.pdf");
        when(commonUtils.emptyCheck(anyString())).thenReturn(false);
        when(contentValidationService.getProfanityCheckForText(profanityText)).thenReturn(profanity);
        doNothing().when(contentValidationRepoService).updateContentValidationResult(any(), anyBoolean());
        when(pdfRepo.findProgressByContentIdAndPdfFileName(contentPdfValidation.getContentId(), contentPdfValidation.getFileName())).thenReturn(pdfDocValidationResponse);
        when(pdfRepo.save(any(PdfDocValidationResponse.class))).thenReturn(pdfDocValidationResponse);
        assertEquals("1, 2, 4", contentValidationService.performProfanityAnalysis(inputStream, contentPdfValidation.getFileName(), contentPdfValidation.getContentId()).getImage_occurances());
    }

    @Test
    void enrichTotalPages() {
        ContentValidationServiceImpl mockObj = mock(ContentValidationServiceImpl.class);
        mockObj.enrichTotalPages("do_113155331519225856127", "sample.pdf", 3);
        verify(mockObj, times(1)).enrichTotalPages("do_113155331519225856127", "sample.pdf", 3);
    }

    @Test
    void getTheProfanityCheckList() throws IOException {
        given(configuration.getProfanityServiceHost()).willReturn(profanityServiceHost);
        given(configuration.getProfanityServicePath()).willReturn(profanityServicePath);
        given(outboundRequestHandlerService.fetchResultUsingPost(eq(profanityServiceHost.concat(profanityServicePath)), any(Map.class))).willReturn(profanityResourse);
        when(mapper.convertValue(any(), eq(Profanity.class))).thenReturn(profanityResourse);
        assertEquals(contentValidationService.getTheProfanityCheckList(inputStream, fileName).getFileName(), fileName);
    }

    @Test
    void validatePdfContent1Test() throws IOException {
        when(contentProviderService.getContentFile(pdfDownloadURL)).thenReturn(inputStream);
        ContentPdfValidation contentPdfValidation = new ContentPdfValidation();
        contentPdfValidation.setPdfDownloadUrl(pdfDownloadURL);
        contentPdfValidation.setContentId("do_113155331519225856127");
        contentPdfValidation.setFileName("sample.pdf");
        PdfDocValidationResponse pdfDocValidationResponse = new PdfDocValidationResponse();
        Profanity profanity = new Profanity();
        profanity.setPossible_profanity_categorical(possibleCategorial);
        ProfanityClassification classification = new ProfanityClassification();
        classification.setProbability(1.0);
        profanity.setOverall_text_classification(classification);
        pdfDocValidationResponse.setCompleted(true);
        when(commonUtils.emptyCheck(anyString())).thenReturn(false);
        when(contentValidationService.getProfanityCheckForText(profanityText)).thenReturn(profanity);
        doNothing().when(contentValidationRepoService).updateContentValidationResult(any(), anyBoolean());
        when(pdfRepo.findProgressByContentIdAndPdfFileName(contentPdfValidation.getContentId(), contentPdfValidation.getFileName())).thenReturn(pdfDocValidationResponse);
        when(pdfRepo.save(any(PdfDocValidationResponse.class))).thenReturn(pdfDocValidationResponse);
        assertEquals("1, 2, 4", contentValidationService.validatePdfContent(contentPdfValidation).getImage_occurances());
    }
}
