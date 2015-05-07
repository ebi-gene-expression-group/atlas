package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfilesTsvInputStream;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqProfilesTSVWriter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class RnaSeqProfilesWriter extends ProfilesWriter<RnaSeqProfile, Contrast, DifferentialProfileStreamOptions> {

    private RnaSeqProfileStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public RnaSeqProfilesWriter(DifferentialProfileStreamPipelineBuilder<RnaSeqProfile> pipelineBuilder,
                                RnaSeqProfilesTSVWriter tsvWriter,
                                RnaSeqProfileStreamFactory inputStreamFactory,
                                LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public long write(PrintWriter outputWriter, RnaSeqRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());
        RnaSeqProfilesTsvInputStream inputStream = inputStreamFactory.create(requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getExperiment().getContrasts());
    }

}
