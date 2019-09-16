package com.tony.test.testservice3.services;

import com.tony.test.testservice3.exceptions.BadRequestException;
import generated.XmlTestRequest;
import generated.XmlTestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.tony.test.testservice3.utils.helpers.removeCountryCode;

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
    public XmlTestResponse executeTest(XmlTestRequest request) throws BadRequestException, NullPointerException {
        String msisdn = request.getMsisdn();
        if (StringUtils.isEmpty(msisdn)) throw new NullPointerException("Empty msisdn");
        if (removeCountryCode(msisdn).length() != 10) {
            throw new BadRequestException(String.format("%s[%s]","Msisdn must be 10 or 11 digits long. Given msisdn: ", msisdn));
        }
        XmlTestResponse response = new XmlTestResponse();
        response.setStatus("Success");
        return response;
    }
}
