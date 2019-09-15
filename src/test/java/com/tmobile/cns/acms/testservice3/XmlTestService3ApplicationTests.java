package com.tmobile.cns.acms.testservice3;

import com.tmobile.cns.acms.testservice3.configs.ApplicationProperties;
import com.tmobile.cns.acms.testservice3.controllers.XmlTestController;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.services.XmlTestService;
import generated.XmlTestRequest;
import generated.XmlTestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTestService3ApplicationTests {

    @Autowired
    ApplicationProperties properties;
    XmlTestController xmlTestController;
    XmlTestService xmlTestService;

    @Before
    public void setup() {
        xmlTestService = spy(new XmlTestService());
        xmlTestController = spy(new XmlTestController(xmlTestService));

    }

    @Test
    public void testPropertiesLoading() {
        String testConstant = properties.getTestConstant();
        log.info("testConstant: {}", testConstant);
        assertEquals("helloWorld", testConstant);
    }

    @Test
    public void testControllerSuccess10digit() throws BadRequestException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("1234567890");
        XmlTestResponse response = xmlTestController.executeTest(request);
        assertEquals("Success", response.getStatus());
    }

    @Test
    public void testControllerSuccess11digit() throws BadRequestException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("11234567890");
        XmlTestResponse response = xmlTestController.executeTest(request);
        assertEquals("Success", response.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void testControllerFailNullRequest() throws BadRequestException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("");
        xmlTestController.executeTest(request);
    }

    @Test(expected = BadRequestException.class)
    public void testControllerFailBadRequest() throws BadRequestException {
        XmlTestRequest request = new XmlTestRequest();
        request.setMsisdn("123");
        xmlTestController.executeTest(request);
    }

    @Test
    public void testService() {

    }
}
