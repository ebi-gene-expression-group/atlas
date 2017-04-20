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
        if (isBlank(speciesString)) {
            return inferSpecies(geneQuery, conditionQuery);
        }

        return speciesFactory.create(speciesString);
    }

    public Species inferSpeciesForGeneQuery(SemanticQuery geneQuery) {
        return inferSpecies(geneQuery, SemanticQuery.create());
    }

    public Species inferSpeciesForGeneQuery(SemanticQuery geneQuery, String speciesString) {
        return inferSpecies(geneQuery, SemanticQuery.create(), speciesString);
    }

    private Species inferSpecies(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        if (isEmpty(geneQuery) && isEmpty(conditionQuery)) {
            return speciesFactory.createUnknownSpecies();
        }

        ImmutableList<String> speciesCandidates = analyticsSearchService.findSpecies(geneQuery, conditionQuery);

        return speciesCandidates.isEmpty() ?
                speciesFactory.createUnknownSpecies() :
                speciesFactory.create(speciesCandidates.get(0));
    }

}
