package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

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
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
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
public class RnaSeqDiffAnalyticsIndexerServiceIT {

    private RnaSeqDiffAnalyticsIndexerService subject;

    @Inject
    EFOLookupService efoParentsLookupService;

    @Inject
    private DifferentialConditionsBuilder conditionsBuilder;

    @Inject
    private RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;

    @Mock
    private AnalyticsIndexDAO analyticsIndexDAOMock;

    @Inject
    private ExperimentTrader experimentTrader;


    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        RnaSeqDiffAnalyticsDocumentStreamIndexer rnaSeqDiffAnalyticsDocumentStreamIndexer = new RnaSeqDiffAnalyticsDocumentStreamIndexer(rnaSeqDifferentialAnalyticsInputStreamFactory, analyticsIndexDAOMock);
        given(analyticsIndexDAOMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any(), anyInt())).willAnswer(storeDocuments());
        subject = new RnaSeqDiffAnalyticsIndexerService(efoParentsLookupService, conditionsBuilder,
                rnaSeqDifferentialAnalyticsInputStreamFactory, rnaSeqDiffAnalyticsDocumentStreamIndexer);
    }

    Answer<Integer> storeDocuments() {
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

    @Test
    public void index() {

        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment("E-GEOD-22351");
        subject.index(experiment, ImmutableMap.of("", ""), 1024);
        assertThat(documents, hasSize(greaterThan(0)));

        // This Matcher cast shouldn’t be necessary and I’m not sure why it is :/
        assertThat(documents, everyItem((Matcher)hasProperty("species", is("mus musculus"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentAccession", is("E-GEOD-22351"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentType", is(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL))));
        assertThat(documents, everyItem((Matcher)hasProperty("bioentityIdentifier")));
        assertThat(documents, everyItem((Matcher)hasProperty("contrastId")));
        // Seriously? Notice the change in case pValue -> PValue
        assertThat(documents, everyItem((Matcher)hasProperty("PValue")));
    }

}