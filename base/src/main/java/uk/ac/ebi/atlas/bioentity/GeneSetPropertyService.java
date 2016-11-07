package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class GeneSetPropertyService {

    private final GoPoTermTrader goPoTermTrader;
    private final InterProTrader interProTermTrader;
    private final ReactomeClient reactomeClient;

    @Inject
    public GeneSetPropertyService(GoPoTermTrader goPoTermTrader,
                                  InterProTrader interProTermTrader,
                                  ReactomeClient reactomeClient) {
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
        this.reactomeClient = reactomeClient;
    }

    public SortedSetMultimap<String, String> propertyValuesByType(String identifier) {

        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();

        identifier = identifier.toUpperCase();

        if (GeneSetUtil.matchesReactomeID(identifier)) {
            propertyValuesByType.put("reactome", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeClient.fetchPathwayNameFailSafe(identifier));
        } else if (GeneSetUtil.matchesGeneOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("go", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
        } else if (GeneSetUtil.matchesPlantOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("po", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
        } else if (GeneSetUtil.matchesInterProAccession(identifier)) {
            String term = interProTermTrader.getTermName(identifier);
            propertyValuesByType.put("interpro", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, term);
        } else if (GeneSetUtil.matchesPlantReactomeID(identifier)) {
            propertyValuesByType.put("plant_reactome", identifier);
        }

        return propertyValuesByType;

    }

}
