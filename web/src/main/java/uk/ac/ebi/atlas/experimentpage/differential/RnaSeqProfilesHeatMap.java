package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RankRnaSeqProfilesFactory;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqProfilesHeatMap extends DifferentialProfilesHeatMap<RnaSeqProfile, DifferentialRequestContext<?>> {

    @Inject
    public RnaSeqProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<RnaSeqProfile> pipelineBuilder,
                                 RankRnaSeqProfilesFactory rankProfilesFactory,
                                 RnaSeqProfileStreamFactory inputStreamFactory,
                                 SolrQueryService solrQueryService) {
        super(pipelineBuilder, rankProfilesFactory,inputStreamFactory,solrQueryService,true);
    }

}
