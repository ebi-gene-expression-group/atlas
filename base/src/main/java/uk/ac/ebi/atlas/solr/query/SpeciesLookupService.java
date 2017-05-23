package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.solr.SolrUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;

@Deprecated // See SpeciesInferrer for a better (?) option
@Named
public class SpeciesLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesLookupService.class);

    private static final String SPECIES_FIELD = "species";
    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    private static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    private GxaSolrClient solrServer;

    @Inject
    public SpeciesLookupService(GxaSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    // Used for looking up species for a gene/protein/transcript/mirna/etc. ID; they will only have a single species
    @Deprecated
    public Optional<String> fetchSpeciesForBioentityId(String identifier) {
        return fetchFirstSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, Collections.singleton(identifier));
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

    // Used for looking up species for gene sets (GO, PO, InterPro, Reactome and Plant Reactome)
    // Reactome and Plant Reactome are always single species, but GO, PO and InterPro gene sets can be multi-species
    // If results are empty, then term does not exist in Solr
    @Deprecated
    public Optional<String> fetchSpeciesForGeneSet(String term) {
        // eg: property_value_lower:"IPR027417"
        String queryText = PROPERTY_LOWER_FIELD + ":" + StringUtils.wrap(term, "\"") +
                " AND property_name:(pathwayid OR go OR po OR interpro)";  // Needed to exclude Entrez numerical ids, identical to Plant Reactome ids (pathwayid)
        LOGGER.debug("fetch species for geneset {}", queryText);

        SolrQuery query = new SolrQuery(queryText);

        query.addFacetField(SPECIES_FIELD);
        query.setRows(0);
        query.setFacet(true);

        // just get first 2 species for performance reasons, we only need to know if multi-species exist, not what they are
        query.setFacetLimit(2);
        query.setFacetMinCount(1);

        QueryResponse solrResponse = solrServer.query(query);
        ImmutableSet<String> species = SolrUtil.extractFirstFacetValues(solrResponse);

        return species.size() == 1 ? Optional.of(species.iterator().next()) : Optional.<String>absent();
    }
}