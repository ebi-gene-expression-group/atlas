package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline.BaselineAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.MicroArrayDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.RnaSeqDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsTrader;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexerServiceIT {


    @Mock
    SolrClient solrClient;
    @Mock AnalyticsIndexDAO analyticsIndexDAO;
    @Mock BaselineAnalyticsIndexerService baselineAnalyticsIndexerService;
    @Mock RnaSeqDiffAnalyticsIndexerService diffAnalyticsIndexerService;
    @Mock MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    IdentifierSearchTermsTrader identifierSearchTermsTrader;

    @Inject
    ExperimentDataPointStreamFactory experimentDataPointStreamFactory;

    AnalyticsIndexerService subject;
    
    @Before
    public void setUp(){
        subject = new AnalyticsIndexerService(solrClient,experimentDataPointStreamFactory, analyticsIndexDAO,
                baselineAnalyticsIndexerService, diffAnalyticsIndexerService, microArrayDiffAnalyticsIndexerService);
    }

    @Test
    public void testSomeExperiments(){
        testExperiment("E-MTAB-513");
        testExperiment("E-PROT-1");
        testExperiment("E-GEOD-48549");
        testExperiment("E-GEOD-22351");
    }

    public void testExperiment(String accession){
        Experiment experiment = experimentTrader.getPublicExperiment(accession);

        ImmutableMap<String, Map<BioentityPropertyName, Collection<String>>> ms =
                identifierSearchTermsTrader.getBioentityIdToIdentifierSearchMap2(experiment.getAccession());

        Iterable<SolrInputDocument> result = subject.solrInputDocuments(experiment, ms);

        int count = 0;

        for(SolrInputDocument solrInputDocument: result){
            count++;

            assertThat(solrInputDocument.size(), greaterThan(10));
            assertEquals(experiment.getSpecies().mappedName, solrInputDocument.getField("species").getValue());
        }

        assertTrue(count>100);
    }




}