package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 10/10/15.
 */
@Named
@Scope("singleton")
public class IdentifierSearchTermsTrader {

    private static final Logger LOGGER = LogManager.getLogger(IdentifierSearchTermsTrader.class);

    private BioentityIdentifiersReader bioentityIdentifiersReader;
    private IdentifierSearchDAO identifierSearchDAO;

    @Inject
    public IdentifierSearchTermsTrader(IdentifierSearchDAO identifierSearchDAO, BioentityIdentifiersReader bioentityIdentifiersReader) {
        this.identifierSearchDAO = identifierSearchDAO;
        this.bioentityIdentifiersReader = bioentityIdentifiersReader;
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(ExperimentType experimentType) {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiments(experimentType));
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap() {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromAllExperiments());
    }

    public ImmutableMap<String, String> getBioentityIdToIdentifierSearchMap(String experimentAccession) {
        return getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiment(experimentAccession));
    }

    private ImmutableMap<String, String> getMap(Set<String> bioentityIdentifiers) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();
        for (String bioentityIdentifier : bioentityIdentifiers) {
            Set<String> propertyValueTerms = identifierSearchDAO.getProperties(bioentityIdentifier);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug(String.format("Bioentity properties for %,d bioentities fetched in %s seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds()));

        return mapBuilder.build();
    }
}
