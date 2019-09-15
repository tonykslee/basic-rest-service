package com.tmobile.cns.acms.testservice3.services;

import com.tmobile.cns.acms.testservice3.exceptions.PretendExternalApiFailureException;
import generated.XmlTestBaseResponse;
import generated.XmlTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Fake External API
 * <p>
 * DO NOT CALL ENDPOINTS WITHIN THE SAME REST API!!! THIS IS BAD PRACTICE!!!
 */
@Slf4j
@Controller
public class TestExternalService {

    private RestTemplate restTemplate;

    @Autowired
    public TestExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Pretend that this method executes a REST API call to an external service.
     *
     * @param request the request
     * @return the test external response
     */
    public XmlTestBaseResponse.XmlTestResponse executeExternalTest(XmlTestRequest request) throws NullPointerException, PretendExternalApiFailureException {
        if (request == null) throw new NullPointerException("Null External Request Body");
        long startTime = System.currentTimeMillis();

        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost")
                .port(8080)
                .path("/external")
                .path("/xml")
                .path("/api")
                .build().toUriString();
        log.info("Calling External XML API | URL: {} | Request Body: {}", url, request);
        ResponseEntity<XmlTestBaseResponse> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, createHttpEntity(request), XmlTestBaseResponse.class);
        } catch (Exception e) {
            log.error("exception thrown during api call: {}", e.getMessage(), e);
            throw new PretendExternalApiFailureException(e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new PretendExternalApiFailureException(response.getBody().toString());
        }
        XmlTestBaseResponse.XmlTestResponse externalResponse = response.getBody().getXmlTestResponse();
        log.info("{} {} | {}ms | External API Response Body: {}",
                response.getStatusCodeValue(),
                response.getStatusCode().getReasonPhrase(),
                System.currentTimeMillis() - startTime,
                externalResponse);
        return externalResponse;

    }

    private HttpEntity createHttpEntity(XmlTestRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        return new HttpEntity(request, headers);
    }
}