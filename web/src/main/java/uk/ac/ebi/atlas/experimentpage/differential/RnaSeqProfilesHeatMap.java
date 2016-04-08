package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Optional;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RankRnaSeqProfilesFactory;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqProfilesHeatMap extends ProfilesHeatMap<RnaSeqProfile, DifferentialRequestContext,
        DifferentialProfilesList<RnaSeqProfile>, DifferentialProfileStreamOptions, Contrast> {

    private RnaSeqProfileStreamFactory inputStreamFactory;
    private SolrQueryService solrQueryService;

    @Inject
    public RnaSeqProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<RnaSeqProfile> pipelineBuilder,
                                 RankRnaSeqProfilesFactory rankProfilesFactory,
                                 RnaSeqProfileStreamFactory inputStreamFactory,
                                 SolrQueryService solrQueryService) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public DifferentialProfilesList<RnaSeqProfile> fetch(DifferentialRequestContext requestContext) throws GenesNotFoundException {
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext(requestContext, requestContext.getFilteredBySpecies());
        ObjectInputStream<RnaSeqProfile> inputStream = inputStreamFactory.create(requestContext);
        return super.fetch(inputStream, requestContext,geneQueryResponse);
    }

}
