package com.tmobile.cns.acms.testservice3.controllers;

import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.services.TestService;
import generated.XmlTestErrorResponse;
import generated.XmlTestRequest;
import generated.XmlTestResponse;
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
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Execute basic test api",
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = XmlTestResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = XmlTestErrorResponse.class)})
    @ResponseBody
    public XmlTestResponse executeTest(@RequestBody XmlTestRequest request) throws BadRequestException, NullPointerException {
        setupMDC("/test");
        log.info("Initial Request Body: {}", request);

        XmlTestResponse response = testService.executeTest(request);
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
