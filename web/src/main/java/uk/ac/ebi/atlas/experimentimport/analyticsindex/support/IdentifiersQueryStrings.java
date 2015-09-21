package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class IdentifiersQueryStrings {

    @Value("classpath:analytics.bioentityIdentifier.faceted.query.json")
    private Resource bioentityIdentifiersFacetedQuery;

    @Value("classpath:analytics.id.faceted.query.json")
    private Resource idsFacetedQuery;

    @Value("classpath:gxa.propertyValues.faceted.query.json")
    private Resource propertyValuesFacetedQuery;

    @Bean
    public String bioentityIdentifiersFacetedQuery() {
        try (InputStream inputStream = bioentityIdentifiersFacetedQuery.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean
    public String idsFacetedQuery() {
        try (InputStream inputStream = idsFacetedQuery.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean
    public String propertyValuesFacetedQuery() {
        try (InputStream inputStream = propertyValuesFacetedQuery.getInputStream()) {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
