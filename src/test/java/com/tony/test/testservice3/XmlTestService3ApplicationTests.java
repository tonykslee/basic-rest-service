package com.tony.test.testservice3;

import com.tony.test.testservice3.configs.ApplicationProperties;
import com.tony.test.testservice3.controllers.TestController;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import com.tony.test.testservice3.services.TestExternalService;
import com.tony.test.testservice3.external.api.XmlTestService;
import generated.XmlTestBaseResponse;
import generated.XmlTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTestService3ApplicationTests {

    @Autowired
    ApplicationProperties properties;
    TestController testController;
    TestExternalService testExternalService;
    XmlTestService xmlTestService;
    @Mock
    RestTemplate restTemplate;


    @Before
    public void setup() {
        xmlTestService = spy(new XmlTestService());
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
    public void testControllerSuccess10digit() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("1234567890");

        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        XmlTestBaseResponse.XmlTestResponse testResponse = new XmlTestBaseResponse.XmlTestResponse();
        testResponse.setStatus("Success");
        baseResponse.setXmlTestResponse(testResponse);
        doReturn(ResponseEntity.ok(baseResponse))
                .when(restTemplate)
                .exchange(contains("/external/xml/api"), any(), any(), (Class<Object>)any());

        XmlTestBaseResponse.XmlTestResponse response = testController.executeTest(request);
        assertEquals("Success", response.getStatus());
    }

    @Test
    public void testControllerSuccess11digit() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("11234567890");

        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        XmlTestBaseResponse.XmlTestResponse testResponse = new XmlTestBaseResponse.XmlTestResponse();
        testResponse.setStatus("Success");
        baseResponse.setXmlTestResponse(testResponse);
        doReturn(ResponseEntity.ok(baseResponse))
                .when(restTemplate)
                .exchange(contains("/external/xml/api"), any(), any(), (Class<Object>)any());

        XmlTestBaseResponse.XmlTestResponse response = testController.executeTest(request);
        assertEquals("Success", response.getStatus());
    }

    @Test(expected = PretendExternalApiFailureException.class)
    public void testControllerFailNullRequest() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("");

        doThrow(new NullPointerException("Empty Msisdn"))
                .when(restTemplate)
                .exchange(contains("/external/xml/api"), any(), any(), (Class<Object>)any());
        testController.executeTest(request);
    }

    @Test(expected = BadRequestException.class)
    public void testControllerFailBadRequest() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("123");

        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        XmlTestBaseResponse.XmlTestErrorResponse errorResponse = new XmlTestBaseResponse.XmlTestErrorResponse();
        errorResponse.setCode("4000");
        errorResponse.setReason("Bad Request");
        errorResponse.setExplanation("Bad Request");
        baseResponse.setXmlTestErrorResponse(errorResponse);
        doReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse))
                .when(restTemplate)
                .exchange(contains("/external/xml/api"), any(), any(), (Class<Object>)any());

        testController.executeTest(request);
    }

    @Test
    public void testService() {

    }
}
