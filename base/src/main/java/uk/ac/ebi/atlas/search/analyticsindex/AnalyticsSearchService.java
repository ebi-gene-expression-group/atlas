package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Optional;
import net.minidev.json.JSONArray;
import uk.ac.ebi.atlas.search.SemanticQuery;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Named
public class AnalyticsSearchService {

    private final MiscellaneousAnalyticsSearchDao miscellaneousAnalyticsSearchDao;

    @Inject
    public AnalyticsSearchService(MiscellaneousAnalyticsSearchDao miscellaneousAnalyticsSearchDao) {
        this.miscellaneousAnalyticsSearchDao = miscellaneousAnalyticsSearchDao;
    }

    public ImmutableSet<String> fetchExperimentTypes(String bioentityIdentifier) {
        return fetchExperimentTypes(SemanticQuery.create(bioentityIdentifier), SemanticQuery.create(), "");
    }

    public static ImmutableSet<String> readBuckets(String response){
        List<Map<String,Object>> res = JsonPath.read(response, "$..buckets[*]");

        ImmutableSet.Builder<String> b = ImmutableSet.builder();
        for(Map<String,Object> m: res) {
            b.add(m.get("val").toString());
        }
        return b.build();
    }

    public ImmutableSet<String> fetchExperimentTypesInAnyField(SemanticQuery query) {
        String response = miscellaneousAnalyticsSearchDao.fetchExperimentTypesInAnyField(query);
        return readBuckets(response);
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, String speciesReferenceName) {

        return fetchExperimentTypes(geneQuery, SemanticQuery.create(), speciesReferenceName);

    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String speciesReferenceName) {

        String response = miscellaneousAnalyticsSearchDao.fetchExperimentTypes(geneQuery, conditionQuery, speciesReferenceName);

        return readBuckets(response);
    }

    public ImmutableSet<String> searchMoreThanOneBioentityIdentifier(SemanticQuery geneQuery, SemanticQuery conditionQuery, String speciesReferenceName) {

        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiers(geneQuery, conditionQuery, speciesReferenceName, 2);

        return readBuckets(response);
    }

    public ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, String speciesReferenceName) {

        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiers(geneQuery, conditionQuery, speciesReferenceName, -1);
        return readBuckets(response);
    }

    public Collection<String> getBioentityIdentifiersForSpecies(String speciesReferenceName){

        String response = miscellaneousAnalyticsSearchDao.getBioentityIdentifiersForSpecies(speciesReferenceName);
        return readBuckets(response);
    }

    public boolean tissueExpressionAvailableFor(SemanticQuery geneQuery) {
        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiersForTissuesInBaselineExperiments(geneQuery);

        return ! readBuckets(response).isEmpty();
    }

    public Optional<String> findSpeciesFor(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        if (geneQuery.isEmpty() && conditionQuery.isEmpty()) {
            return Optional.absent();
        }

        JSONArray jsonArray = JsonPath.read(
                miscellaneousAnalyticsSearchDao.getFirstSpeciesForQuery(geneQuery, conditionQuery), "$..species");

        return jsonArray.size() != 0 ? Optional.of(jsonArray.get(0).toString()) : Optional.<String>absent();
    }
}
