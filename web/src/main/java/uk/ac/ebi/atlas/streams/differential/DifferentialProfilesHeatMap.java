package uk.ac.ebi.atlas.streams.differential;

import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.streams.IterableObjectInputStream;
import uk.ac.ebi.atlas.streams.RankProfiles;

import java.io.IOException;

public abstract class DifferentialProfilesHeatMap<T extends DifferentialProfile, C extends DifferentialRequestContext> {

    private static final Logger LOGGER = Logger.getLogger(DifferentialProfilesHeatMap.class);

    private DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder;
    private RankDifferentialProfilesFactory<T> rankProfilesFactory;

    protected DifferentialProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder, RankDifferentialProfilesFactory<T> rankProfilesFactory) {
        this.pipelineBuilder = pipelineBuilder;
        this.rankProfilesFactory = rankProfilesFactory;
    }

    public abstract DifferentialProfilesList fetch(C requestContext) throws GenesNotFoundException;

    protected DifferentialProfilesList fetch(ObjectInputStream<T> inputStream, DifferentialProfileStreamOptions options)  {
        int maxSize = options.getHeatmapMatrixSize();

        RankProfiles<T, DifferentialProfilesList<T>> rankProfiles = rankProfilesFactory.create(options);

        try (ObjectInputStream<T> source = inputStream) {

            Iterable<T> profiles = new IterableObjectInputStream<>(source);

            Iterable<T> profilesPipeline = pipelineBuilder.build(profiles, options);

            return rankProfiles.rank(profilesPipeline, maxSize);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
