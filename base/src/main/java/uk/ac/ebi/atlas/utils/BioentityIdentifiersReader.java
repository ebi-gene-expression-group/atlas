package uk.ac.ebi.atlas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.io.IOException;
import java.util.HashSet;

public abstract class BioentityIdentifiersReader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIdentifiersReader.class);
    /*
        In this class we’re not using ImmutableSet because an immutable builder has a copy of all the elements in memory (including repeated ones)
        before flattening the set when we call builder.build() and therefore using up much more memory than needed in this case, where the number
        of repeated elements is very high.
         */
    public HashSet<String> getBioentityIdsFromExperiments(ExperimentType... experimentTypes) throws IOException {

        HashSet<String> bioentityIds = new HashSet<>();

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();
        LOGGER.info("Reading all bioentity identifiers from analytics TSV/kryo files...");

        for (ExperimentType experimentType : experimentTypes) {
            LOGGER.info("Reading all bioentity identifiers for experiments of type {}...", experimentType.getDescription());

            int count = addBioentityIdentifiers(bioentityIds, experimentType);

            LOGGER.info(
                "Finished reading all bioentity identifiers for experiments of type {}: {} bioentities added (total {})",
                experimentType.getDescription(), count, bioentityIds.size());
        }

        stopWatch.stop();
        LOGGER.info("Built a set of {} bioentity identifiers in {} seconds", bioentityIds.size(), stopWatch.getTotalTimeSeconds());

        return bioentityIds;
    }

    protected abstract int addBioentityIdentifiers(HashSet<String> bioentityIdentifiers, ExperimentType experimentType) throws IOException;

    public abstract HashSet<String> getBioentityIdsFromExperiment(String experimentAccession) throws IOException;
}
