package uk.ac.ebi.atlas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configuration.properties")
public class DataFilesConfig {
    @Bean
    public String dataFilesLocation(@Value("${data.files.location}") String dataFilesLocation) {
        return dataFilesLocation;
    }
}
