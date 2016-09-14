package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsIndexerServiceIT {

    private BaselineAnalyticsIndexerService subject;

    @Inject
    EFOLookupService efoLookupService;

    @Inject
    BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    @Mock
    AnalyticsIndexDAO analyticsIndexDAOMock;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    private BaselineConditionsBuilder baselineConditionsBuilder;

    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        given(analyticsIndexDAOMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any(), anyInt())).willAnswer(storeDocuments());
        subject = new BaselineAnalyticsIndexerService(efoLookupService, baselineAnalyticsInputStreamFactory, analyticsIndexDAOMock, baselineConditionsBuilder);
    }

    private Answer<Integer> storeDocuments() {
        return new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                @SuppressWarnings("unchecked")
                Iterable<AnalyticsDocument> documentStream = (Iterable<AnalyticsDocument>) invocationOnMock.getArguments()[0];

                ImmutableList.Builder<AnalyticsDocument> documentsBuilder = ImmutableList.builder();
                for (AnalyticsDocument document : documentStream) {
                    documentsBuilder.add(document);
                }
                documents = documentsBuilder.build();

                return documents.size();
            }

        };
    }


    private void indexExperiment(String accession){
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(accession);
        subject.index(experiment, ImmutableMap.of("", ""), 1024);
        assertAboutDocuments(accession,experiment.getType());
    }

    @Test
    public void testSomeExperiments() {
        indexExperiment("E-MTAB-513");
        indexExperiment("E-PROT-1");
    }

    private void assertAboutDocuments(String accession, ExperimentType type) {
        assertThat(documents, hasSize(greaterThan(10000)));
        // This Matcher cast shouldn’t be necessary and I’m not sure why it is :/
        assertThat(documents, everyItem((Matcher)hasProperty("species", is("homo sapiens"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentAccession", is(accession))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentType", is(type))));
        assertThat(documents, everyItem((Matcher)hasProperty("defaultQueryFactorType", is("ORGANISM_PART"))));
        assertThat(documents, everyItem((Matcher)hasProperty("expressionLevel")));
        assertThat(documents, everyItem((Matcher)hasProperty("bioentityIdentifier")));
        assertThat(documents, everyItem((Matcher)hasProperty("assayGroupId")));
    }


}