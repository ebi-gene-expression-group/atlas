package uk.ac.ebi.atlas.search.geneids;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.util.Optional;
import java.util.function.Function;

import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENSGENE;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENTREZGENE;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.FLYBASE_GENE_ID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.HGNC_SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.MGI_ID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.MGI_SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.WBPSGENE;

// Takes a GeneQuery object and returns a set of matching gene IDs, if a category is missing it tries several ID fields
@Component
public class GeneIdSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneIdSearchService.class);

    // Where, and in what order, should we search in case of a free text query (without category)
    static final ImmutableList<BioentityPropertyName> ID_PROPERTY_NAMES =
            ImmutableList.of(ENSGENE, SYMBOL, ENTREZGENE, HGNC_SYMBOL, MGI_ID, MGI_SYMBOL, FLYBASE_GENE_ID, WBPSGENE);

    // These are species-specific property names that will ignore a species argument when searching. We assume that if
    // the user chooses something like ENSG000001234 from the drop-down and Mus musculus in the species select, it is
    // because she didn’t notice and her intent is clear (or even that she chose a species but when typing and saw the
    // suggestions she changed her mind). After all, she’s the geneticist/biologist/bioinformatician!
    static final ImmutableList<BioentityPropertyName> SPECIES_OVERRIDE_PROPERTY_NAMES =
            ImmutableList.of(ENSGENE, ENTREZGENE, HGNC_SYMBOL, MGI_ID, MGI_SYMBOL, FLYBASE_GENE_ID, WBPSGENE);

    private final GeneIdSearchDao geneIdSearchDao;

    public GeneIdSearchService(GeneIdSearchDao geneIdSearchDao) {
        this.geneIdSearchDao = geneIdSearchDao;
    }

    public Optional<ImmutableSet<String>> search(GeneQuery geneQuery) {
        if (geneQuery.category().isPresent()) {
            if (geneQuery.species().isPresent() &&
                !SPECIES_OVERRIDE_PROPERTY_NAMES.contains(geneQuery.category().get())) {
                LOGGER.debug(
                        "Searching {}/{} in species {}",
                        geneQuery.queryTerm(),
                        geneQuery.category().get(),
                        geneQuery.species().get().getEnsemblName());

                return geneIdSearchDao.searchGeneIds(
                        geneQuery.queryTerm(),
                        geneQuery.category().get().name,
                        geneQuery.species().get().getEnsemblName());
            }

            LOGGER.debug(
                    "Searching {}/{} ignoring species {}",
                    geneQuery.queryTerm(),
                    geneQuery.category().get(),
                    geneQuery.species().map(Species::getEnsemblName).orElse("(none provided)"));

            return geneIdSearchDao.searchGeneIds(geneQuery.queryTerm(), geneQuery.category().get().name);
        }

        LOGGER.debug(
                "Searching {} (free text without category) in species {}",
                geneQuery.queryTerm(),
                geneQuery.species().map(Species::getEnsemblName).orElse("(none provided)"));

        return geneQuery.species().isPresent() ?
                searchIds(propertyName ->
                        geneIdSearchDao.searchGeneIds(
                                geneQuery.queryTerm(),
                                propertyName,
                                geneQuery.species().get().getEnsemblName())) :
                searchIds(propertyName -> geneIdSearchDao.searchGeneIds(geneQuery.queryTerm(), propertyName));
    }

    private Optional<ImmutableSet<String>> searchIds(Function<String, Optional<ImmutableSet<String>>> idSearcher) {
        boolean queryMatchesKnownIds = false;

        for (BioentityPropertyName propertyName : ID_PROPERTY_NAMES) {
            Optional<ImmutableSet<String>> matchingGeneIds = idSearcher.apply(propertyName.name);

            if (matchingGeneIds.isPresent()) {
                if (!matchingGeneIds.get().isEmpty()) {
                    return matchingGeneIds;
                }

                queryMatchesKnownIds = true;
            }
        }

        return queryMatchesKnownIds ? Optional.of(ImmutableSet.of()) : Optional.empty();
    }
}
