package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.download.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.ProfilesWriter;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStream;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@Scope("prototype")
public class RnaSeqProfilesWriter extends ProfilesWriter<RnaSeqProfile, Contrast> {

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
        RnaSeqProfileStream inputStream = inputStreamFactory.create(requestContext);
        return super.write(outputWriter, inputStream, requestContext, requestContext.getExperiment().getContrasts());
    }

}
