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
        return analyticsIndexSearchDAO.fetchExperimentTypes(bioentityIdentifier);
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, String species) {
        return analyticsIndexSearchDAO.fetchExperimentTypes(geneQuery, species);
    }

    public ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, String species) {
        return analyticsIndexSearchDAO.searchBioentityIdentifiers(geneQuery, species);
    }

    public Collection<String> getBioentityIdentifiersForSpecies(String species){
        return analyticsIndexSearchDAO.getBioentityIdentifiersForSpecies(species);
    }

}
