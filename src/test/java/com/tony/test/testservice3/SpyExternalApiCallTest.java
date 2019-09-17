package com.tony.test.testservice3;

import com.tony.test.testservice3.configs.ApplicationProperties;
import com.tony.test.testservice3.controllers.TestController;
import com.tony.test.testservice3.entities.SuccessResponse;
import com.tony.test.testservice3.entities.requests.TestExternalRequest;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import com.tony.test.testservice3.services.TestExternalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpyExternalApiCallTest {

    @Autowired
    ApplicationProperties properties;
    TestController testController;
    TestExternalService testExternalService;
    @Mock RestTemplate mockRestTemplate;

    @Before
    public void setup() {
        testExternalService = spy(new TestExternalService(mockRestTemplate));
        testController = spy(new TestController(testExternalService));
    }

    @Test
    public void spyExternalApiService() throws PretendExternalApiFailureException {
        TestExternalResponse mockResponse = new TestExternalResponse("Success", true);
        doReturn(ResponseEntity.ok(new SuccessResponse(mockResponse, null))).when(mockRestTemplate).exchange(contains("/external/api"), any(), any(), (Class<Object>) any());

        SuccessResponse response = testController.executeTest(new TestExternalRequest(11));
        assertEquals("Success", response.getBody().getStatus());
    }
}
