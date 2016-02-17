package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.SortedSetMultimap;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.solr.query.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class BioEntityPropertyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioEntityPropertyDao.class);

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;
    private final GxaSolrClient solrServer;
    private final String[] tooltipPropertyTypes;

    private static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";
    private static final int PROPERTY_VALUES_LIMIT = 1000;

    @Inject
    public BioEntityPropertyDao(SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrClient solrServer, @Value("#{configuration['index.property_names.tooltip']}") String[] tooltipPropertyTypes) {
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrServer = solrServer;
        this.tooltipPropertyTypes = tooltipPropertyTypes;
    }

    public Set<String> fetchPropertyValuesForGeneId(String identifier, String propertyName) {
        String _identifier = identifier.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");

        SolrQuery query = solrQueryBuilderFactory.createFacetedPropertyValueQueryBuilder()
                .withPropertyNames(propertyName).buildBioentityQuery(_identifier);
        query.setFields(PROPERTY_VALUE_FIELD);
        query.setRows(PROPERTY_VALUES_LIMIT);

        return solrServer.query(query, false, PROPERTY_VALUE_FIELD);

    }


    public SortedSetMultimap<String, String> fetchTooltipProperties(String identifier) {
        return fetchProperties(identifier, tooltipPropertyTypes);

    }

    public SortedSetMultimap<String, String> fetchGenePageProperties(String identifier, String[] propertyNames) {
        SortedSetMultimap<String, String> propertiesByName = fetchProperties(identifier, propertyNames);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByName;
    }

    public boolean hasBioentityProperties(String identifier, String[] propertyNames) {
        SortedSetMultimap<String, String> propertiesByName = fetchProperties(identifier, propertyNames);

        return !propertiesByName.isEmpty();
    }

    SortedSetMultimap<String, String> fetchProperties(String bioentityIdentifier, String[] propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createFacetedPropertyValueQueryBuilder()
                .withPropertyNames(propertyNames).buildBioentityQuery(bioentityIdentifier);

        solrQuery.setRows(PROPERTY_VALUES_LIMIT);
        solrQuery.setFields(PROPERTY_VALUE_FIELD, PROPERTY_NAME_FIELD);

        LOGGER.debug("<querySolrForProperties> processing solr query: {}", solrQuery.getQuery());

        return solrServer.queryForProperties(solrQuery);

    }
}
