package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
public class AnalyticsSearchService {

    private AnalyticsIndexSearchDAO analyticsIndexSearchDAO;

    @Inject
    public AnalyticsSearchService(AnalyticsIndexSearchDAO analyticsIndexSearchDAO) {
        this.analyticsIndexSearchDAO= analyticsIndexSearchDAO;
    }

    public ImmutableSet<String> fetchExperimentTypes(String bioentityIdentifier) {
        return fetchExperimentTypes(SemanticQuery.create(bioentityIdentifier), SemanticQuery.create(), "");
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, String species) {
        return fetchExperimentTypes(geneQuery, SemanticQuery.create(), species);
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsIndexSearchDAO.fetchExperimentTypes(geneQuery, conditionQuery, species);
    }

    public ImmutableSet<String> searchMoreThanOneBioentityIdentifier(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsIndexSearchDAO.searchBioentityIdentifiers(geneQuery, conditionQuery, species, 2);
    }

    public ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsIndexSearchDAO.searchBioentityIdentifiers(geneQuery, conditionQuery, species, -1);
    }


    public Collection<String> getBioentityIdentifiersForSpecies(String species){
        return analyticsIndexSearchDAO.getBioentityIdentifiersForSpecies(species);
    }

}
