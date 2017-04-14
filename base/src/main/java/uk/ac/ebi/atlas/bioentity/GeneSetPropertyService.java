package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.bioentity.go.GoPoTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.*;

@Named
public class GeneSetPropertyService {

    private final GoPoTrader goPoTermTrader;
    private final InterProTrader interProTermTrader;
    private final ReactomeClient reactomeClient;

    @Inject
    public GeneSetPropertyService(GoPoTrader goPoTermTrader,
                                  InterProTrader interProTermTrader,
                                  ReactomeClient reactomeClient) {
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
        this.reactomeClient = reactomeClient;
    }

    public static final List<BioentityPropertyName> all =
            ImmutableList.of(REACTOME, GO, PO, INTERPRO);

    public Map<BioentityPropertyName, Set<String>> propertyValuesByType(String identifier) {
        identifier = identifier.toUpperCase();
        if (GeneSetUtil.matchesReactomeID(identifier)) {
            return propertyValuesByType(REACTOME, identifier, reactomeClient.fetchPathwayNameFailSafe(identifier));
        } else if (GeneSetUtil.matchesGeneOntologyAccession(identifier)) {
            return propertyValuesByType(GO, identifier, goPoTermTrader.getTermName(identifier));
        } else if (GeneSetUtil.matchesPlantOntologyAccession(identifier)) {
            return propertyValuesByType(PO, identifier, goPoTermTrader.getTermName(identifier));
        }else if (GeneSetUtil.matchesInterProAccession(identifier)) {
            return propertyValuesByType(INTERPRO, identifier, interProTermTrader.getTermName(identifier));
        } else {
            return ImmutableMap.of();
        }
    }

    private Map<BioentityPropertyName, Set<String>> propertyValuesByType(BioentityPropertyName which,
                                                                         String identifier, String value) {
        return ImmutableMap.of(which, (Set<String>)ImmutableSet.of(identifier), DESCRIPTION, ImmutableSet.of(value));
    }

}
