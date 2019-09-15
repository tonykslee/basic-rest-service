package com.tmobile.cns.acms.testservice3.exceptions.handler;


import com.tmobile.cns.acms.testservice3.entities.responses.BaseError;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.tmobile.cns.acms.testservice3.utils.helpers.buildFailResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * The type Test exception handler.
 */
@Slf4j
@ControllerAdvice
public class TestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle bad request exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
    protected BaseError handleBadRequestException(BadRequestException ex) {
        BaseError baseError = new BaseError("4000", "Bad Request", ex.getMessage());
        return baseError;
    }

    /**
     * Handle null request exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {NullPointerException.class})
    protected BaseError handleNullRequestException(NullPointerException ex) {
        BaseError baseError = new BaseError("4001", "Null Request Field", ex.getMessage());
        return baseError;
    }
}