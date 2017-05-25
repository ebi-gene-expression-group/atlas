package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.profiles.MinMaxProfileRanking;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesListBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.concurrent.TimeUnit;

public class DifferentialProfilesHeatMap<Expr extends DifferentialExpression,
        E extends DifferentialExperiment, Prof extends DifferentialProfile<Expr, Prof>, R extends
        DifferentialRequestContext<E, ? extends DifferentialRequestPreferences>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialProfilesHeatMap.class);
    private SolrQueryService solrQueryService;
    private final ProfileStreamFactory<Contrast, Expr, E, R, Prof> profileStreamFactory;

    public DifferentialProfilesHeatMap(ProfileStreamFactory<Contrast, Expr, E, R, Prof> profileStreamFactory, SolrQueryService solrQueryService) {
        this.profileStreamFactory = profileStreamFactory;
        this.solrQueryService = solrQueryService;
    }

    public DifferentialProfilesList<Prof> fetch(R requestContext) {
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (requestContext.getGeneQuery(), requestContext.getExperiment().getType().isMicroarray()? ""  :
                        requestContext.getSpecies().getReferenceName());

        Stopwatch stopwatch = Stopwatch.createStarted();

        DifferentialProfilesList<Prof> profiles = profileStreamFactory.select(requestContext.getExperiment(), requestContext,
                new DifferentialProfileStreamTransforms<Prof>(requestContext, geneQueryResponse),
                new MinMaxProfileRanking<>(
                        DifferentialProfileComparator.<Prof>create(requestContext),
                        new DifferentialProfilesListBuilder<Prof>()));

        stopwatch.stop();

        LOGGER.debug("<fetch> for [{}]  took {} secs",
                geneQueryResponse.getAllGeneIds().size(),
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        return profiles;
    }
}
