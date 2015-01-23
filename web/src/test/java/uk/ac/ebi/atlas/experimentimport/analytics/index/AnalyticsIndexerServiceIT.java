package uk.ac.ebi.atlas.experimentimport.analytics.index;

import com.google.common.collect.ImmutableList;
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
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineProteomicsAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexerServiceIT {

    @Inject
    AnalyticsDocumentStreamFactory streamFactory;

    @Inject
    EFOParentsLookupService efoParentsLookupService;

    @Inject
    BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    @Inject
    BaselineProteomicsAnalyticsInputStreamFactory baselineProteomicsAnalyticsInputStreamFactory;

    @Mock
    AnalyticsIndexer analyticsIndexerMock;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    private BaselineConditionsBuilder baselineConditionsBuilder;

    private AnalyticsIndexerService subject;

    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(analyticsIndexerMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any())).thenAnswer(storeDocuments());
        subject = new AnalyticsIndexerService(streamFactory, efoParentsLookupService, baselineAnalyticsInputStreamFactory, baselineProteomicsAnalyticsInputStreamFactory, analyticsIndexerMock, experimentTrader, baselineConditionsBuilder);
    }

    Answer<Integer> storeDocuments() {
        return new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
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
    public void indexBaselineExperimentAnalytics() {
        subject.indexBaselineExperimentAnalytics("E-MTAB-2039");
        assertThat(documents, hasSize(14));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.bioentityIdentifier, is("OS12G0515800"));
        assertThat(document.species, is("oryza sativa"));
        assertThat(document.experimentAccession, is("E-MTAB-2039"));
        assertThat(document.experimentType, is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document.defaultQueryFactorType, is("ORGANISM_PART"));
        assertThat(document.identifierSearch, is("OS12G0515800 Os.12120.1.S1_at Expressed protein; cDNA clone:J013098I09, full insert sequence  [Source:UniProt/SPTREMBL;Acc:Q2QPV9] Q2QPV9"));
        assertThat(document.conditionsSearch, is("emerging inflorescence 60 days after sowing Oryza sativa Japonica Group Nipponbare NCBITaxon:39947 whole post-emergence inflorescence"));
        assertThat(document.assayGroupId, is("g2"));
        assertThat(document.expressionLevel, is(0.2));
    }

    @Test
    public void indexProteomicsBaselineExperimentAnalytics() {
        subject.indexBaselineExperimentAnalytics("E-PROT-1");
        assertThat(documents, hasSize(3366));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.bioentityIdentifier, is("ENSG00000000003"));
        assertThat(document.species, is("homo sapiens"));
        assertThat(document.experimentAccession, is("E-PROT-1"));
        assertThat(document.experimentType, is(ExperimentType.PROTEOMICS_BASELINE));
        assertThat(document.defaultQueryFactorType, is("ORGANISM_PART"));
        assertThat(document.identifierSearch, is("ENSG00000000003 8173941 g2995860_3p_at g4099210_3p_a_at TSPAN6 integral to membrane 209108_at O43657 signal transduction 39361_f_at 4015421 4015403 Tetraspanin/Peripherin 4015420 209109_s_at 4015402 signal transducer activity 4015401 4015400 4015423 A_23_P171143 tetraspanin 6 [Source:HGNC Symbol;Acc:11858] TSPAN-6 4015409 4015408 4015407 4015406 4015405 4015404 Tetraspanin, EC2 domain positive regulation of I-kappaB kinase/NF-kappaB cascade T245 protein_coding 4015412 TM4SF6 4015414 4015413 39362_r_at 4015410 4015419 4015416 4015415 Tetraspanin 4015418 4015417"));
        assertThat(document.conditionsSearch, is("EFO_0000399 EFO_0000635 EFO_0000001 EFO_0001272 colon adult UBERON_0001155 OBI_0100026 snap#MaterialEntity NCBITaxon_9606 EFO_0000786 EFO_0000787 NCBITaxon_2759 Homo sapiens span#ProcessualEntity"));
        assertThat(document.assayGroupId, is("g5"));
        assertThat(document.expressionLevel, is(9.94E06));
    }

}