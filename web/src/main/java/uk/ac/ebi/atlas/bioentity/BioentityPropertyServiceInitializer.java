package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService.Result;
import uk.ac.ebi.atlas.utils.ReactomeClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
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
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier, genePropertyNames);
        SortedSet<String> entityNames = propertyValuesByType.get("symbol");
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        ImmutableSetMultimap<Integer, OntologyTerm> goTerms = mapGoPoTermsByDepth(propertyValuesByType.get("go"));
        ImmutableSetMultimap<Integer, OntologyTerm> poTerms = mapGoPoTermsByDepth(propertyValuesByType.get("po"));

        bioentityPropertyService.init(species, propertyValuesByType, goTerms, poTerms, entityNames, identifier);
    }

    public void initForGeneSetPage(BioEntityPropertyService bioentityPropertyService, String identifier) {
        Result speciesResult = speciesLookupService.fetchSpeciesForGeneSet(identifier);
        String species = speciesResult.isSingleSpecies() ? speciesResult.firstSpecies() : "";

        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();

        ImmutableSetMultimap.Builder<Integer, OntologyTerm> builder = new ImmutableSetMultimap.Builder<>();
        ImmutableSetMultimap<Integer, OntologyTerm> goTermsByDepth = builder.build();
        ImmutableSetMultimap<Integer, OntologyTerm> poTermsByDepth = builder.build();

        identifier = identifier.toUpperCase();

        if (GeneSetUtil.matchesReactomeID(identifier)) {
            propertyValuesByType.put("reactome", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeClient.fetchPathwayNameFailSafe(identifier));
        } else if (GeneSetUtil.matchesGeneOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("go", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            goTermsByDepth = mapGoPoTermsByDepth(propertyValuesByType.get("go"));
        } else if (GeneSetUtil.matchesPlantOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("po", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            poTermsByDepth = mapGoPoTermsByDepth(propertyValuesByType.get("po"));
        } else if (GeneSetUtil.matchesInterProAccession(identifier)) {
            String term = interProTermTrader.getTermName(identifier);
            propertyValuesByType.put("interpro", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, term);
        } else if (GeneSetUtil.matchesPlantReactomeID(identifier)) {
            propertyValuesByType.put("plant_reactome", identifier);
        }

        SortedSet<String> names = Sets.newTreeSet();
        names.add(identifier);

        bioentityPropertyService.init(species, propertyValuesByType, goTermsByDepth, poTermsByDepth, names, identifier);
    }


    private ImmutableSetMultimap<Integer, OntologyTerm> mapGoPoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, OntologyTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            try {
                builder.put(goPoTermTrader.getTerm(accession).depth(), goPoTermTrader.getTerm(accession));
            } catch (NullPointerException e) {
                // Ignore terms which aren’t found in goIDToTerm.tsv
            }
        }

        return builder.build();
    }

}
