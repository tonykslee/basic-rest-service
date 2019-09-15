package com.tmobile.cns.acms.testservice3;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestService3Application.class);
    }

}
