package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;

import javax.inject.Inject;
import javax.inject.Named;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static uk.ac.ebi.atlas.search.SemanticQuery.isEmpty;

@Named
public class SpeciesInferrer {

    private AnalyticsSearchService analyticsSearchService;
    private SpeciesFactory speciesFactory;

    @Inject
    public SpeciesInferrer(AnalyticsSearchService analyticsSearchService, SpeciesFactory speciesFactory) {
        this.analyticsSearchService = analyticsSearchService;
        this.speciesFactory = speciesFactory;
    }

    public Species inferSpecies(SemanticQuery geneQuery, SemanticQuery conditionQuery, String speciesString) {
        if (isEmpty(geneQuery) && isEmpty(conditionQuery) && isBlank(speciesString)) {
            return speciesFactory.create("");
        }

        ImmutableList<String> speciesCandidates = analyticsSearchService.findSpecies(geneQuery, conditionQuery, speciesString);

        return speciesCandidates.isEmpty() ? speciesFactory.create("") : speciesFactory.create(speciesCandidates.get(0));
    }

    public Species inferSpeciesForGeneQuery(SemanticQuery geneQuery, String speciesString) {
        return inferSpecies(geneQuery, SemanticQuery.create(), speciesString);
    }

    public Species inferSpeciesForConditionQuery(SemanticQuery conditionQuery, String speciesString) {
        return inferSpecies(SemanticQuery.create(), conditionQuery, speciesString);
    }

}
