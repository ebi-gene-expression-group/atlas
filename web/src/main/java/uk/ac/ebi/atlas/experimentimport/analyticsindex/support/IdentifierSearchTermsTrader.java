package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 10/10/15.
 */
@Named
@Scope("singleton")
public class IdentifierSearchTermsTrader {

    private static final Logger LOGGER = Logger.getLogger(IdentifierSearchTermsTrader.class);

    private GxaSolrClient gxaSolrClient;
    private String[] searchProperties;

    private BioentityIdentifiersReader bioentityIdentifiersReader;

    @Inject
    public IdentifierSearchTermsTrader(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient, GxaSolrClient gxaSolrClient,
                                       @Value("#{configuration['index.property_names.identifier.search']}") String[] searchProperties,
                                       BioentityIdentifiersReader bioentityIdentifiersReader) {
        this.gxaSolrClient = gxaSolrClient;
        this.searchProperties = searchProperties;
        this.bioentityIdentifiersReader = bioentityIdentifiersReader;
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(ExperimentType experimentType) {
        int PROPERTY_LIMIT = 1000;
        String PROPERTY_VALUE_FIELD = "property_value";
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        HashSet<String> allBioentities = bioentityIdentifiersReader.getBioentityIdsFromExperiments(experimentType);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SolrQuery query = new SolrQuery();

        query.setRows(PROPERTY_LIMIT);
        query.setFilterQueries("property_name:(\"" + Joiner.on("\" OR \"").join(searchProperties) + "\")");
        query.setFields(PROPERTY_VALUE_FIELD);
        for (String bioentityIdentifier : allBioentities) {
            query.setQuery("bioentity_identifier:\"" + bioentityIdentifier + "\"");
            Set<String> propertyValueTerms = gxaSolrClient.query(query, PROPERTY_VALUE_FIELD, false);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug(String.format("Bioentity properties for %,d bioentities fetched in %s seconds", allBioentities.size(), stopWatch.getTotalTimeSeconds()));

        return mapBuilder.build();
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap() {
        int PROPERTY_LIMIT = 1000;
        String PROPERTY_VALUE_FIELD = "property_value";
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        HashSet<String> allBioentities = bioentityIdentifiersReader.getBioentityIdsFromAllExperiments();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SolrQuery query = new SolrQuery();

        query.setRows(PROPERTY_LIMIT);
        query.setFilterQueries("property_name:(\"" + Joiner.on("\" OR \"").join(searchProperties) + "\")");
        query.setFields(PROPERTY_VALUE_FIELD);
        for (String bioentityIdentifier : allBioentities) {
            query.setQuery("bioentity_identifier:\"" + bioentityIdentifier + "\"");
            Set<String> propertyValueTerms = gxaSolrClient.query(query, PROPERTY_VALUE_FIELD, false);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug(String.format("Bioentity properties for %,d bioentities fetched in %s seconds", allBioentities.size(), stopWatch.getTotalTimeSeconds()));

        return mapBuilder.build();
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(String experimentAccession) {
        int PROPERTY_LIMIT = 1000;
        String PROPERTY_VALUE_FIELD = "property_value";
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

        HashSet<String> allBioentities = bioentityIdentifiersReader.getBioentityIdsFromExperiment(experimentAccession);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SolrQuery query = new SolrQuery();

        query.setRows(PROPERTY_LIMIT);
        query.setFilterQueries("property_name:(\"" + Joiner.on("\" OR \"").join(searchProperties) + "\")");
        query.setFields(PROPERTY_VALUE_FIELD);
        for (String bioentityIdentifier : allBioentities) {
            query.setQuery("bioentity_identifier:\"" + bioentityIdentifier + "\"");
            Set<String> propertyValueTerms = gxaSolrClient.query(query, PROPERTY_VALUE_FIELD, false);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug(String.format("Bioentity properties for %,d bioentities fetched in %s seconds", allBioentities.size(), stopWatch.getTotalTimeSeconds()));

        return mapBuilder.build();
    }

}
