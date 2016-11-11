package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

@Named
public class BioentityPropertiesDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityPropertiesDao.class);

    private GxaSolrClient gxaSolrClient;

    @Inject
    public BioentityPropertiesDao(GxaSolrClient gxaSolrClient) {
        this.gxaSolrClient = gxaSolrClient;
    }

    public ImmutableMap<String, Map<BioentityPropertyName, Set<String>>> getMap(Set<String> bioentityIdentifiers) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ImmutableMap.Builder<String, Map<BioentityPropertyName, Set<String>>> mapBuilder = new ImmutableMap.Builder<>();
        for (String bioentityIdentifier : bioentityIdentifiers) {
            mapBuilder.put(bioentityIdentifier, getMap(bioentityIdentifier));
        }

        stopWatch.stop();
        LOGGER.debug("Bioentity properties for {} bioentities fetched in {} seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds());

        return mapBuilder.build();
    }
    
    private Map<BioentityPropertyName, Set<String>> getMap(String bioentityIdentifier) {
        SolrQuery query = new SolrQuery();

        query.setRows(1000);
        query.setFilterQueries("property_name:(\"" +
                Joiner.on("\" OR \"").join(FluentIterable.from(ExperimentDataPoint.bioentityPropertyNames).transform(
                        new Function<BioentityPropertyName, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable BioentityPropertyName bioentityPropertyName) {
                                return bioentityPropertyName.name().toLowerCase();
                            }
                        })) + "\")");
        query.setFields("property_name", "property_value");
        query.setQuery(MessageFormat.format("bioentity_identifier:\"{0}\"", bioentityIdentifier));

        return gxaSolrClient.queryForProperties(query);
    }
}
