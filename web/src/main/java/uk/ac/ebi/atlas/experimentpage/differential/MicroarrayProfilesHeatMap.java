package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Optional;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.microarray.RankMicroarrayProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayProfilesHeatMap extends DifferentialProfilesHeatMap<MicroarrayProfile,
        DifferentialRequestContext<?>> {

    @Inject
    public MicroarrayProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder,
                                     RankMicroarrayProfilesFactory rankProfilesFactory,
                                     MicroarrayProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService) {
        super(pipelineBuilder, rankProfilesFactory,inputStreamFactory,solrQueryService,false);
    }

}
