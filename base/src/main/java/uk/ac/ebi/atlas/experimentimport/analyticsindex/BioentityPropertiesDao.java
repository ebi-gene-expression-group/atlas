package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.inject.Inject;
import javax.inject.Named;
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
            mapBuilder.put(bioentityIdentifier, gxaSolrClient.getMap(bioentityIdentifier, ExperimentDataPoint.bioentityPropertyNames));
        }

        stopWatch.stop();
        LOGGER.debug("Bioentity properties for {} bioentities fetched in {} seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds());

        return mapBuilder.build();
    }

}
