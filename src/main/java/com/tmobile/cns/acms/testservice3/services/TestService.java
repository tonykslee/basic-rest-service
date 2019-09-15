package com.tmobile.cns.acms.testservice3.services;

import com.tmobile.cns.acms.testservice3.entities.requests.TestRequest;
import com.tmobile.cns.acms.testservice3.entities.responses.TestResponse;
import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.tmobile.cns.acms.testservice3.utils.helpers.removeCountryCode;

@Slf4j
@Service
public class TestService {

    public TestResponse executeTest(TestRequest request) throws BadRequestException {
        String msisdn = request.getMsisdn();
        if (StringUtils.isEmpty(msisdn)) throw new NullPointerException("Empty msisdn");
        if (removeCountryCode(msisdn).length() != 10) {
            throw new BadRequestException(String.format("%s[%s]","Msisdn must be 10 or 11 digits long. Given msisdn: ", msisdn));
        }

        return new TestResponse("Success");
    }
}
