package uk.ac.ebi.atlas.search.analyticsindex.differential;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class DifferentialGeneQueryStrings {

    @Value("classpath:differential.gene.facets.query.json")
    private Resource differentialGeneFacetsQuery;

    @Bean
    public String differentialGeneFacetsQuery() {
        try (InputStream inputStream = differentialGeneFacetsQuery.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
