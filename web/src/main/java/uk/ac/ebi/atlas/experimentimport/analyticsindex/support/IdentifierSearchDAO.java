package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.base.Joiner;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class IdentifierSearchDAO {

    private static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";
    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";

    private GxaSolrClient gxaSolrClient;
    private String[] searchProperties;

    @Inject
    public IdentifierSearchDAO(GxaSolrClient gxaSolrClient, @Value("#{configuration['index.property_names.identifier.search']}") String[] searchProperties) {
        this.gxaSolrClient = gxaSolrClient;
        this.searchProperties = searchProperties;
    }

    public Set<String> getFormattedProperties(String bioentityIdentifier) {
        int PROPERTY_LIMIT = 1000;

        SolrQuery query = new SolrQuery();

        query.setRows(PROPERTY_LIMIT);
        query.setFilterQueries(PROPERTY_NAME_FIELD + ":(\"" + Joiner.on("\" OR \"").join(searchProperties) + "\")");
        query.setFields(PROPERTY_NAME_FIELD, PROPERTY_VALUE_FIELD);
        query.setQuery(BIOENTITY_IDENTIFIER_FIELD + ":\"" + bioentityIdentifier + "\"");

        return gxaSolrClient.queryFormatted(query, false, "%s:{%s}", PROPERTY_NAME_FIELD, PROPERTY_VALUE_FIELD);
    }
}
