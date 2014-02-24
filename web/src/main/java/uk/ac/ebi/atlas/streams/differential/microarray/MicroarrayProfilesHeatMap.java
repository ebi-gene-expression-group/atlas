package uk.ac.ebi.atlas.streams.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesHeatMap;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayProfile> {

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

    public DifferentialProfilesList fetch(MicroarrayRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.loadFromAnySpecies(requestContext);
        ObjectInputStream<MicroarrayProfile> inputStream = inputStreamFactory.create(requestContext);
        return super.fetch(inputStream, requestContext);
    }

}
