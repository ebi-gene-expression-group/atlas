package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchDao {
    public static final String DIFFERENTIAL_ONLY = "experimentType:rnaseq_mrna_differential OR experimentType:microarray_1colour_mrna_differential or experimentType:microarray_2colour_mrna_differential or experimentType:microarray_1colour_microrna_differential";

    private static final Logger LOGGER = Logger.getLogger(DifferentialAnalyticsSearchDao.class);

    private SolrServer analyticsSolrServer;

    @Inject
    public DifferentialAnalyticsSearchDao(@Qualifier("analyticsSolrServer") SolrServer analyticsSolrServer) {
        this.analyticsSolrServer = analyticsSolrServer;
    }


    public Multimap<String, NameValue> fetchFacets(GeneQuery geneQuery) {
        QueryResponse queryResponse = query(buildQuery(geneQuery));
        return extractFacetValues(queryResponse);
    }

    private ImmutableMultimap<String, NameValue> extractFacetValues(QueryResponse queryResponse) {
        ImmutableMultimap.Builder<String, NameValue> builder = ImmutableMultimap.builder();

        for (FacetField facetField : queryResponse.getFacetFields()) {
            String name = facetField.getName();

            for (FacetField.Count facetFieldCount : facetField.getValues()) {
                builder.put(name, NameValue.create(facetFieldCount.getName(), facetFieldCount.getName()));
            }


        }
        return builder.build();

    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            QueryResponse queryResponse = analyticsSolrServer.query(solrQuery);
            //LOGGER.debug("<query> Solr query time: " + queryResponse.getQTime() + "ms, status code: " + queryResponse.getStatus());
            return queryResponse;
        } catch (SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        } catch (SolrException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private SolrQuery buildQuery(GeneQuery geneQuery) {

        String identifierSearch = geneQuery.asString(); //TODO support multiple gene queries

        //eg: http://localhost:8983/solr/analytics/select?q=*%3A*&rows=0&fq=experimentType%3Arnaseq_mrna_differential+OR+experimentType%3Amicroarray_1colour_mrna_differential+or+experimentType%3Amicroarray_2colour_mrna_differential+or+experimentType%3Amicroarray_1colour_microrna_differential&wt=json&indent=true&facet=true&facet.field=kingdom&facet.field=species&facet.field=experimentType&facet.field=factors&facet.field=numReplicates&facet.field=regulation&facet.mincount=1&facet.limit=100
        SolrQuery solrQuery = new SolrQuery("identifierSearch:" + identifierSearch);
        solrQuery.setRows(0);
        solrQuery.setFilterQueries(DIFFERENTIAL_ONLY);
        solrQuery.setFacet(true);
        solrQuery.addFacetField("kingdom");
        solrQuery.addFacetField("species");
        solrQuery.addFacetField("experimentType");
        solrQuery.addFacetField("factors");
        solrQuery.addFacetField("numReplicates");
        solrQuery.addFacetField("regulation");
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(100);
        return solrQuery;
    }


}
