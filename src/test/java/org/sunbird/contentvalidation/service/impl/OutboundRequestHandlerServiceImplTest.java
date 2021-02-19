package org.sunbird.contentvalidation.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OutboundRequestHandlerServiceImplTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private OutboundRequestHandlerServiceImpl outboundRequestHandlerService;

    final String URI = "http://localhost:8588/profanity/check";
    HashMap<String, String> response;
    HashMap<String, String> request;
    ResponseEntity<byte[]> responseEntity = null;
    ResponseEntity<String> responseEntity1 = new ResponseEntity<String>("sampleBodyString", HttpStatus.ACCEPTED);


    @BeforeAll
    void loadDefaultObj() {
        response = new HashMap<>();
        request = new HashMap<>();
        response.put("key", "value");
        request.put("key", "value");
        String inputString = "Image byte array!";
        byte[] byteArrray = inputString.getBytes();
        responseEntity = new ResponseEntity<>(byteArrray, HttpStatus.OK);
    }

    @Test
    void fetchResultUsingPost() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        given(restTemplate.postForObject(URI, entity, Map.class)).willReturn(response);
        HashMap<String, Object> response = (HashMap<String, Object>) outboundRequestHandlerService.fetchResultUsingPost(URI, request);
        assertEquals("value", (String) response.get("key"));
    }

    @Test
    void fetchResult(){
        given(restTemplate.getForObject(URI, Map.class)).willReturn(response);
        HashMap<String, Object> response = (HashMap<String, Object>) outboundRequestHandlerService.fetchResult(URI);
        assertEquals("value", (String) response.get("key"));
    }

    @Test
    void fetchByteStream(){
        doReturn(responseEntity).when(restTemplate).exchange(ArgumentMatchers.any(java.net.URI.class), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.<HttpEntity<?>> any(),  ArgumentMatchers.<Class<byte[]>> any());
        byte [] bytes = outboundRequestHandlerService.fetchByteStream(URI);
        assertEquals(0, bytes.length);
    }
}
