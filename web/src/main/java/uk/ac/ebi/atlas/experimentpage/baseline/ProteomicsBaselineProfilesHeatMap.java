package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineProfilesHeatMap extends BaselineProfilesHeatMap {

    @Inject
    public ProteomicsBaselineProfilesHeatMap(BaselineProfileStreamPipelineBuilder pipelineBuilder,
                                             RankBaselineProfilesFactory rankProfilesFactory,
                                             ProteomicsBaselineProfileInputStreamFactory inputStreamFactory) {
        super(pipelineBuilder, rankProfilesFactory, inputStreamFactory);
    }

}
