package uk.ac.ebi.atlas.search.geneids;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.species.Species;

import java.util.Optional;
import java.util.function.Function;

// Takes a GeneQuery object and returns a set of matching gene IDs, if a category is missing it tries several ID fields
@Component
public class GeneIdSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneIdSearchService.class);

    private final GeneIdSearchDao geneIdSearchDao;

    public GeneIdSearchService(GeneIdSearchDao geneIdSearchDao) {
        this.geneIdSearchDao = geneIdSearchDao;
    }

    public Optional<ImmutableSet<String>> search(GeneQuery geneQuery) {
        if (geneQuery.category().isPresent()) {
            if (geneQuery.species().isPresent() &&
                !BioentitiesCollectionProxy.SPECIES_OVERRIDE_PROPERTY_NAMES.contains(geneQuery.category().get())) {
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

        for (BioentityPropertyName propertyName : BioentitiesCollectionProxy.ID_PROPERTY_NAMES) {
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
