package uk.ac.ebi.atlas.streams.differential.rnaseq;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.streams.differential.DifferentialProfilesHeatMap;
import uk.ac.ebi.atlas.streams.differential.RankDifferentialProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqProfilesHeatMap extends DifferentialProfilesHeatMap<RnaSeqProfile> {

    private RnaSeqProfileStreamFactory inputStreamFactory;

    @Inject
    public RnaSeqProfilesHeatMap(RnaSeqProfileStreamFactory inputStreamFactory,
                                 DifferentialProfileStreamPipelineBuilder<RnaSeqProfile> pipelineBuilder,
                                 RankRnaSeqProfilesFactory rankProfilesFactory) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
    }

    public DifferentialProfilesList fetch(DifferentialProfileStreamOptions options)  {
        ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(options);
        return super.fetch(inputStream, options);
    }

}
