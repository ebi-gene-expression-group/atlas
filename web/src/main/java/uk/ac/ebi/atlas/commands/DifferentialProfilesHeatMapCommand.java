package uk.ac.ebi.atlas.commands;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.IterableObjectInputStream;
import uk.ac.ebi.atlas.streams.RankProfiles;
import uk.ac.ebi.atlas.streams.RankProfilesFactory;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilePipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesCommandOptions;
import uk.ac.ebi.atlas.streams.differential.RnaSeqInputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class DifferentialProfilesHeatMapCommand {

    private static final Logger LOGGER = Logger.getLogger(DifferentialProfilesHeatMapCommand.class);

    private RnaSeqInputStreamFactory inputStreamFactory;
    private DifferentialProfilePipelineBuilder<RnaSeqProfile> pipelineBuilder;
    private RankProfilesFactory rankProfilesFactory;

    @Inject
    public DifferentialProfilesHeatMapCommand(RnaSeqInputStreamFactory inputStreamFactory, DifferentialProfilePipelineBuilder<RnaSeqProfile> pipelineBuilder, RankProfilesFactory rankProfilesFactory) {
        this.inputStreamFactory = inputStreamFactory;
        this.pipelineBuilder = pipelineBuilder;
        this.rankProfilesFactory = rankProfilesFactory;
    }

    public DifferentialProfilesList fetch(DifferentialProfilesCommandOptions options)  {
        int maxSize = options.getHeatmapMatrixSize();
        String experimentAccession = options.getExperimentAccession();
        double cutOff = options.getCutoff();
        Regulation regulation = options.getRegulation();

        RankProfiles<RnaSeqProfile, DifferentialProfilesList<RnaSeqProfile>> rankProfiles = rankProfilesFactory.create(options);

        try (ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(experimentAccession, cutOff, regulation)) {

            Iterable<RnaSeqProfile> profiles = new IterableObjectInputStream<>(inputStream);

            Iterable<RnaSeqProfile> profilesPipeline = pipelineBuilder.build(profiles, options);

            return rankProfiles.rank(profilesPipeline, maxSize);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
