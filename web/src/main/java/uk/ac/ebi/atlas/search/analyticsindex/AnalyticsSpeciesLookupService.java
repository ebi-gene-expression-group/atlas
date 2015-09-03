package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Optional;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named
@Scope("singleton")
public class AnalyticsSpeciesLookupService  {
    public static final String SPECIES_FIELD = "species";
    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";

    private AnalyticsSearchDao analyticsSearchDao;

    @Inject
    public AnalyticsSpeciesLookupService(AnalyticsSearchDao analyticsSearchDao) {
        this.analyticsSearchDao = analyticsSearchDao;
    }

    // used for looking up species for a gene/protein/transcript/mirna/etc. id
    // they will only have a single species
    public String fetchSpeciesForBioentityId(String identifier) throws IOException, SolrServerException {
        // eg: bioentity_identifier:ENSMUSG00000021789
        return fetchFirstSpeciesByField(BIOENTITY_IDENTIFIER_FIELD, identifier);
    }

    public String fetchFirstSpeciesByField(String fieldName, String multiTermQuery) throws IOException, SolrServerException {
        List<String> queryTokens = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(multiTermQuery);
        for (String queryToken : queryTokens) {
            Optional<String> species = fetchFirstSpecies(fieldName, encloseInQuotes(queryToken));
            if (species.isPresent()) {
                return species.get();
            }
        }
        throw new ResourceNotFoundException("Species can't be determined for " + fieldName + ":" + multiTermQuery);
    }

    // surround in quotes, so queries with special chars work, eg: "GO:0003674"
    private String encloseInQuotes(String queryToken) {
        return "\"" + queryToken + "\"";
    }

    // this is faster than fetchSpeciesForGeneSet because it doesn't facet
    Optional<String> fetchFirstSpecies(String fieldName, String queryToken) throws IOException, SolrServerException {

        SolrQuery query = new SolrQuery(fieldName + ":" + queryToken);

        //fields to be returned, ie: fl=species
        query.setFields(SPECIES_FIELD);
        query.setRows(1);

//        Collection<String> species = solrServer.query(query, SPECIES_FIELD, false);
        QueryResponse species = analyticsSearchDao.query(query);

        return species.getResults().isEmpty() ? Optional.<String>absent() : Optional.of(species.getResults().iterator().next().getFieldValue("species").toString());
    }


}
