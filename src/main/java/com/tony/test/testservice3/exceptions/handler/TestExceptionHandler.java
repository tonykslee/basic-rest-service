package com.tony.test.testservice3.exceptions.handler;


import com.tony.test.testservice3.exceptions.BadRequestException;
import generated.XmlTestBaseResponse;
import generated.XmlTestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The type Test exception handler.
 */
@Slf4j
@ControllerAdvice
public class TestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Build fail response entity.
     *
     * @param error         the be
     * @param httpStatus the http status
     * @return the response entity
     */
    public static ResponseEntity<XmlTestBaseResponse> buildFailResponse(XmlTestBaseResponse error, HttpStatus httpStatus) {
        ResponseEntity res = ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_XML)
                .body(error);
        log.error("failure response: {}", res);
        return res;
    }

    /**
     * Handle bad request exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<XmlTestBaseResponse> handleBadRequestException(BadRequestException ex) {
        XmlTestErrorResponse xmlTestErrorResponse = new XmlTestErrorResponse();
        xmlTestErrorResponse.setCode("4000");
        xmlTestErrorResponse.setReason("Bad Request");
        xmlTestErrorResponse.setExplanation(ex.getMessage());
        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        baseResponse.setXmlTestErrorResponse(xmlTestErrorResponse);
        return buildFailResponse(baseResponse, BAD_REQUEST);
    }

    /**
     * Handle bad request exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(value = {NullPointerException.class})
    protected ResponseEntity<XmlTestBaseResponse> handleNullException(NullPointerException ex) {
        XmlTestErrorResponse xmlTestErrorResponse = new XmlTestErrorResponse();
        xmlTestErrorResponse.setCode("4001");
        xmlTestErrorResponse.setReason("null");
        xmlTestErrorResponse.setExplanation(ex.getMessage());
        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        baseResponse.setXmlTestErrorResponse(xmlTestErrorResponse);
        return buildFailResponse(baseResponse, BAD_REQUEST);
    }
}