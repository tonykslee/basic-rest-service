package com.tony.test.testservice3.services;

import com.tony.test.testservice3.entities.requests.TestExternalRequest;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Fake External API
 *
 * DO NOT USE THIS CLASS AS AN EXAMPLE FOR YOUR DEVELOPMENT. THIS CLASS'S PURPOSE IS TO MIMIC AN EXTERNAL
 * API CALL.
 *
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
    public TestExternalResponse executeExternalTest(TestExternalRequest request) throws PretendExternalApiFailureException, NullPointerException {
        if (request == null) throw new NullPointerException("Null External Request Body");
        long startTime = System.currentTimeMillis();

        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost")
                .port(8080)
                .path("/external")
                .path("/api")
                .build().toUriString();
        log.info("Calling External API | URL: {} | Request Body: {}", url, request);
        ResponseEntity<TestExternalResponse> response = restTemplate.exchange(url, HttpMethod.POST, createHttpEntity(request), TestExternalResponse.class);

        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw new PretendExternalApiFailureException(response.getBody().toString());
        }
        TestExternalResponse externalResponse = response.getBody();
        log.info("{} {} | {}ms | External API Response Body: {}",
                response.getStatusCodeValue(),
                response.getStatusCode().getReasonPhrase(),
                System.currentTimeMillis() - startTime,
                externalResponse);
        return externalResponse;

    }

    private HttpEntity createHttpEntity(TestExternalRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(request, headers);
    }
}
