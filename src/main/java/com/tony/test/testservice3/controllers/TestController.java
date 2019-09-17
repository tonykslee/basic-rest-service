package com.tony.test.testservice3.controllers;

import com.tony.test.testservice3.entities.BaseResponse;
import com.tony.test.testservice3.entities.requests.TestExternalRequest;
import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;
import com.tony.test.testservice3.entities.responses.TestResponse;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import com.tony.test.testservice3.services.TestExternalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.ToString;
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

    @ToString
    private class SuccessResponse extends BaseResponse<TestExternalResponse>{
        SuccessResponse(TestExternalResponse success, BaseError error) {super(success, error);}
    }


    /**
     * Instantiates a new Test controller.
     *
     * @param testExternalService the test external service
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
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    @PostMapping(value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Execute basic test api",
            code = 200,
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = BaseResponse.class)})
    @ResponseBody
    public SuccessResponse executeTest(@RequestBody TestExternalRequest request)
            throws NullPointerException, PretendExternalApiFailureException {
        setupMDC("/test");
        log.info("Initial Request Body: {}", request);

        TestExternalResponse response = testExternalService.executeExternalTest(request);
        SuccessResponse successResponse = new SuccessResponse(response, null);
        log.info("Returning Successful Response: {}", successResponse);
        return successResponse;
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
            response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = BaseResponse.class)})
    @ResponseBody
    public SuccessResponse executeTestWithoutAuth(@RequestBody TestExternalRequest request)
            throws NullPointerException, PretendExternalApiFailureException {
        setupMDC("/testWithoutAuth");
        log.info("Initial Request Body: {}", request);
        TestExternalResponse response = testExternalService.executeExternalTest(request);
        SuccessResponse successResponse = new SuccessResponse(response, null);
        log.info("Returning Successful Response: {}", successResponse);
        return successResponse;
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
