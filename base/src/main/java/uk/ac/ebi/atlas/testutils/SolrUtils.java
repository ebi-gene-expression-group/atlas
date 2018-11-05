package uk.ac.ebi.atlas.testutils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;

import static java.lang.Math.toIntExact;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.AnalyticsSchemaField;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.SPECIES;

import uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class SolrUtils {
    private static final int MAX_ROWS = 10000;
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private AnalyticsCollectionProxy analyticsCollectionProxy;
    private BioentitiesCollectionProxy bioentitiesCollectionProxy;

    public SolrUtils(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        analyticsCollectionProxy = solrCloudCollectionProxyFactory.create(AnalyticsCollectionProxy.class);
        bioentitiesCollectionProxy = solrCloudCollectionProxyFactory.create(BioentitiesCollectionProxy.class);
    }

    public String fetchRandomGeneIdFromAnalytics() {
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder
                .setFieldList(AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .setRows(MAX_ROWS);

        return getRandomSolrDocument(analyticsCollectionProxy.query(queryBuilder).getResults())
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    public String fetchRandomGeneIdFromAnalytics(AnalyticsSchemaField field, String term) {
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder
                .addQueryFieldByTerm(field, term)
                .setFieldList(AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .setRows(MAX_ROWS);

        return getRandomSolrDocument(analyticsCollectionProxy.query(queryBuilder).getResults())
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    public String fetchRandomGeneWithoutSymbolFromAnalytics() {
        SolrQuery solrQuery = new SolrQuery("-keyword_symbol:*");
        solrQuery.setRows(MAX_ROWS);
        return getRandomSolrDocument(analyticsCollectionProxy.rawQuery(solrQuery).getResults())
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    public String fetchRandomGeneOfSpecies(String species) {
        SolrQueryBuilder<BioentitiesCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder.addFilterFieldByTerm(SPECIES, species);
        queryBuilder.setFieldList(BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER);
        queryBuilder.setRows(MAX_ROWS);
        return getRandomSolrDocument(bioentitiesCollectionProxy.query(queryBuilder).getResults())
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    private SolrDocument getRandomSolrDocument(SolrDocumentList solrDocumentList) {
        return solrDocumentList.get(RNG.nextInt(solrDocumentList.size()));
    }
}
