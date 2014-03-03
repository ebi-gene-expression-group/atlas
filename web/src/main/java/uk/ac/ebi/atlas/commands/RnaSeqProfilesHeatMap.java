package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesHeatMap;
import uk.ac.ebi.atlas.streams.differential.rnaseq.RankRnaSeqProfilesFactory;
import uk.ac.ebi.atlas.streams.differential.rnaseq.RnaSeqProfileStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqProfilesHeatMap extends DifferentialProfilesHeatMap<RnaSeqProfile, DifferentialRequestContext> {

    private RnaSeqProfileStreamFactory inputStreamFactory;
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    public RnaSeqProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<RnaSeqProfile> pipelineBuilder,
                                 RankRnaSeqProfilesFactory rankProfilesFactory,
                                 RnaSeqProfileStreamFactory inputStreamFactory,
                                 LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.loadGeneIdsIntoRequestContext = loadGeneIdsIntoRequestContext;
    }

    public DifferentialProfilesList fetch(DifferentialRequestContext requestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(requestContext, requestContext.getFilteredBySpecies());
        ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(requestContext);
        return super.fetch(inputStream, requestContext);
    }

}
