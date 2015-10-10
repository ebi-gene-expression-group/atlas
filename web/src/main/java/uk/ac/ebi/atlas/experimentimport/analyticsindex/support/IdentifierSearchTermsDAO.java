package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class IdentifierSearchTermsDAO {

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;
    private final GxaSolrClient solrClient;
    private final String[] searchProperties;

    private static final String PROPERTY_VALUE_FIELD = "property_value";
    private static final int PROPERTY_VALUES_LIMIT = 1000;

    @Inject
    public IdentifierSearchTermsDAO(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrClient solrClient, @Value("#{configuration['index.property_names.identifier.search']}") String[] searchProperties) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrClient = solrClient;
        this.searchProperties = searchProperties;
    }

    public Set<String> fetchSearchTerms(String identifier) {
        SolrQuery query = solrQueryBuilderFactory.createFacetedPropertyValueQueryBuilder().withPropertyNames(searchProperties).buildBioentityQuery(identifier);
        query.setFields(PROPERTY_VALUE_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        return solrClient.query(query, PROPERTY_VALUE_FIELD, false);
    }

}
