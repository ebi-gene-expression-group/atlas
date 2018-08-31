package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfileComparator;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilter;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.solr.bioentities.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.concurrent.TimeUnit;

public class
DifferentialProfilesHeatMap<
        X extends DifferentialExpression,
        E extends DifferentialExperiment,
        P extends DifferentialProfile<X, P>,
        R extends DifferentialRequestContext<E, ? extends DifferentialRequestPreferences>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialProfilesHeatMap.class);
    private SolrQueryService solrQueryService;
    private final ProfileStreamFactory<Contrast, X, E, R, P> profileStreamFactory;

    public DifferentialProfilesHeatMap(ProfileStreamFactory<Contrast, X, E, R, P> profileStreamFactory,
                                       SolrQueryService solrQueryService) {
        this.profileStreamFactory = profileStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public DifferentialProfilesList<P> fetch(R requestContext) {
        GeneQueryResponse geneQueryResponse =
                solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getSpecies());

        Stopwatch stopwatch = Stopwatch.createStarted();

        DifferentialProfilesList<P> profiles =
                profileStreamFactory.select(
                        requestContext.getExperiment(),
                        requestContext,
                        geneQueryResponse.getAllGeneIds(),
                        ProfileStreamFilter.create(requestContext),
                        new MinMaxProfileRanking<>(
                                DifferentialProfileComparator.create(requestContext),
                                new DifferentialProfilesListBuilder<>()));

        stopwatch.stop();

        LOGGER.debug("<fetch> for [{}]  took {} secs",
                geneQueryResponse.getAllGeneIds().size(),
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return profiles;
    }
}
