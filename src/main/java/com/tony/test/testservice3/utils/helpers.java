package com.tony.test.testservice3.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * The type Helpers.
 */
@Slf4j
public class helpers {

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
