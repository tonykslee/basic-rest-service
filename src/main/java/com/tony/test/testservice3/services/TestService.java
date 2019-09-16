package com.tony.test.testservice3.services;

import com.tony.test.testservice3.entities.requests.TestRequest;
import com.tony.test.testservice3.entities.responses.TestResponse;
import com.tony.test.testservice3.exceptions.BadRequestException;
import com.tony.test.testservice3.utils.helpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The type Test service.
 */
@Slf4j
@Service
public class TestService {

    /**
     * Execute test test response.
     *
     * @param request the request
     * @return the test response
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    public TestResponse executeTest(TestRequest request) throws BadRequestException, NullPointerException {
        String msisdn = request.getMsisdn();
        if (StringUtils.isEmpty(msisdn)) throw new NullPointerException("Empty msisdn");
        if (helpers.removeCountryCode(msisdn).length() != 10) {
            throw new BadRequestException(String.format("%s[%s]","Msisdn must be 10 or 11 digits long. Given msisdn: ", msisdn));
        }

        return new TestResponse("Success");
    }
}
