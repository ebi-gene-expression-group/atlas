package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.solr.query.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class BioEntityPropertyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioEntityPropertyDao.class);

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;
    private final GxaSolrClient solrClient;

    private static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";
    private static final int PROPERTY_VALUES_LIMIT = 1000;

    private static final BioentityPropertyName[] tooltipPropertyTypes =
            {BioentityPropertyName.SYNONYM, BioentityPropertyName.GOTERM, BioentityPropertyName.INTERPROTERM};

    @Inject
    public BioEntityPropertyDao(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrClient gxaSolrClient) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrClient = gxaSolrClient;
    }

    public Set<String> fetchPropertyValuesForGeneId(String identifier, BioentityPropertyName propertyName) {
        String _identifier = identifier.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");

        SolrQuery query = solrQueryBuilderFactory.createFacetedPropertyValueQueryBuilder()
                .withPropertyNames(propertyName).buildBioentityQuery(_identifier);
        query.setFields(PROPERTY_VALUE_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        return solrClient.query(query, false, PROPERTY_VALUE_FIELD);

    }

    public Map<BioentityPropertyName, Set<String>> fetchTooltipProperties(String identifier) {
        return fetchProperties(identifier, tooltipPropertyTypes);

    }

    public Map<BioentityPropertyName, Set<String>> fetchGenePageProperties(String identifier, BioentityPropertyName[] propertyNames) {
        Map<BioentityPropertyName, Set<String>> propertiesByName = fetchProperties(identifier, propertyNames);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByName;
    }

    private Map<BioentityPropertyName, Set<String>> fetchProperties(String bioentityIdentifier, BioentityPropertyName[] propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createFacetedPropertyValueQueryBuilder()
                .withPropertyNames(propertyNames).buildBioentityQuery(bioentityIdentifier);

        solrQuery.setRows(PROPERTY_VALUES_LIMIT);
        solrQuery.setFields(PROPERTY_VALUE_FIELD, PROPERTY_NAME_FIELD);

        LOGGER.debug("<querySolrForProperties> processing solr query: {}", solrQuery.getQuery());

        return solrClient.queryForProperties(solrQuery);

    }

}
