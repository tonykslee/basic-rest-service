package com.tony.test.testservice3.exceptions.handler;


import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.entities.responses.BaseResponse;
import com.tony.test.testservice3.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
    protected BaseResponse handleBadRequestException(BadRequestException ex) {
        BaseError baseError = new BaseError("4000", "Bad Request", ex.getMessage());
        BaseResponse baseResponse = new BaseResponse(null, baseError);
        return baseResponse;
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
    protected BaseResponse handleNullRequestException(NullPointerException ex) {
        BaseError baseError = new BaseError("4001", "Null Request Field", ex.getMessage());
        BaseResponse baseResponse = new BaseResponse(null, baseError);
        return baseResponse;
    }
}