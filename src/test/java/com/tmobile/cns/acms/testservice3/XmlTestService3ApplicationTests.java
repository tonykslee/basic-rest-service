package com.tmobile.cns.acms.testservice3;

import com.tmobile.cns.acms.testservice3.configs.ApplicationProperties;
import com.tmobile.cns.acms.testservice3.controllers.TestController;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.exceptions.PretendExternalApiFailureException;
import com.tmobile.cns.acms.testservice3.services.TestExternalService;
import com.tmobile.cns.acms.testservice3.external.api.XmlTestService;
import generated.XmlTestBaseResponse;
import generated.XmlTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

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

//    @Test
    public void testControllerSuccess10digit() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("1234567890");
        XmlTestBaseResponse.XmlTestResponse response = testExternalService.executeExternalTest(request);
        assertEquals("Success", response.getStatus());
    }

//    @Test
    public void testControllerSuccess11digit() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("11234567890");
        XmlTestBaseResponse.XmlTestResponse response = testExternalService.executeExternalTest(request);
        assertEquals("Success", response.getStatus());
    }

//    @Test(expected = NullPointerException.class)
    public void testControllerFailNullRequest() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("");
        testExternalService.executeExternalTest(request);
    }

//    @Test(expected = BadRequestException.class)
    public void testControllerFailBadRequest() throws BadRequestException, PretendExternalApiFailureException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("123");
        testExternalService.executeExternalTest(request);
    }

    @Test
    public void testService() {

    }
}
