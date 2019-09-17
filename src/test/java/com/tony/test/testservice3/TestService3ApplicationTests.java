package com.tony.test.testservice3;

import com.tony.test.testservice3.configs.ApplicationProperties;
import com.tony.test.testservice3.controllers.TestController;
import com.tony.test.testservice3.entities.BaseResponse;
import com.tony.test.testservice3.entities.requests.TestExternalRequest;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import com.tony.test.testservice3.services.TestExternalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.contains;
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
public class TestService3ApplicationTests {

    @Autowired
    ApplicationProperties properties;
    TestController testController;
    TestExternalService testExternalService;
    @Mock
    RestTemplate restTemplate;

    @Before
    public void setup() {
        testExternalService = spy(new TestExternalService(restTemplate));
        testController = spy(new TestController(testExternalService));

    }

    @Test
    public void testPropertiesLoading() {
        String testConstant = properties.getTestConstant();
        log.info("testConstant: {}", testConstant);
        assertEquals("helloWorld", testConstant);
    }

    @Test
    public void testControllerFail5() throws BadRequestException, PretendExternalApiFailureException {
        TestExternalResponse expectedResponse = new TestExternalResponse("Fail", false);
        doReturn(ResponseEntity.ok(expectedResponse)).when(restTemplate).exchange(contains("/external/api"), any(), any(), (Class<Object>) any());
        TestExternalRequest request = new TestExternalRequest(5);
        BaseResponse<TestExternalResponse> response = testController.executeTest(request);
        assertEquals("Fail", response.getBody().getStatus());
    }

    @Test
    public void testControllerSuccess12() throws BadRequestException, PretendExternalApiFailureException {
        TestExternalResponse expectedResponse = new TestExternalResponse("Success", true);
        doReturn(ResponseEntity.ok(expectedResponse)).when(restTemplate).exchange(contains("/external/api"), any(), any(), (Class<Object>) any());
        TestExternalRequest request = new TestExternalRequest(12);
        BaseResponse<TestExternalResponse> response = testController.executeTest(request);
        assertEquals("Success", response.getBody().getStatus());
    }

    @Test(expected = PretendExternalApiFailureException.class)
    public void testControllerFailNullRequest() throws BadRequestException, PretendExternalApiFailureException {
        doThrow(new NullPointerException()).when(restTemplate).exchange(contains("/external/api"), any(), any(), (Class<Object>) any());
        TestExternalRequest request = new TestExternalRequest(0);
        testController.executeTest(request);
    }

    @Test
    public void testService() {

    }
}
