package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
public class BioEntityPropertyService {

    private SpeciesInferrer speciesInferrer;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private ReactomeClient reactomeClient;
    private GoPoTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyService(SpeciesInferrer speciesInferrer,
                                    BioEntityPropertyDao bioEntityPropertyDao,
                                    ReactomeClient reactomeClient,
                                    GoPoTrader goPoTermTrader,
                                    InterProTrader interProTermTrader) {

        this.speciesInferrer = speciesInferrer;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.reactomeClient = reactomeClient;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;

    }

    Map<String, String> mapToLinkText(BioentityPropertyName propertyName, Collection<String> propertyValues) {
        switch (propertyName) {
            case ORTHOLOG:
                return propertyValues.stream()
                        .collect(Collectors.toMap(Function.identity(), this::fetchSymbolAndSpeciesForOrtholog));
            case PATHWAYID:
                return reactomeClient.getPathwayNames(propertyValues);
            case GO:
            case PO:
                return propertyValues.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        p -> goPoTermTrader.get(p).map(OntologyTerm::name).orElse(p)));
            case INTERPRO:
                return propertyValues.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        p -> interProTermTrader.get(p).map(OntologyTerm::name).orElse(p)));
            default:
                return propertyValues.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
        }
    }

    int assessRelevance(BioentityPropertyName bioentityPropertyName, String propertyValue) {
        if (ImmutableList.of(BioentityPropertyName.GO, BioentityPropertyName.PO).contains(bioentityPropertyName)) {
            return goPoTermTrader.get(propertyValue).map(OntologyTerm::depth).orElse(0);
        } else {
            return 0;
        }
    }

    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {

        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(identifier));

        if (species.isUnknown()) {
            return identifier;
        }

        String speciesName = species.getName();
        String speciesToken = " (" + Character.toUpperCase(speciesName.charAt(0)) + speciesName.substring(1) + ")";

        Set<String> identifierSymbols =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);

        if (!identifierSymbols.isEmpty()) {
            return identifierSymbols.iterator().next() + speciesToken;
        }

        return identifier + speciesToken;

    }

}
