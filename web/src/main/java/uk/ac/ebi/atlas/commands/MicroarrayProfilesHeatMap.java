package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesHeatMap;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.streams.differential.microarray.RankMicroarrayProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayProfile, MicroarrayRequestContext> {

    private MicroarrayProfileStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public MicroarrayProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder,
                                     RankMicroarrayProfilesFactory rankProfilesFactory,
                                     MicroarrayProfileStreamFactory inputStreamFactory,
                                     LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    @Override
    public DifferentialProfilesList fetch(MicroarrayRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.loadFromAnySpecies(requestContext);
        ObjectInputStream<MicroarrayProfile> inputStream = inputStreamFactory.createForAllArrayDesigns(requestContext);
        return super.fetch(inputStream, requestContext);
    }

}
