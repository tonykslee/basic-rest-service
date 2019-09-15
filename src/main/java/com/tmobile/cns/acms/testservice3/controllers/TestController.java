package com.tmobile.cns.acms.testservice3.controllers;

import com.tmobile.cns.acms.testservice3.entities.requests.TestRequest;
import com.tmobile.cns.acms.testservice3.entities.responses.TestResponse;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import com.tmobile.cns.acms.testservice3.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping(value = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TestResponse executeTest(@RequestBody TestRequest request) throws BadRequestException {
        TestResponse response = testService.executeTest(request);
        log.info("Returning Successful Response: {}", response);
        return response;
    }
}
