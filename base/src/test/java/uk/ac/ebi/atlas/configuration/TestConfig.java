package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
// Enabling component scanning will also load DataFilesConfig, JdbcConfig and SolrConfig, so just using this class as
// application context is enough in integration tests
@ComponentScan(basePackages = "uk.ac.ebi.atlas")
public class TestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
