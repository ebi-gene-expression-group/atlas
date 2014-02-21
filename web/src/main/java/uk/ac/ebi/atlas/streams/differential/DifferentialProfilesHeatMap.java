package uk.ac.ebi.atlas.streams.differential;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.streams.IterableObjectInputStream;
import uk.ac.ebi.atlas.streams.RankProfiles;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class DifferentialProfilesHeatMap<T extends DifferentialProfile> {

    private static final Logger LOGGER = Logger.getLogger(DifferentialProfilesHeatMap.class);

    private DifferentialProfileStreamFactory<T> inputStreamFactory;
    private DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder;
    private RankDifferentialProfilesFactory<T> rankProfilesFactory;

    @Inject
    public DifferentialProfilesHeatMap(DifferentialProfileStreamFactory<T> inputStreamFactory, DifferentialProfileStreamPipelineBuilder<T> pipelineBuilder, RankDifferentialProfilesFactory<T> rankProfilesFactory) {
        this.inputStreamFactory = inputStreamFactory;
        this.pipelineBuilder = pipelineBuilder;
        this.rankProfilesFactory = rankProfilesFactory;
    }

    public DifferentialProfilesList fetch(DifferentialProfileStreamOptions options)  {
        int maxSize = options.getHeatmapMatrixSize();
        String experimentAccession = options.getExperimentAccession();
        double cutOff = options.getCutoff();
        Regulation regulation = options.getRegulation();

        RankProfiles<T, DifferentialProfilesList<T>> rankProfiles = rankProfilesFactory.create(options);

        try (ObjectInputStream<T> inputStream = inputStreamFactory.create(experimentAccession, cutOff, regulation)) {

            Iterable<T> profiles = new IterableObjectInputStream<>(inputStream);

            Iterable<T> profilesPipeline = pipelineBuilder.build(profiles, options);

            return rankProfiles.rank(profilesPipeline, maxSize);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
