package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.List;

public class BaselineProfilesHeatMapWrangler {

    private GeneQueryResponse geneQueryResponseForProfiles;

    private BaselineProfilesList jsonProfiles;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final SolrQueryService solrQueryService;

    private final BaselineExperiment experiment;

    private final BaselineRequestContext requestContext;

    private final CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesHeatMapWrangler(
            BaselineProfilesHeatMap baselineProfilesHeatMap,
            BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder, SolrQueryService solrQueryService,
            CoexpressedGenesService coexpressedGenesService,
            BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
        this.experiment = experiment;
        requestContext = BaselineRequestContext.createFor(experiment, preferences);
    }

    private String getSpecies() {
        return requestContext.getFilteredBySpecies();
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
            jsonProfiles = baselineProfilesHeatMap.fetch(experiment,requestContext, getGeneQueryResponseForProfiles(), false);
        }
    }

    public JsonObject getJsonProfiles()  {
        fetchProfilesIfMissing();
        return baselineProfilesViewModelBuilder.build(jsonProfiles, requestContext.getFilterFactorsInTheSameOrderAsTheExperimentHeader());
    }

    public Optional<JsonObject> getJsonProfilesAsGeneSets()  {
        GeneQueryResponse r = getGeneQueryResponseForProfiles();
        return r.containsGeneSets()
                ? Optional.of(baselineProfilesViewModelBuilder.build(baselineProfilesHeatMap.fetch(experiment,requestContext,
                r, true),
                requestContext.getFilterFactorsInTheSameOrderAsTheExperimentHeader()))
                : Optional.<JsonObject>absent();
    }

    public JsonArray getJsonCoexpressions()   {
        fetchProfilesIfMissing();
        JsonArray result = new JsonArray();

        if (jsonProfiles.size() == 1) {
            OldBaselineProfile baselineProfile = jsonProfiles.get(0);

            Optional<Pair<GeneQueryResponse, List<String>>> coexpressedStuff =
                    coexpressedGenesService.tryGetRelatedCoexpressions(experiment, getGeneQueryResponseForProfiles(), ImmutableMap.of(baselineProfile.getId().toUpperCase(), 49));
            JsonObject o = new JsonObject();
            o.addProperty("geneName", baselineProfile.getName());
            o.addProperty("geneId", baselineProfile.getId());
            if (coexpressedStuff.isPresent()) {
                o.add("jsonProfiles", baselineProfilesViewModelBuilder.build
                        (baselineProfilesHeatMap.fetchInPrescribedOrder(
                                coexpressedStuff.get()
                                .getRight(),
                                experiment,
                                requestContext,
                                coexpressedStuff.get().getLeft(), false),
                                requestContext
                                        .getFilterFactorsInTheSameOrderAsTheExperimentHeader()));
            }
            result.add(o);
        }

        return result;

    }
}
