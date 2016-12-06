package uk.ac.ebi.atlas.search.analyticsindex;

import uk.ac.ebi.atlas.search.SemanticQuery;
import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Named
public class AnalyticsSearchService {

    private final MiscellaneousAnalyticsSearchDao miscellaneousAnalyticsSearchDao;
    private final SpeciesPropertiesTrader speciesTrader;

    @Inject
    public AnalyticsSearchService(MiscellaneousAnalyticsSearchDao miscellaneousAnalyticsSearchDao,
                                  SpeciesPropertiesTrader speciesTrader) {
        this.miscellaneousAnalyticsSearchDao = miscellaneousAnalyticsSearchDao;
        this.speciesTrader = speciesTrader;
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

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, String species) {
        return fetchExperimentTypes(geneQuery, SemanticQuery.create(), speciesTrader.find(species).humanReadableName());

    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {

        String response = miscellaneousAnalyticsSearchDao.fetchExperimentTypes(geneQuery, conditionQuery, species.mappedName);

        return readBuckets(response);
    }

    public ImmutableSet<String> searchMoreThanOneBioentityIdentifier(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {

        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiers(geneQuery, conditionQuery, species.mappedName, 2);

        return readBuckets(response);
    }

    public ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {

        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiers(geneQuery, conditionQuery, species.mappedName, -1);
        return readBuckets(response);
    }

    public Collection<String> getBioentityIdentifiersForSpecies(Species species){

        String response = miscellaneousAnalyticsSearchDao.getBioentityIdentifiersForSpecies(species.mappedName);
        return readBuckets(response);
    }

    public boolean tissueExpressionAvailableFor(SemanticQuery geneQuery) {
        String response = miscellaneousAnalyticsSearchDao.searchBioentityIdentifiersForTissuesInBaselineExperiments(geneQuery);

        return ! readBuckets(response).isEmpty();
    }

}
