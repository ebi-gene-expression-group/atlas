package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Named
public class IdentifierSearchTermsTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifierSearchTermsTrader.class);

    private BioentityIdentifiersReader bioentityIdentifiersReader;
    private IdentifierSearchDAO identifierSearchDAO;
    private GxaSolrClient gxaSolrClient;

    @Inject
    public IdentifierSearchTermsTrader(IdentifierSearchDAO identifierSearchDAO, GxaSolrClient gxaSolrClient,
                                       BioentityIdentifiersReader bioentityIdentifiersReader) {
        this.identifierSearchDAO = identifierSearchDAO;
        this.bioentityIdentifiersReader = bioentityIdentifiersReader;
        this.gxaSolrClient = gxaSolrClient;
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(ExperimentType experimentType) {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiments(experimentType));
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap() {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiments(ExperimentType.all()));
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(String experimentAccession) {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiment(experimentAccession));
    }

    public ImmutableMap<String, Map<BioentityPropertyName, Collection<String>>> getBioentityIdToIdentifierSearchMap2(ExperimentType
                                                                                                 experimentType) {
        return getMap2(bioentityIdentifiersReader.getBioentityIdsFromExperiments(experimentType));
    }

    public ImmutableMap<String, Map<BioentityPropertyName, Collection<String>>> getBioentityIdToIdentifierSearchMap2() {
        return getMap2(bioentityIdentifiersReader.getBioentityIdsFromExperiments(ExperimentType.all()));
    }

    public ImmutableMap<String, Map<BioentityPropertyName, Collection<String>>> getBioentityIdToIdentifierSearchMap2(String experimentAccession) {
        return getMap2(bioentityIdentifiersReader.getBioentityIdsFromExperiment(experimentAccession));
    }

    public ImmutableMap<String, Map<BioentityPropertyName, Collection<String>>> getMap2(Set<String> bioentityIdentifiers) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ImmutableMap.Builder<String, Map<BioentityPropertyName, Collection<String>>> mapBuilder = new ImmutableMap.Builder<>();
        for (String bioentityIdentifier : bioentityIdentifiers) {
            mapBuilder.put(bioentityIdentifier, getMap2(bioentityIdentifier));
        }

        stopWatch.stop();
        LOGGER.debug("Bioentity properties for {} bioentities fetched in {} seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds());

        return mapBuilder.build();
    }
    
    private Map<BioentityPropertyName, Collection<String>> getMap2(String bioentityIdentifier){
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
                        }))+ "\")");
        query.setFields("property_name", "property_value");
        query.setQuery(MessageFormat.format("bioentity_identifier:\"{0}\"", bioentityIdentifier));
        
        return gxaSolrClient.queryForProperties2(query).asMap();
    }

    private ImmutableMap<String, String> getMap(Set<String> bioentityIdentifiers) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();
        for (String bioentityIdentifier : bioentityIdentifiers) {
            Set<String> propertyValueTerms = identifierSearchDAO.getFormattedProperties(bioentityIdentifier);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug("Bioentity properties for {} bioentities fetched in {} seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds());

        return mapBuilder.build();
    }
}
