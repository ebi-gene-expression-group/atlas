package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Optional;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;
import uk.ac.ebi.atlas.profiles.differential.microarray.DifferentialProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;

public class DifferentialProfilesHeatMap<P extends DifferentialProfile, R extends DifferentialRequestContext<?>>
        extends
        ProfilesHeatMap<P, R,
        DifferentialProfilesList<P>, DifferentialProfileStreamOptions, Contrast> {

    DifferentialProfileStreamFactory inputStreamFactory;
    SolrQueryService solrQueryService;
    private final boolean queryBySpecies;

    @Inject
    public DifferentialProfilesHeatMap(DifferentialProfileStreamPipelineBuilder<P> pipelineBuilder,
                                     RankProfilesFactory<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions>
                                             rankProfilesFactory,
                                       DifferentialProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService,boolean queryBySpecies) {
        super(pipelineBuilder, rankProfilesFactory);
        this.inputStreamFactory = inputStreamFactory;
        this.solrQueryService = solrQueryService;
        this.queryBySpecies = queryBySpecies;
    }

    public DifferentialProfilesList<P> fetch(R requestContext) throws GenesNotFoundException {
        Optional<GeneQueryResponse> geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (requestContext, queryBySpecies ? requestContext.getFilteredBySpecies(): "");
        ObjectInputStream<P> inputStream = inputStreamFactory.create(requestContext);
        return super.fetch(inputStream, requestContext,geneQueryResponse);
    }
}
