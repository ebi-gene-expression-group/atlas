package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AutocompleteGroupedPropertyValueQueryBuilderIT {

    @Inject
    private SolrQueryBuilderFactory solrQueryBuilderFactory;

    @Inject
    private GxaSolrClient solrServer;

    @Test
    public void asp() {
        //eg: http://lime:8983/solr/gxa/select?q=property_value_edgengram%3A%22asp%22+AND+(bioentity_type%3A%22ensgene%22+OR+bioentity_type%3A%22mirna%22+OR+bioentity_type%3A%22ensprotein%22+OR+bioentity_type%3A%22enstranscript%22)+AND+(property_name%3A%22symbol%22)&rows=15&wt=json&indent=true&group=true&group.field=property_value&group.main=true
        String species = "homo sapiens";
        String propertyNames = "symbol";
        SolrQuery solrQuery = solrQueryBuilderFactory.createAutocompleteGroupedPropertyValueQueryBuilder()
                .withSpecies(SpeciesUtils.convertToEnsemblSpecies(species))
                .withBioentityTypes(BioentityType.getAllSolrAliases())
                .withPropertyNames(propertyNames)
                .build("asp");

        QueryResponse queryResponse = solrServer.query(solrQuery);

        SolrDocumentList results = queryResponse.getResults();

        assertThat(results, hasSize(10));

        SolrDocument doc = results.get(0);

        String propertyValue = doc.getFieldValue("property_value").toString();
        String propertyName = doc.getFieldValue("property_name").toString();

        assertThat(propertyValue, is("ASPA"));
        assertThat(propertyName, is("symbol"));

    }
}