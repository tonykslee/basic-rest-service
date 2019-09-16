package com.tony.test.testservice3.exceptions.handler;


import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.exceptions.PretendExternalApiFailureException;
import generated.XmlTestBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * The type Test exception handler.
 */
@Slf4j
@ControllerAdvice
public class TestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Build fail response entity.
     *
     * @param error      the be
     * @param httpStatus the http status
     * @return the response entity
     */
    public static ResponseEntity<XmlTestBaseResponse> buildFailResponse(XmlTestBaseResponse.XmlTestErrorResponse error, HttpStatus httpStatus) {
        XmlTestBaseResponse baseResponse = new XmlTestBaseResponse();
        baseResponse.setXmlTestErrorResponse(error);
        ResponseEntity res = ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_XML)
                .body(baseResponse);
        log.error("failure response: {}", res);
        return res;
    }

    /**
     * Handle bad request exception response entity.
     * <p>
     * We are using ResponseEntity<XmlTestErrorResponse> as the return object so that we can set the response
     * content-type to XML format.
     *
     * @param ex the ex
     * @param wr the wr
     * @return the response entity
     */
    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<XmlTestBaseResponse> handleBadRequestException(BadRequestException ex, WebRequest wr) {
        XmlTestBaseResponse.XmlTestErrorResponse xmlTestErrorResponse = new XmlTestBaseResponse.XmlTestErrorResponse();
        xmlTestErrorResponse.setCode("4000");
        xmlTestErrorResponse.setReason("Bad Request");
        xmlTestErrorResponse.setExplanation(ex.getMessage());
        return buildFailResponse(xmlTestErrorResponse, BAD_REQUEST);
    }

    /**
     * Handle the external api failure exception.
     * <p>
     * We can use @ResponseBody because we want to return the response in JSON Format.
     *
     * @param ex the ex
     * @return base error
     */
    @ResponseBody
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {PretendExternalApiFailureException.class})
    protected BaseError handlePretendExternalApiFailureException(PretendExternalApiFailureException ex) {
        BaseError baseError = new BaseError("4002", "call to pretend external api failed", ex.getMessage());
        return baseError;
    }
}