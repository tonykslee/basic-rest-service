package com.tony.test.testservice3.controllers;

import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import com.tony.test.testservice3.services.TestExternalService;
import generated.XmlTestBaseResponse;
import generated.XmlTestRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * The type Test controller.
 */
@Slf4j
@Controller
public class TestController {

    private TestExternalService testExternalService;

    /**
     * Instantiates a new Test controller.
     *
     * @param testExternalService the test service
     */
    @Autowired
    public TestController(TestExternalService testExternalService) {
        this.testExternalService = testExternalService;
    }

    /**
     * Execute test.
     *
     * @param request the request
     * @return the test response
     * @throws NullPointerException               the null pointer exception
     * @throws PretendExternalApiFailureException the pretend external api failure exception
     */
    @PostMapping(value = "/callXmlApiTest",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Execute basic test api",
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = XmlTestBaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = XmlTestBaseResponse.class)})
    @ResponseBody
    public XmlTestBaseResponse executeTest(@RequestBody XmlTestRequest request) throws NullPointerException, PretendExternalApiFailureException, BadRequestException {
        setupMDC("/test");
        log.info("Initial Request Body: {}", request);

        XmlTestBaseResponse response = testExternalService.executeExternalTest(request);
        log.info("Returning Successful Response: {}", response);
        return response;
    }

    /**
     * Setup MDC variables.
     *
     * @param method the api endpoint that was called
     */
    private void setupMDC(String method) {
        MDC.clear();
        MDC.put("method", method);
        MDC.put("workflow", "Example Flow");
        MDC.put("transactionId", UUID.randomUUID().toString());
    }
}