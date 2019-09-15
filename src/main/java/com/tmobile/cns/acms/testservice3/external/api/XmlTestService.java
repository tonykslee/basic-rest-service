package com.tmobile.cns.acms.testservice3.external.api;

import com.tmobile.cns.acms.testservice3.exceptions.BadRequestException;
import generated.XmlTestBaseResponse;
import generated.XmlTestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.tmobile.cns.acms.testservice3.utils.helpers.removeCountryCode;

/**
 * The type Test service.
 */
@Slf4j
@Service
public class XmlTestService {

    /**
     * Execute test test response.
     *
     * @param request the request
     * @return the test response
     * @throws BadRequestException  the bad request exception
     * @throws NullPointerException the null pointer exception
     */
    public XmlTestBaseResponse.XmlTestResponse executeXmlTest(XmlTestRequest request) throws BadRequestException, NullPointerException {
        String msisdn = request.getMsisdn();
        if (StringUtils.isEmpty(msisdn)) throw new NullPointerException("Empty msisdn");
        if (removeCountryCode(msisdn).length() != 10) {
            throw new BadRequestException(String.format("%s[%s]","Msisdn must be 10 or 11 digits long. Given msisdn: ", msisdn));
        }
        XmlTestBaseResponse.XmlTestResponse response = new XmlTestBaseResponse.XmlTestResponse();
        response.setStatus("Success");
        return response;
    }

}
