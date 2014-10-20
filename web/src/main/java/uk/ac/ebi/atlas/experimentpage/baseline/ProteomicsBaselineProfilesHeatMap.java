package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfilesHeatMap extends BaselineProfilesHeatMap {

    @Inject
    public ProteomicsBaselineProfilesHeatMap(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                             RankBaselineProfilesFactory rankProfilesFactory,
                                             ProteomicsBaselineProfileInputStreamFactory inputStreamFactory,
                                             LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext) {
        super(pipelineBuilder, rankProfilesFactory, inputStreamFactory, loadGeneIdsIntoRequestContext);
    }

}
