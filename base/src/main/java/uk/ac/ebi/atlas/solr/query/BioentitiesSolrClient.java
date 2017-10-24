package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class BioentitiesSolrClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BioentitiesSolrClient.class);

    private static final int ROWS = 1000;
    private SolrClient solrClient;

    @Inject
    public BioentitiesSolrClient(@Qualifier("solrClientBioentities") SolrClient solrClient){
        this.solrClient = solrClient;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);

            LOGGER.debug("<query> Solr query: {}", solrQuery.toString());
            LOGGER.debug(
                    "<query> Solr query time: {} ms, status code: {}",
                    queryResponse.getStatus(), queryResponse.getQTime());

            return queryResponse;
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    public Set<String> query(SolrQuery solrQuery, boolean returnUppercaseValues, String field) {
        solrQuery.setFields(field);
        QueryResponse queryResponse = query(solrQuery);

        Set<String> results = Sets.newHashSet();
        for (SolrDocument doc : queryResponse.getResults()) {
            results.add(returnUppercaseValues ? doc.get(field).toString().toUpperCase() : doc.get(field).toString());
        }
        return results;
    }

    public Set<String> getBioentityIdentifiers(BioentityPropertyName bioentityPropertyName,
                                               String bioentityPropertyValue) {
        SolrQuery query = new SolrQuery();
        query.setRows(ROWS);
        query.setQuery(MessageFormat.format("property_name:\"{0}\" AND property_value:\"{1}\"",
                bioentityPropertyName.name, bioentityPropertyValue));
        query.setFields("bioentity_identifier");

        return query(query).getResults().stream()
                .map(d -> d.getFieldValue("bioentity_identifier").toString()).collect(Collectors.toSet());
    }

    public Map<BioentityPropertyName, Set<String>> getMap(String bioentityIdentifier,
                                                          Collection<BioentityPropertyName> bioentityPropertyNames) {
        SolrQuery query = new SolrQuery();

        query.setRows(ROWS);

        query.setFilterQueries("property_name:(\"" +
                bioentityPropertyNames.stream()
                        .map(bioentityPropertyName -> bioentityPropertyName.name().toLowerCase())
                        .collect(Collectors.joining("\" OR \""))
                + "\")"
        );
        query.setFields("property_name", "property_value");
        query.setQuery(MessageFormat.format("bioentity_identifier:\"{0}\"", bioentityIdentifier));

        Map<BioentityPropertyName, Set<String>> result = new HashMap<>();
        QueryResponse queryResponse = query(query);

        for (SolrDocument document : queryResponse.getResults()) {
            BioentityPropertyName key =
                    BioentityPropertyName.getByName(document.getFieldValue("property_name").toString());
            String value = document.getFieldValue("property_value").toString();

            if(!result.containsKey(key)){
                result.put(key, new HashSet<>());
            }
            result.get(key).add(value);
        }

        return result;
    }
}
