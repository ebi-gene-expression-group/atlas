package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class ConditionsIndexingServiceIT {

    private SolrClient baselineConditionsCore;

    private SolrClient differentialConditionsCore;

    @Inject
    private ConditionsLookupService conditionsLookupService;

    @Inject
    private ConfigurationTrader configurationTrader;

    @Inject
    private CondensedSdrfParser condensedSdrfParser;

    private ConditionsIndexingService subject;

    @Before
    public void setUp(){

        baselineConditionsCore = Mockito.mock(SolrClient.class);
        differentialConditionsCore = Mockito.mock(SolrClient.class);
        subject = new ConditionsIndexingService(baselineConditionsCore,differentialConditionsCore,
                conditionsLookupService,configurationTrader);
    }

    @Test
    public void indexBaseline() throws Exception{
        String accession = "TEST-RNASEQ-BASELINE";
        ExperimentType type = ExperimentType.RNASEQ_MRNA_BASELINE;

        ExperimentDesign experimentDesign = condensedSdrfParser.parse(accession, type).getExperimentDesign();

        subject.indexBaseline(accession, experimentDesign);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(baselineConditionsCore).addBeans(addBeansQueryCaptor.capture());

        Collection result = addBeansQueryCaptor.getValue();
        assertThat(result.size(), greaterThan(0));
        for(Object o : result){
            assertEquals( Condition.class, o.getClass());
            assertThat(((Condition) o).getExperimentAccession(), is(accession));
        }

        verifyZeroInteractions(differentialConditionsCore);
    }

    @Test
    public void indexDifferential() throws Exception {
        String accession = "TEST-RNASEQ-DIFFERENTIAL";
        ExperimentType type = ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;

        ExperimentDesign experimentDesign = condensedSdrfParser.parse(accession, type).getExperimentDesign();

        subject.indexDifferential(accession, experimentDesign);

        ArgumentCaptor<Collection> addBeansQueryCaptor = ArgumentCaptor.forClass(Collection.class);

        verify(differentialConditionsCore).addBeans(addBeansQueryCaptor.capture());

        Collection result = addBeansQueryCaptor.getValue();
        assertThat(result.size(), greaterThan(0));
        for(Object o : result){
            assertEquals( DifferentialCondition.class, o.getClass());
            assertThat(((DifferentialCondition) o).getExperimentAccession(), is(accession));
        }

        verifyZeroInteractions(baselineConditionsCore);
    }

}