package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import uk.ac.ebi.atlas.solr.query.BioentityNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Named
public class AnalyticsBioentityPropertyDao {

    private AnalyticsSearchDao analyticsSearchDao;

    private StringBuilder queryStringBuilder = new StringBuilder();

    private static final String IDENTIFIER_SEARCH_FIELD = "identifierSearch";
    private static final int DEFAULT_LIMIT = 15;
    private static final int ROWS_LIMIT = 1000;


    @Inject
    public AnalyticsBioentityPropertyDao(AnalyticsSearchDao analyticsSearchDao) {
        this.analyticsSearchDao = analyticsSearchDao;
    }


    public SortedSetMultimap<String, String> fetchGenePageProperties(String identifier, String[] propertyNames) {
        SortedSetMultimap<String, String> propertiesByName = fetchProperties(identifier, propertyNames);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByName;
    }


    SortedSetMultimap<String, String> fetchProperties(String bioentityIdentifier, String[] propertyNames) {

        buildPropertyNames(propertyNames);

        SolrQuery query = buildBioentityQuery(bioentityIdentifier);

        query.setRows(ROWS_LIMIT);
        query.setFields("species");

        return queryForProperties(query);

    }

    public SolrQuery buildBioentityQuery(String bioentityId) {

        String solrQueryString = "bioentity_identifier:\"" + bioentityId + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(IDENTIFIER_SEARCH_FIELD);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(DEFAULT_LIMIT);
        solrQuery.setFacetMinCount(1);

        return solrQuery;
    }

    public void buildPropertyNames(String... propertyNames){
        checkArgument(org.apache.commons.lang3.ArrayUtils.isNotEmpty(propertyNames));

        Collection<String> propertyNameConditions = transformToConditions(IDENTIFIER_SEARCH_FIELD, Sets.newHashSet(propertyNames));

        queryStringBuilder.append(" AND (");
        Joiner.on(" OR ").appendTo(queryStringBuilder, propertyNameConditions).append(")");
    }

    private Collection<String> transformToConditions(final String fieldName, Set<String> values){
        return Collections2.transform(values, new Function<String, String>() {
            @Override
            public String apply(String bioEntityType) {
                return fieldName.concat(":\"").concat(bioEntityType).concat("\"");
            }
        });

    }

    public SortedSetMultimap<String, String> queryForProperties(SolrQuery solrQuery){

        QueryResponse queryResponse = analyticsSearchDao.query(solrQuery);

        SortedSetMultimap<String, String> results = TreeMultimap.create();
        for (SolrDocument document : queryResponse.getResults()) {
            String key = document.getFieldValue("identifierSearch").toString();
            String value = document.getFieldValue("identifierSearch").toString();
            results.put(key, value);
        }

        return results;

    }

}
