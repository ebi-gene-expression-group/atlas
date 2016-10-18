package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

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
        return fetchExperimentTypes(SemanticQuery.create(bioentityIdentifier), SemanticQuery.create(), SpeciesFactory.NULL);
    }

    public ImmutableSet<String> fetchExperimentTypesInAnyField(SemanticQuery query) {
        return analyticsIndexSearchDAO.fetchExperimentTypesInAnyField(query);
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, Species species) {
        return fetchExperimentTypes(geneQuery, SemanticQuery.create(), species);
    }

    public ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        return analyticsIndexSearchDAO.fetchExperimentTypes(geneQuery, conditionQuery, species.mappedName);
    }

    public ImmutableSet<String> searchMoreThanOneBioentityIdentifier(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        return analyticsIndexSearchDAO.searchBioentityIdentifiers(geneQuery, conditionQuery, species.mappedName, 2);
    }

    public ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species) {
        return analyticsIndexSearchDAO.searchBioentityIdentifiers(geneQuery, conditionQuery, species.mappedName, -1);
    }

    public Collection<String> getBioentityIdentifiersForSpecies(Species species){
        return analyticsIndexSearchDAO.getBioentityIdentifiersForSpecies(species.mappedName);
    }

    public boolean tissueExpressionAvailableFor(SemanticQuery geneQuery) {
        return !analyticsIndexSearchDAO.searchBioentityIdentifiersForTissuesInBaselineExperiments(geneQuery).isEmpty();
    }

}
