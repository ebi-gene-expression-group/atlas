package uk.ac.ebi.atlas.bioentity.properties;

import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.Set;

@Named
public class BioEntityPropertyLinkTextService {

    private SpeciesInferrer speciesInferrer;
    private BioEntityPropertyDao bioEntityPropertyDao;
    private ReactomeClient reactomeClient;
    private GoPoTrader goPoTermTrader;
    private InterProTrader interProTermTrader;

    @Inject
    public BioEntityPropertyLinkTextService(SpeciesInferrer speciesInferrer,
                                            BioEntityPropertyDao bioEntityPropertyDao, ReactomeClient reactomeClient,
                                            GoPoTrader goPoTermTrader, InterProTrader interProTermTrader) {

        this.speciesInferrer = speciesInferrer;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.reactomeClient = reactomeClient;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;

    }

    public Optional<String> getLinkText(BioentityPropertyName propertyName, String propertyValue) {

        switch (propertyName) {
            case ORTHOLOG:
                return Optional.of(fetchSymbolAndSpeciesForOrtholog(propertyValue));
            case PATHWAYID:
                return reactomeClient.fetchPathwayNameFailSafe(propertyValue);
            case GO:
                return goPoTermTrader.getTermName(propertyValue);
            case INTERPRO:
                return interProTermTrader.getTermName(propertyValue);
            case PO:
                return goPoTermTrader.getTermName(propertyValue);
            default:
                return Optional.of(propertyValue);
        }

    }


    private String fetchSymbolAndSpeciesForOrtholog(String identifier) {

        Species species = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(identifier));

        if (species.isUnknown()) {
            return identifier;
        }

        String speciesToken = " (" + species.getName() + ")";

        Set<String> propertyValuesForGeneId =
                bioEntityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);

        if (!propertyValuesForGeneId.isEmpty()) {
            String symbol = propertyValuesForGeneId.iterator().next();
            return symbol + speciesToken;
        }

        return identifier + speciesToken;

    }

}
