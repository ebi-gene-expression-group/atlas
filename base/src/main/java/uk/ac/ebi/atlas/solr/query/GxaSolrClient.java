package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Named
public class GxaSolrClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GxaSolrClient.class);

    private static final String PROPERTY_NAME_FIELD = "property_name";
    private static final String PROPERTY_VALUE_FIELD = "property_value";

    private SolrClient solrClient;

    @Inject
    public GxaSolrClient(@Qualifier("solrClientGxa") SolrClient solrClient){
        this.solrClient = solrClient;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            LOGGER.debug("<query> Solr query time: {} ms, status code: {}", queryResponse.getStatus(), queryResponse.getQTime() );
            return queryResponse;
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }

    public Set<String> query(SolrQuery solrQuery, boolean returnUppercaseValues, String field){
        QueryResponse queryResponse = query(solrQuery);

        Set<String> results = Sets.newHashSet();

        for (SolrDocument doc : queryResponse.getResults()) {
            String fieldValue = returnUppercaseValues ? StringUtils.upperCase(doc.getFieldValue(field).toString()) : doc.getFieldValue(field).toString();
            results.add(fieldValue);
        }
        return results;
    }

    public Map<BioentityPropertyName, Set<String>> getMap(String bioentityIdentifier, Collection<BioentityPropertyName> bioentityPropertyNames) {
        SolrQuery query = new SolrQuery();

        query.setRows(1000);
        query.setFilterQueries("property_name:(\"" +
                Joiner.on("\" OR \"").join(FluentIterable.from(bioentityPropertyNames).transform(
                        new Function<BioentityPropertyName, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable BioentityPropertyName bioentityPropertyName) {
                                return bioentityPropertyName.name().toLowerCase();
                            }
                        })) + "\")");
        query.setFields("property_name", "property_value");
        query.setQuery(MessageFormat.format("bioentity_identifier:\"{0}\"", bioentityIdentifier));

        Map<BioentityPropertyName, Set<String>> result = new HashMap<>();
        QueryResponse queryResponse = query(query);

        for (SolrDocument document : queryResponse.getResults()) {
            BioentityPropertyName key = BioentityPropertyName.getByName(document.getFieldValue(PROPERTY_NAME_FIELD).toString());
            String value = document.getFieldValue(PROPERTY_VALUE_FIELD).toString();

            if(!result.containsKey(key)){
                result.put(key, new HashSet<String>());
            }
            result.get(key).add(value);
        }

        return result;
    }

}
