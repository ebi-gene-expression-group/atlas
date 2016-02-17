package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 10/10/15.
 */
@Named
@Scope("singleton")
public class IdentifierSearchTermsTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentifierSearchTermsTrader.class);

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
            Set<String> propertyValueTerms = identifierSearchDAO.getFormattedProperties(bioentityIdentifier);
            mapBuilder.put(bioentityIdentifier, Joiner.on(" ").join(propertyValueTerms));
        }

        stopWatch.stop();
        LOGGER.debug("Bioentity properties for {} bioentities fetched in {} seconds", bioentityIdentifiers.size(), stopWatch.getTotalTimeSeconds());

        return mapBuilder.build();
    }
}
