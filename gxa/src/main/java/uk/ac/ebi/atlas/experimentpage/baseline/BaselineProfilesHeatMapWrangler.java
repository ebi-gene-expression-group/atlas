package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
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

public class BaselineProfilesHeatmapWrangler {

    private GeneQueryResponse geneQueryResponseForProfiles;

    private BaselineProfilesList jsonProfiles;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final SolrQueryService solrQueryService;

    private final BaselineExperiment experiment;

    private final BaselineRequestContext requestContext;

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

    private final Function<BaselineProfile, URI> linkToGenesets = new Function<BaselineProfile, URI>() {
        @Nullable
        @Override
        public URI apply(@Nullable BaselineProfile baselineProfile) {
            try {
                return new URI("genesets/"+baselineProfile.getId());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public BaselineProfilesHeatmapWrangler(
            BaselineProfilesHeatMap baselineProfilesHeatMap,
            SolrQueryService solrQueryService,
            CoexpressedGenesService coexpressedGenesService,
            BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
        this.experiment = experiment;
        requestContext = new BaselineRequestContext(preferences, experiment);
    }

    private String getSpecies() {
        return requestContext.getSpecies().getReferenceName();
    }

    private GeneQueryResponse getGeneQueryResponseForProfiles()  {
        if (geneQueryResponseForProfiles == null) {
            geneQueryResponseForProfiles =
                    solrQueryService.fetchResponse(requestContext.getGeneQuery(), getSpecies());
        }
        return geneQueryResponseForProfiles;
    }

    private void fetchProfilesIfMissing()   {
        if (jsonProfiles == null) {
            jsonProfiles =
                    baselineProfilesHeatMap.fetch(experiment,requestContext, getGeneQueryResponseForProfiles(), false);
        }
    }



    public JsonObject getJsonProfiles()  {
        fetchProfilesIfMissing();
        return new ExternallyViewableProfilesList<>(jsonProfiles,linkToGenes,
                requestContext
                .getDataColumnsToReturn() ).asJson();
    }

    public Optional<JsonObject> getJsonProfilesAsGeneSets()  {
        GeneQueryResponse r = getGeneQueryResponseForProfiles();
        return r.containsGeneSets()
                ? Optional.of(
                        new ExternallyViewableProfilesList<>(
                                baselineProfilesHeatMap.fetch(experiment,requestContext, r, true),
                                linkToGenesets, requestContext.getDataColumnsToReturn()).asJson())

                : Optional.<JsonObject>absent();
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
                                        coexpressedStuff.get().getLeft(), false),
                                linkToGenes,
                                requestContext.getDataColumnsToReturn())
                                .asJson());

                result.add(o);
            }
        }

        return result;

    }
}
