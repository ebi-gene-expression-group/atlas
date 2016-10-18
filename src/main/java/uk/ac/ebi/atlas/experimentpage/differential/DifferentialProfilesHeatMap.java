package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMapSource;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;

public class DifferentialProfilesHeatMap<P extends DifferentialProfile<?>, R extends
        DifferentialRequestContext<?>> {

    SolrQueryService solrQueryService;
    private final boolean queryBySpecies;

    private final RankProfilesFactory<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions>
            rankProfilesFactory;

    private ProfilesHeatMapSource<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions, Contrast>
            profilesHeatmapSource;

    @Inject
    public DifferentialProfilesHeatMap(RankProfilesFactory<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions>
                                             rankProfilesFactory,
                                       ProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService,boolean queryBySpecies) {
        this.rankProfilesFactory = rankProfilesFactory;
        this.profilesHeatmapSource = new ProfilesHeatMapSource<>(inputStreamFactory, new
                DifferentialProfileStreamFilters<P>());
        this.solrQueryService = solrQueryService;
        this.queryBySpecies = queryBySpecies;
    }

    public DifferentialProfilesList<P> fetch(R requestContext) throws GenesNotFoundException {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponseBasedOnRequestContext
                (requestContext, queryBySpecies ? requestContext.getFilteredBySpecies(): "");
        return profilesHeatmapSource.fetch(requestContext,rankProfilesFactory.create(requestContext),
                geneQueryResponse, false);
    }
}
