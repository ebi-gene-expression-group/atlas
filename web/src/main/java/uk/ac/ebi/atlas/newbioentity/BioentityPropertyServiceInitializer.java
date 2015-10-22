package uk.ac.ebi.atlas.newbioentity;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SortedSetMultimap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 20/10/15.
 */
@Named
@Scope("singleton")
public class BioentityPropertyServiceInitializer {

    private final BioEntityPropertyDao bioentityPropertyDao;

    private final SpeciesLookupService speciesLookupService;
    private final GoTermTrader goTermTrader;
    private final PoTermTrader poTermTrader;

    @Inject
    public BioentityPropertyServiceInitializer(BioEntityPropertyDao bioentityPropertyDao,
                                               SpeciesLookupService speciesLookupService,
                                               GoTermTrader goTermTrader,
                                               PoTermTrader poTermTrader) {
        this.bioentityPropertyDao = bioentityPropertyDao;
        this.speciesLookupService = speciesLookupService;
        this.goTermTrader = goTermTrader;
        this.poTermTrader = poTermTrader;
    }

    public void initForGenePage(BioEntityPropertyService bioentityPropertyService, String identifier, String[] genePropertyNames) {
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier, genePropertyNames);
        SortedSet<String> entityNames = propertyValuesByType.get("symbol");
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        ImmutableSetMultimap<Integer, GoPoTerm> goTerms = mapGoTermsByDepth(propertyValuesByType.get("go"));
        ImmutableSetMultimap<Integer, GoPoTerm> poTerms = mapPoTermsByDepth(propertyValuesByType.get("po"));

        bioentityPropertyService.init(species, propertyValuesByType, goTerms, poTerms, entityNames, identifier);
    }


    private ImmutableSetMultimap<Integer, GoPoTerm> mapGoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            try {
                builder.put(goTermTrader.getDepth(accession), goTermTrader.getTerm(accession));
            } catch (NullPointerException e) {
                // Ignore terms which aren’t found in goIDToTerm.tsv
            }
        }

        return builder.build();
    }

    private ImmutableSetMultimap<Integer, GoPoTerm> mapPoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            builder.put(poTermTrader.getDepth(accession), poTermTrader.getTerm(accession));
        }

        return builder.build();
    }

}
