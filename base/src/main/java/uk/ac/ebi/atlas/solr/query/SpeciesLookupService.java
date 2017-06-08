package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Deprecated // See SpeciesInferrer for a better (?) option
@Named
public class SpeciesLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesLookupService.class);

    private static final String SPECIES_FIELD = "species";
    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";

    private GxaSolrClient solrServer;

    @Inject
    public SpeciesLookupService(GxaSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    @Deprecated
    public Optional<String> fetchFirstSpeciesForBioentityIdentifiers(Collection<String> identifiers) {
        return fetchFirstSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, identifiers);
    }

    private Optional<String> fetchFirstSpeciesByField(String fieldName, Collection<String> tokens) {
        for (String token : tokens) {
            Optional<String> species = fetchFirstSpecies(fieldName, token);
            if (species.isPresent()) {
                return species;
            }
        }
        return Optional.absent();
    }

    // this is faster than fetchSpeciesForGeneSet because it doesn't facet
    private Optional<String> fetchFirstSpecies(String fieldName, String queryToken) {
        LOGGER.debug("fetch first species for {}:{}", fieldName, queryToken);

        SolrQuery query = new SolrQuery(fieldName + ":" + StringUtils.wrap(queryToken, "\""));

        //fields to be returned, ie: fl=species
        query.setFields(SPECIES_FIELD);
        query.setRows(1);

        Collection<String> species = solrServer.query(query, false, SPECIES_FIELD);

        return species.isEmpty() ? Optional.<String>absent() : Optional.of(species.iterator().next());
    }
}