package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class BaselineProfilesHeatmapsWrangler<Unit extends ExpressionUnit.Absolute> {

    private GeneQueryResponse geneQueryResponseForProfiles;

    private BaselineProfilesList jsonProfiles;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;
    private final SolrQueryService solrQueryService;
    private final BaselineExperiment experiment;
    private final BaselineRequestContext<Unit> requestContext;
    private final CoexpressedGenesService coexpressedGenesService;

    private final Function<BaselineProfile, URI> linkToGenes = new Function<BaselineProfile, URI>() {
        @Nullable
        @Override
        public URI apply(@Nullable BaselineProfile baselineProfile) {
            try {
                return new URI("genes/"+baselineProfile.getId());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private final Function<BaselineProfile, Unit> provideUnits = new Function<BaselineProfile, Unit>() {
        @Override
        public Unit apply(BaselineProfile baselineProfile) {
            return requestContext.getExpressionUnit();
        }
    };

    public BaselineProfilesHeatmapsWrangler(
            BaselineProfilesHeatMap baselineProfilesHeatMap,
            SolrQueryService solrQueryService,
            CoexpressedGenesService coexpressedGenesService,
            BaselineRequestPreferences<Unit> preferences,
            BaselineExperiment experiment) {

        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
        this.experiment = experiment;
        requestContext = new BaselineRequestContext<>(preferences, experiment);

    }

    private GeneQueryResponse getGeneQueryResponseForProfiles()  {
        if (geneQueryResponseForProfiles == null) {
            geneQueryResponseForProfiles =
                    solrQueryService.fetchResponse(requestContext.getGeneQuery(), requestContext.getSpecies());
        }
        return geneQueryResponseForProfiles;
    }

    private void fetchProfilesIfMissing()   {
        if (jsonProfiles == null) {
            jsonProfiles =
                    baselineProfilesHeatMap.fetch(experiment, requestContext, getGeneQueryResponseForProfiles());
        }
    }

    public JsonObject getJsonProfiles()  {
        fetchProfilesIfMissing();
        return new ExternallyViewableProfilesList<>(jsonProfiles,linkToGenes,
                requestContext.getDataColumnsToReturn(), provideUnits).asJson();
    }

    public JsonArray getJsonCoexpressions()   {
        fetchProfilesIfMissing();
        JsonArray result = new JsonArray();

        if (jsonProfiles.size() == 1) {
            BaselineProfile baselineProfile = jsonProfiles.get(0);

            Optional<Pair<GeneQueryResponse, List<String>>> coexpressedStuff =
                    coexpressedGenesService.tryGetRelatedCoexpressions(
                            experiment, getGeneQueryResponseForProfiles(),
                            ImmutableMap.of(baselineProfile.getId().toUpperCase(), 49));

            if (coexpressedStuff.isPresent()) {
                JsonObject o = new JsonObject();
                o.addProperty("geneName", baselineProfile.getName());
                o.addProperty("geneId", baselineProfile.getId());

                o.add("jsonProfiles",
                        new ExternallyViewableProfilesList<>(
                                baselineProfilesHeatMap.fetchInPrescribedOrder(
                                        coexpressedStuff.get().getRight(), experiment, requestContext,
                                        coexpressedStuff.get().getLeft()),
                                linkToGenes,
                                requestContext.getDataColumnsToReturn(), provideUnits)
                                .asJson());

                result.add(o);
            }
        }

        return result;
    }

}
