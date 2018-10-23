package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "uk.ac.ebi.atlas")
public class AppConfig {
    private static final int MAX_TIMEOUT_MILLIS = 20000;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(MAX_TIMEOUT_MILLIS);
        requestFactory.setConnectTimeout(MAX_TIMEOUT_MILLIS);

        return new RestTemplate(requestFactory);
    }
}
