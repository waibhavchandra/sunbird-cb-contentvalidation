package org.sunbird.contentvalidation.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.sunbird.contentvalidation.model.ContentPdfValidation;
import org.sunbird.contentvalidation.model.ContentPdfValidationResponse;
import org.sunbird.contentvalidation.model.ProfanityWordFrequency;
import org.sunbird.contentvalidation.producer.StartContentValidationProducer;
import org.sunbird.contentvalidation.service.impl.ContentProviderRestHandlerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigsTest {

    @Mock
    private StartContentValidationProducer producer;

    @Mock
    private Configuration config;

    @InjectMocks
    private ContentProviderRestHandlerServiceImpl contentProviderRestHandlerService;

    final String CONTENTSERVICEHOST_CONST = "contentServiceHost";

    final String PROFANITY_SERVICE_CONST = "profanityServicePath";

    @Test
    void testConfig() {
        Configuration configuration = new Configuration();
        ContentPdfValidation contentPdfValidation = new ContentPdfValidation();
        contentPdfValidation.setPdfDownloadUrl("PdfDownloadURL");
        contentPdfValidation.setContentId("ContentId");
        contentPdfValidation.setFileName("FileName");
        configuration.setContentServiceHost(CONTENTSERVICEHOST_CONST);
        configuration.setProfanityServicePath(PROFANITY_SERVICE_CONST);
        assertTrue(!configuration.getContentServiceHost().isEmpty());
        assertTrue(!configuration.getProfanityServicePath().isEmpty());
        assertTrue(Constants.MINIMUL_FIELDS.contains("identifier"));
        assertEquals("PdfDownloadURL: " + "PdfDownloadURL" + ", ContentId: " + "ContentId" + ", FileName: " + "FileName", contentPdfValidation.toString());
    }

    @Test
    void consumerConfiguration() {
        ConsumerConfiguration consumerConfiguration = new ConsumerConfiguration();
        assertTrue(!consumerConfiguration.consumerConfigs().isEmpty());
        assertTrue(consumerConfiguration.kafkaListenerContainerFactory() instanceof KafkaListenerContainerFactory);
    }

    @Test
    void cassandraConfig() {
        CassandraConfig cassandraConfig = new CassandraConfig();
        assertNull(cassandraConfig.getKeyspaceName());
        assertTrue(cassandraConfig.cluster() instanceof CassandraClusterFactoryBean);
    }

    @Test
    void contentPdfValidationResponse() {
        ContentPdfValidationResponse contentPdfValidationResponse = new ContentPdfValidationResponse();
        ProfanityWordFrequency profanityWordFrequency = new ProfanityWordFrequency();
        contentPdfValidationResponse.incrementTotalPages();
        contentPdfValidationResponse.incrementProfanityWordCount();
        assertEquals(0, contentPdfValidationResponse.getProfanityWordList().size());
        contentPdfValidationResponse.addProfanityWordDetails(profanityWordFrequency);
        assertEquals(1, contentPdfValidationResponse.getProfanity_word_count());
        assertEquals(1, contentPdfValidationResponse.getTotal_pages());
        assertEquals(1, contentPdfValidationResponse.getProfanityWordList().size());
    }

    @Test
    void handleStartContentValidationRequestTest(){
        when(config.getStartContentValidationTopic()).thenReturn("topicName");
        ContentPdfValidation contentPdfValidation = new ContentPdfValidation();
        contentPdfValidation.setContentId("contentId");
        doNothing().when(producer).push(anyString(), any(ContentPdfValidation.class));
        contentProviderRestHandlerService.handleStartContentValidationRequest(contentPdfValidation);
        assertEquals("contentId", contentPdfValidation.getContentId());
    }
}
