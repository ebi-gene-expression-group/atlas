package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.microarray.RankMicroarrayProfilesFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayProfile,
        DifferentialRequestContext<?>> {

    @Inject
    public MicroarrayProfilesHeatMap(RankMicroarrayProfilesFactory rankProfilesFactory,
                                     MicroarrayProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService) {
        super(rankProfilesFactory,inputStreamFactory,solrQueryService,false);
    }

}
