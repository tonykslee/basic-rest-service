package com.tmobile.cns.acms.testservice3;

import com.tmobile.cns.acms.testservice3.configs.ApplicationProperties;
import com.tmobile.cns.acms.testservice3.controllers.TestController;
import com.tmobile.cns.acms.testservice3.entities.requests.TestExternalRequest;
import com.tmobile.cns.acms.testservice3.entities.responses.TestExternalResponse;
import com.tmobile.cns.acms.testservice3.exceptions.PretendExternalApiFailureException;
import com.tmobile.cns.acms.testservice3.services.TestExternalService;
import com.tmobile.cns.acms.testservice3.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockExternalApiCallTest {

    @Autowired
    ApplicationProperties properties;
    TestController testController;
    TestService testService;
    @Mock
    TestExternalService testExternalService;

    @Before
    public void setup() {
        testService = spy(new TestService());
        testController = spy(new TestController(testService, testExternalService));
    }

    @Test
    public void mockExternalApiService() throws PretendExternalApiFailureException {
        TestExternalResponse mockResponse = new TestExternalResponse("Success", true);
        doReturn(mockResponse).when(testExternalService).executeExternalTest(any());


        TestExternalResponse response = testController.executeTestExternalApiCall(new TestExternalRequest(11));
        assertEquals("Success", response.getStatus());
    }
}
