package com.tmobile.cns.acms.testservice3.controllers;

import com.tmobile.cns.acms.testservice3.entities.requests.TestRequest;
import com.tmobile.cns.acms.testservice3.entities.responses.BaseError;
import com.tmobile.cns.acms.testservice3.entities.responses.TestResponse;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.services.TestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
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

    private TestService testService;

    /**
     * Instantiates a new Test controller.
     *
     * @param testService the test service
     */
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    /**
     * Execute test.
     *
     * @param request the request
     * @return the test response
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    @PostMapping(value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Execute basic test api",
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = TestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = BaseError.class)})
    @ResponseBody
    public TestResponse executeTest(@RequestBody TestRequest request) throws BadRequestException, NullPointerException {
        setupMDC("/test");
        log.info("Initial Request Body: {}", request);

        TestResponse response = testService.executeTest(request);
        log.info("Returning Successful Response: {}", response);
        return response;
    }

    /**
     * Execute test without auth.
     *
     * @param request the request
     * @return the test response
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    @PostMapping(value = "/testWithoutAuth",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Execute basic test api without authentication requirement",
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = TestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = BaseError.class)})
    @ResponseBody
    public TestResponse executeTestWithoutAuth(@RequestBody TestRequest request) throws BadRequestException, NullPointerException {
        setupMDC("/testWithoutAuth");
        log.info("Initial Request Body: {}", request);
        TestResponse response = testService.executeTest(request);
        log.info("Returning Successful Response: {}", response);
        return response;
    }

    private void setupMDC(String method) {
        MDC.clear();
        MDC.put("method", method);
        MDC.put("workflow", "ACMS Example");
        MDC.put("transactionId", UUID.randomUUID().toString());

    }
}
