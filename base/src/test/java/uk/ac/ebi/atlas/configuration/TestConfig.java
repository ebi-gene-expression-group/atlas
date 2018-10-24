package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;

@Configuration
// Enabling component scanning will also load BasePathsConfig, JdbcConfig and SolrConfig, so just using this class as
// application context is enough in integration tests
@ComponentScan(basePackages = "uk.ac.ebi.atlas",
               includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = TestJdbcConfig.class),
               excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = JdbcConfig.class))
public class TestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
