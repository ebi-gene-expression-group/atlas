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
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsInputStreamFactory;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsIndexerServiceIT {

    private BaselineAnalyticsIndexerService subject;

    @Inject
    BaselineAnalyticsDocumentStreamFactory streamFactory;

    @Inject
    EFOLookupService efoLookupService;

    @Inject
    BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    @Inject
    ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;

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
        subject = new BaselineAnalyticsIndexerService(streamFactory, efoLookupService, baselineAnalyticsInputStreamFactory, proteomicsBaselineAnalyticsInputStreamFactory, analyticsIndexDAOMock, baselineConditionsBuilder);
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

    @Test
    public void indexRnaSeqBaselineExperiment() {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-MTAB-513");
        subject.index(experiment, ImmutableMap.of("", ""), 0);

        assertThat(documents, hasSize(greaterThan(10000)));

        // This Matcher cast shouldn’t be necessary and I’m not sure why it is :/
        assertThat(documents, everyItem((Matcher)hasProperty("species", is("homo sapiens"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentAccession", is("E-MTAB-513"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentType", is(ExperimentType.RNASEQ_MRNA_BASELINE))));
        assertThat(documents, everyItem((Matcher)hasProperty("defaultQueryFactorType", is("ORGANISM_PART"))));
        assertThat(documents, everyItem((Matcher)hasProperty("expressionLevel")));
        assertThat(documents, everyItem((Matcher)hasProperty("bioentityIdentifier")));
        assertThat(documents, everyItem((Matcher)hasProperty("assayGroupId")));
    }

    @Test
    public void indexProteomicsBaselineExperiment() {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1");
        subject.index(experiment, ImmutableMap.of("", ""), 1024);

        assertThat(documents, hasSize(greaterThan(10000)));

        // This Matcher cast shouldn’t be necessary and I’m not sure why it is :/
        assertThat(documents, everyItem((Matcher)hasProperty("species", is("homo sapiens"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentAccession", is("E-PROT-1"))));
        assertThat(documents, everyItem((Matcher)hasProperty("experimentType", is(ExperimentType.PROTEOMICS_BASELINE))));
        assertThat(documents, everyItem((Matcher)hasProperty("defaultQueryFactorType", is("ORGANISM_PART"))));
        assertThat(documents, everyItem((Matcher)hasProperty("expressionLevel")));
        assertThat(documents, everyItem((Matcher)hasProperty("bioentityIdentifier")));
        assertThat(documents, everyItem((Matcher)hasProperty("assayGroupId")));
    }

}