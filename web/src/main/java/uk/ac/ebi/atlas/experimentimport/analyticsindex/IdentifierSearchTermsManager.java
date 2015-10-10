package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 10/10/15.
 */
@Named
public class IdentifierSearchTermsManager {

    private static final Logger LOGGER = Logger.getLogger(IdentifierSearchTermsManager.class);

    private static SolrClient analyticsSolrClient;
    private static SolrClient gxaSolrClient;
    private static String[] searchProperties;

    @Inject
    public IdentifierSearchTermsManager(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient, @Qualifier("solrClient") SolrClient gxaSolrClient,
                                        @Value("#{configuration['index.property_names.identifier.search']}") String[] searchProperties) {
        this.analyticsSolrClient = analyticsSolrClient;
        this.gxaSolrClient = gxaSolrClient;
        this.searchProperties = searchProperties;
    }

    public void updateAllBioentityIdentifiers() {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(1000000);
        query.addFacetField("bioentityIdentifier");

        ImmutableList<String> allBioentities;
        try {
            ImmutableList.Builder<String> builder = new ImmutableList.Builder<>();
            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            FacetField bioentityIdentifierFacetField = analyticsSolrClient.query(query).getFacetField("bioentityIdentifier");
            for (FacetField.Count bioentityIdentifierCount: bioentityIdentifierFacetField.getValues()) {
               builder.add(bioentityIdentifierCount.getName());
            }

            stopWatch.stop();
            LOGGER.debug(String.format("%,d bioentities fetched in %s seconds", bioentityIdentifierFacetField.getValueCount(), stopWatch.getTotalTimeSeconds()));

            allBioentities = builder.build();



            stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            query = new SolrQuery();
            query.setRows(1000);
            query.setFilterQueries("property_name:(\"" + Joiner.on("\" OR \"").join(searchProperties) + "\")");
            query.setFields("property_value");
            for (String bioentityIdentifier : allBioentities) {
                query.setQuery("bioentity_identifier:\"" + bioentityIdentifier + "\"");
                gxaSolrClient.query(query);
            }

            stopWatch.stop();
            LOGGER.debug(String.format("Bioentity properties for %,d bioentities fetched in %s seconds", allBioentities.size(), stopWatch.getTotalTimeSeconds()));


        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
