package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.utils.ExperimentSorter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 10/10/15.
 */
@Named
public class IdentifierSearchTermsTrader {

    private static final Logger LOGGER = Logger.getLogger(IdentifierSearchTermsTrader.class);

    private static SolrClient analyticsSolrClient;
    private static GxaSolrClient gxaSolrClient;
    private static String[] searchProperties;

    private ExperimentSorter experimentSorter;

    private static String PROPERTY_VALUE_FIELD = "property_value";
    private static int FACET_LIMIT = 1000000; // Greater or equal than the count of bioentityIdentifier (2015-10-10 616,998)
    private static int PROPERTY_LIMIT = 1000;

    @Inject
    public IdentifierSearchTermsTrader(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient, GxaSolrClient gxaSolrClient,
                                       @Value("#{configuration['index.property_names.identifier.search']}") String[] searchProperties,
                                       ExperimentSorter experimentSorter) {
        this.analyticsSolrClient = analyticsSolrClient;
        this.gxaSolrClient = gxaSolrClient;
        this.searchProperties = searchProperties;
        this.experimentSorter = experimentSorter;
    }

    private LazyReference<Map<String, String>> bioentityIdToIdentifierSearch = new LazyReference<Map<String, String>>() {
        @Override
        protected Map<String, String> create() {
            ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();

//                SolrQuery query = new SolrQuery();
//                query.setQuery("*:*");
//                query.setFacet(true);
//                query.setFacetLimit(FACET_LIMIT);
//                query.addFacetField("bioentityIdentifier");
//
//                ImmutableList<String> allBioentities;
//
//                ImmutableList.Builder<String> builder = new ImmutableList.Builder<>();
//                StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
//                stopWatch.start();
//
//                FacetField bioentityIdentifierFacetField = analyticsSolrClient.query(query).getFacetField("bioentityIdentifier");
//                for (FacetField.Count bioentityIdentifierCount: bioentityIdentifierFacetField.getValues()) {
//                    builder.add(bioentityIdentifierCount.getName());
//                }
//
//                stopWatch.stop();
//                LOGGER.debug(String.format("%,d bioentities fetched in %s seconds", bioentityIdentifierFacetField.getValueCount(), stopWatch.getTotalTimeSeconds()));
//                allBioentities = builder.build();

            HashSet<String> allBioentities = experimentSorter.getBioentityIdsFromAllExperiments();

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
    };

    public String getIdentifierSearch(String bioentityIdentifier) {
        try {
            return bioentityIdToIdentifierSearch.get().get(bioentityIdentifier);
        } catch (NullPointerException e) {
            return "";
        }
    }

}
