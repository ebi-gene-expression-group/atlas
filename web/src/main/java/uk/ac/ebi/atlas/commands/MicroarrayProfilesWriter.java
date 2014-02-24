package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.download.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesWriter;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfileStream;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfileStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;
import java.util.Set;

@Named
@Scope("prototype")
public class MicroarrayProfilesWriter extends DifferentialProfilesWriter<MicroarrayProfile> {

    private MicroarrayProfileStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public MicroarrayProfilesWriter(DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder,
                                    MicroarrayProfilesTSVWriter tsvWriter,
                                    MicroarrayProfileStreamFactory inputStreamFactory,
                                    LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, tsvWriter);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public long write(PrintWriter outputWriter, MicroarrayRequestContext requestContext, String arrayDesign) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.loadFromAnySpecies(requestContext);
        MicroarrayProfileStream inputStream = inputStreamFactory.create(requestContext, arrayDesign);
        Set<Contrast> contrasts = Sets.newHashSet(inputStream.getOrderedContrastsPresentInStream());
        return super.write(outputWriter, inputStream, requestContext, contrasts);
    }

}
