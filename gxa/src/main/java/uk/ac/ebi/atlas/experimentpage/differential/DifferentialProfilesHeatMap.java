package uk.ac.ebi.atlas.experimentpage.differential;

import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMapSource;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

public class DifferentialProfilesHeatMap<E extends DifferentialExperiment, P extends DifferentialProfile<?>, R extends
        DifferentialRequestContext<E>> {

    private SolrQueryService solrQueryService;
    private final boolean queryBySpecies;

    private final RankProfilesFactory<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions>
            rankProfilesFactory;

    private ProfilesHeatMapSource<E, P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions, Contrast>
            profilesHeatmapSource;

    public DifferentialProfilesHeatMap(RankProfilesFactory<P, DifferentialProfilesList<P>, DifferentialProfileStreamOptions>
                                             rankProfilesFactory,
                                       ProfileStreamFactory inputStreamFactory,
                                     SolrQueryService solrQueryService,boolean queryBySpecies) {
        this.rankProfilesFactory = rankProfilesFactory;
        this.profilesHeatmapSource = new ProfilesHeatMapSource<>(inputStreamFactory, new DifferentialProfileStreamFilters<P>());
        this.solrQueryService = solrQueryService;
        this.queryBySpecies = queryBySpecies;
    }

    public DifferentialProfilesList<P> fetch(R requestContext)  {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (requestContext.getGeneQuery(), queryBySpecies ? requestContext.getFilteredBySpecies(): "");
        return profilesHeatmapSource.fetch(requestContext.getExperiment(),requestContext,
                rankProfilesFactory.create(requestContext), geneQueryResponse, false);
    }
}
