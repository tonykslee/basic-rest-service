package com.tmobile.cns.acms.testservice3.utils;

import com.tmobile.cns.acms.testservice3.entities.responses.BaseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The type Helpers.
 */
@Slf4j
public class helpers {

    /**
     * Build fail response entity.
     *
     * @param be         the be
     * @param httpStatus the http status
     * @return the response entity
     */
    public static ResponseEntity<BaseError> buildFailResponse(BaseError be, HttpStatus httpStatus) {
        ResponseEntity<BaseError> res =  ResponseEntity.status(httpStatus).body(be);
        log.error("failure response: {}", res);
        return res;
    }

    /**
     * This method removes the country code from the msisdn.
     *
     * @param msisdn original msisdn.
     * @return returns the msisdn without the country code.
     */
    public static String removeCountryCode(String msisdn) {
        if (msisdn == null) return null;
        int len = msisdn.length();
        if (len == 10) return msisdn;
        if (len == 11) return msisdn.substring(1, len);
        return msisdn;
    }
}
