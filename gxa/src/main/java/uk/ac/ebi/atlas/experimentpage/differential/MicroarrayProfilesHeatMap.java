package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.microarray.RankMicroarrayProfilesFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayExperiment, MicroarrayProfile,
        DifferentialRequestContext<MicroarrayExperiment>> {

    @Inject
    public MicroarrayProfilesHeatMap(RankMicroarrayProfilesFactory rankProfilesFactory,
                                     MicroarrayProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService) {
        super(rankProfilesFactory,inputStreamFactory,solrQueryService,false);
    }

}
