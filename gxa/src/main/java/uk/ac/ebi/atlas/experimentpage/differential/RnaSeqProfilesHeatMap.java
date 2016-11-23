package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RankRnaSeqProfilesFactory;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqProfilesHeatMap extends DifferentialProfilesHeatMap<DifferentialExperiment,RnaSeqProfile,
        DifferentialRequestContext<DifferentialExperiment>> {

    @Inject
    public RnaSeqProfilesHeatMap(RankRnaSeqProfilesFactory rankProfilesFactory,
                                 RnaSeqProfileStreamFactory inputStreamFactory,
                                 SolrQueryService solrQueryService) {
        super(rankProfilesFactory,inputStreamFactory,solrQueryService,true);
    }

}
