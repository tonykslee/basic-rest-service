package com.tmobile.cns.acms.testservice3.controllers;

import com.tmobile.cns.acms.testservice3.entities.requests.TestExternalRequest;
import com.tmobile.cns.acms.testservice3.entities.requests.TestRequest;
import com.tmobile.cns.acms.testservice3.entities.responses.BaseError;
import com.tmobile.cns.acms.testservice3.entities.responses.TestExternalResponse;
import com.tmobile.cns.acms.testservice3.entities.responses.TestResponse;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.exceptions.PretendExternalApiFailureException;
import com.tmobile.cns.acms.testservice3.services.TestExternalService;
import com.tmobile.cns.acms.testservice3.services.TestService;
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

    private TestService testService;
    private TestExternalService testExternalService;

    /**
     * Instantiates a new Test controller.
     *
     * @param testService         the test service
     * @param testExternalService the test external service
     */
    @Autowired
    public TestController(TestService testService, TestExternalService testExternalService) {
        this.testService = testService;
        this.testExternalService = testExternalService;
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

    /**
     * Execute test that calls an external service.
     *
     * @param request the request
     * @return the test response
     * @throws PretendExternalApiFailureException the pretend external api failure exception
     */
    @PostMapping(value = "/testExternalApiCall",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Execute test that calls an external api",
            notes = "Send arbitrary number a response based on what number it is",
            response = TestExternalResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Null External Request Body", response = BaseError.class),
            @ApiResponse(code = 500, message = "call to pretend external api failed", response = BaseError.class)})
    @ResponseBody
    public TestExternalResponse executeTestExternalApiCall(@RequestBody TestExternalRequest request)
            throws PretendExternalApiFailureException, NullPointerException {
        setupMDC("/testExternalApiCall");
        log.info("Initial Request Body: {}", request);

        TestExternalResponse response = testExternalService.executeExternalTest(request);
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
        MDC.put("workflow", "ACMS Example");
        MDC.put("transactionId", UUID.randomUUID().toString());
    }
}
