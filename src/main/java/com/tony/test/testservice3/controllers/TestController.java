package com.tony.test.testservice3.controllers;

import com.tony.test.testservice3.entities.BaseResponse;
import com.tony.test.testservice3.entities.SuccessResponse;
import com.tony.test.testservice3.entities.requests.TestExternalRequest;
import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;
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

        SuccessResponse response = testExternalService.executeExternalTest(request);
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
        SuccessResponse response = testExternalService.executeExternalTest(request);
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
