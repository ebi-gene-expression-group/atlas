package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
//can be singleton because HttpSolrClient is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SpeciesLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesLookupService.class);

    public static final String SPECIES_FIELD = "species";
    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    private GxaSolrClient solrServer;

    @Inject
    public SpeciesLookupService(GxaSolrClient solrServer) {
        this.solrServer = solrServer;
    }

    // used for looking up species for a gene/protein/transcript/mirna/etc. id
    // they will only have a single species
    public String fetchSpeciesForBioentityId(String identifier) {
        // eg: bioentity_identifier:ENSMUSG00000021789
        return fetchFirstSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, identifier);
    }

    public String fetchFirstSpeciesByField(String fieldName, String multiTermQuery) {
        if (StringUtils.isBlank(fieldName)) {
            fieldName = PROPERTY_LOWER_FIELD;
        }

        List<String> queryTokens = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(multiTermQuery);
        for (String queryToken : queryTokens) {
            Optional<String> species = fetchFirstSpecies(fieldName, encloseInQuotes(queryToken));
            if (species.isPresent()) {
                return species.get();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for " + fieldName + ":" + multiTermQuery);
    }

    public String fetchFirstSpeciesByField(String fieldName, SemanticQuery geneQuery) {
        if (StringUtils.isBlank(fieldName)) {
            fieldName = PROPERTY_LOWER_FIELD;
        }

        for (SemanticQueryTerm queryTerm : geneQuery) {
            Optional<String> species = fetchFirstSpecies(fieldName, encloseInQuotes(queryTerm.value()));
            if (species.isPresent()) {
                return species.get();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for " + fieldName + ":" + geneQuery.toJson());
    }

    // surround in quotes, so queries with special chars work, eg: "GO:0003674"
    private String encloseInQuotes(String queryToken) {
        return "\"" + queryToken + "\"";
    }

    // this is faster than fetchSpeciesForGeneSet because it doesn't facet
    Optional<String> fetchFirstSpecies(String fieldName, String queryToken) {
        LOGGER.debug("fetch first species for {}:{}", fieldName, queryToken);

        SolrQuery query = new SolrQuery(fieldName + ":" + queryToken);

        //fields to be returned, ie: fl=species
        query.setFields(SPECIES_FIELD);
        query.setRows(1);

        Collection<String> species = solrServer.query(query, false, SPECIES_FIELD);

        return species.isEmpty() ? Optional.<String>absent() : Optional.of(species.iterator().next());
    }

    // Used for looking up species for gene sets (GO, PO, InterPro, Reactome and Plant Reactome)
    // Reactome and Plant Reactome are always single species, but GO, PO and InterPro gene sets can be multi-species
    // If results are empty, then term does not exist in Solr
    public Result fetchSpeciesForGeneSet(String term) {
        // eg: property_value_lower:"IPR027417"
        String queryText = PROPERTY_LOWER_FIELD + ":" + encloseInQuotes(term) +
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

        return new Result(species);
    }

    public static class Result {
        public final ImmutableSet<String> species;

        public Result(ImmutableSet<String> species) {
            this.species = species;
        }

        public boolean isMultiSpecies() {
            return species.size() > 1;
        }

        public boolean isSingleSpecies() {
            return species.size() == 1;
        }

        public String firstSpecies() {
            return species.iterator().next();
        }

        public boolean isEmpty() {
            return species.isEmpty();
        }
    }

}