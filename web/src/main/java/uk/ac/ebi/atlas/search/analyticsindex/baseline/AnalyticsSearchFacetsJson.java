package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class AnalyticsSearchFacetsJson {

    @Value("classpath:analytics.search.facets.json")
    private Resource analyticsSearchFacetsJson;

    @Bean
    public String analyticsSearchJsonFacet() {
        try (InputStream inputStream = analyticsSearchFacetsJson.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
