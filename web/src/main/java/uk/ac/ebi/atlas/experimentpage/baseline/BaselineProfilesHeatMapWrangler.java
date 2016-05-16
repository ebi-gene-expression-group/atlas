package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.HashMap;
import java.util.Map;

public class BaselineProfilesHeatMapWrangler {

    private GeneQueryResponse geneQueryResponseForProfiles;

    private BaselineProfilesList jsonProfiles;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final SolrQueryService solrQueryService;

    private final BaselineExperiment experiment;

    private final boolean IGNORE_SPECIES = false;

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
        requestContext = BaselineRequestContext.createFor(experiment,preferences);
    }

    private String getSpecies(){
        return IGNORE_SPECIES ? "" : requestContext.getFilteredBySpecies();
    }

    public GeneQueryResponse getGeneQueryResponseForProfiles() throws GenesNotFoundException {
        if(geneQueryResponseForProfiles==null){
            geneQueryResponseForProfiles =
                    solrQueryService.fetchResponseBasedOnRequestContext(requestContext,
                            getSpecies());
        }
        return geneQueryResponseForProfiles;
    }

    private void fetchProfilesIfMissing() throws GenesNotFoundException{
        if (jsonProfiles == null) {
            jsonProfiles = baselineProfilesHeatMap.fetch( requestContext, getGeneQueryResponseForProfiles(), false);
        }
    }
    public JsonObject getJsonProfiles() throws GenesNotFoundException {
        fetchProfilesIfMissing();
        return baselineProfilesViewModelBuilder.build(jsonProfiles, requestContext.getOrderedFilterFactors());
    }

    public Optional<JsonObject> getJsonProfilesAsGeneSets() throws GenesNotFoundException {
        GeneQueryResponse r = getGeneQueryResponseForProfiles();
        return r.containsGeneSets()
                ? Optional.of(baselineProfilesViewModelBuilder.build(baselineProfilesHeatMap.fetch(requestContext,r, true),
                requestContext.getOrderedFilterFactors()))
                : Optional.<JsonObject>absent();
    }

    public Map<String, JsonObject> getJsonCoexpressions() throws GenesNotFoundException {
        fetchProfilesIfMissing();
        Map<String, JsonObject> result = new HashMap<>();
        if(jsonProfiles.size() == 1) {
            for(String geneName: jsonProfiles.extractGeneNames()){
                Optional<GeneQuery> query = coexpressedGenesService.tryGetRelatedCoexpressions(experiment, requestContext
                        .getGeneQuery(), ImmutableMap.of(geneName.toUpperCase(), 49));
                if(query.isPresent()) {
                    GeneQueryResponse response = solrQueryService.fetchResponseBasedOnRequestContext(query.get(),
                            requestContext.isExactMatch(), getSpecies());

                    result.put(geneName, baselineProfilesViewModelBuilder.build
                            (baselineProfilesHeatMap.fetchInPrescribedOrder(query.get().terms(), requestContext, response, false),
                                    requestContext
                                    .getOrderedFilterFactors()));
                }

            }
        }
        return result;

    }
}
