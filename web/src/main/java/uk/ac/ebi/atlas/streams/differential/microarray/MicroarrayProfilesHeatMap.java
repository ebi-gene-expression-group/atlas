package uk.ac.ebi.atlas.streams.differential.microarray;

import org.springframework.context.annotation.Scope;
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

    @Inject
    public MicroarrayProfilesHeatMap(MicroarrayProfileStreamFactory inputStreamFactory,
                                     DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder,
                                     RankMicroarrayProfilesFactory rankProfilesFactory) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
    }

    public DifferentialProfilesList fetch(MicroarrayProfileStreamOptions options)  {
        ObjectInputStream<MicroarrayProfile> inputStream = inputStreamFactory.create(options);
        return super.fetch(inputStream, options);
    }

}
