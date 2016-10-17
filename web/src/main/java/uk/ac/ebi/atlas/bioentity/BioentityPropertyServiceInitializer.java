package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.utils.ReactomeClient;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.SortedSet;

@Named
@Scope("singleton")
public class BioentityPropertyServiceInitializer {

    private final BioEntityPropertyDao bioentityPropertyDao;

    private final SpeciesLookupService speciesLookupService;
    private final GoPoTermTrader goPoTermTrader;
    private final InterProTrader interProTermTrader;
    private final ReactomeClient reactomeClient;

    @Inject
    public BioentityPropertyServiceInitializer(BioEntityPropertyDao bioentityPropertyDao,
                                               SpeciesLookupService speciesLookupService,
                                               GoPoTermTrader goPoTermTrader,
                                               InterProTrader interProTermTrader,
                                               ReactomeClient reactomeClient) {
        this.bioentityPropertyDao = bioentityPropertyDao;
        this.speciesLookupService = speciesLookupService;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
        this.reactomeClient = reactomeClient;
    }

    public void initForGenePage(BioEntityPropertyService bioentityPropertyService, String identifier, String[] genePropertyNames) {
        Optional<String> species = speciesLookupService.fetchSpeciesForBioentityId(identifier);
        if(!species.isPresent()){
            throw new ResourceNotFoundException("Species can't be determined for gene:" + identifier);
        }

        SortedSetMultimap<String, String> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier, genePropertyNames);
        SortedSet<String> entityNames = propertyValuesByType.get("symbol");
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        bioentityPropertyService.init(propertyValuesByType);
    }

    public void initForGeneSetPage(BioEntityPropertyService bioentityPropertyService, String identifier) {

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

        bioentityPropertyService.init(propertyValuesByType);
    }
}
