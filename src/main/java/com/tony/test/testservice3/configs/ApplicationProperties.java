/**
 * 
 */
package com.tony.test.testservice3.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix="app")
public class ApplicationProperties {
	private String testConstant;
}
