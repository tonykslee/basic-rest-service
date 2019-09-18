package com.tony.test.testservice3.external.api;

import com.tony.test.testservice3.exceptions.BadRequestException;
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
public class PretendExternalXmlApi {

    private XmlTestService xmlTestService;

    /**
     * Instantiates a new Test controller.
     *
     * @param xmlTestService the test service
     */
    @Autowired
    public PretendExternalXmlApi(XmlTestService xmlTestService) {
        this.xmlTestService = xmlTestService;
    }

    /**
     * Execute test.
     *
     * @param request the request
     * @return the test response
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    @PostMapping(value = "/external/xml/api",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Execute XML test api",
            notes = "Send arbitrary msisdn and receive an arbitrary response",
            response = XmlTestBaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message =
                    "1. Empty msisdn\n" +
                            "2. Msisdn must be 10 or 11 digits long.",
                    response = XmlTestBaseResponse.class)})
    @ResponseBody
    public XmlTestBaseResponse executeTest(@RequestBody XmlTestRequest request) throws BadRequestException, NullPointerException {
        setupMDC("/external/xml/api");
        log.info("Initial Request Body: {}", request);

        XmlTestBaseResponse.XmlTestResponse response = xmlTestService.executeXmlTest(request);
        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        baseResponse.setXmlTestResponse(response);
        log.info("Returning Successful Response: {}", baseResponse);
        return baseResponse;
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
