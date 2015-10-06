package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

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
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.ProteomicsBaselineAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsIndexerServiceIT {

    @Inject
    BaselineAnalyticsDocumentStreamFactory streamFactory;

    @Inject
    EFOParentsLookupService efoParentsLookupService;

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

    private BaselineAnalyticsIndexerService subject;

    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(analyticsIndexDAOMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any(), anyInt())).thenAnswer(storeDocuments());
        subject = new BaselineAnalyticsIndexerService(streamFactory, efoParentsLookupService, baselineAnalyticsInputStreamFactory, proteomicsBaselineAnalyticsInputStreamFactory, analyticsIndexDAOMock, baselineConditionsBuilder);
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
    public void indexBaselineExperimentAnalytics() {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-MTAB-2039");
        subject.index(experiment, 0);
        assertThat(documents, hasSize(14));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.getBioentityIdentifier(), is("OS12G0515800"));
        assertThat(document.getSpecies(), is("oryza sativa"));
        assertThat(document.getExperimentAccession(), is("E-MTAB-2039"));
        assertThat(document.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document.getConditionsSearch(), is("EFO_0000001 EFO_0000635 Oryza sativa Japonica Group 60 days after sowing EFO_0001029 OBI_0100026 NCBITaxon_33090 snap#MaterialEntity NCBITaxon_39947 emerging inflorescence EFO_0000786 NCBITaxon_2759 EFO_0000789 Nipponbare EFO_0000992 whole post-emergence inflorescence"));
        assertThat(document.getAssayGroupId(), is("g2"));
        assertThat(document.getExpressionLevel(), is(0.2));
    }

    @Test
    public void indexMultiSpeciesBaselineExperimentAnalytics() {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-GEOD-30352");
        subject.index(experiment, 1024);

        assertThat(documents, hasSize(2179));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.getBioentityIdentifier(), is("ENSGALG00000009623"));
        assertThat(document.getSpecies(), is("gallus gallus"));
        assertThat(document.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(document.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document.getConditionsSearch(), is("EFO_0001265 EFO_0000399 Gallus gallus EFO_0000635 EFO_0000001 EFO_0001272 adult OBI_0100026 approx 1 year snap#MaterialEntity EFO_0000786 NCBITaxon_2759 EFO_0000787 NCBITaxon_9031 UBERON_0002037 female snap#SpecificallyDependentContinuant snap#Quality EFO_0000695 span#ProcessualEntity Facility of Linkoping University, Sweden cerebellum male EFO_0001266"));
        assertThat(document.getAssayGroupId(), is("g52"));
        assertThat(document.getExpressionLevel(), is(0.4));

        AnalyticsDocument document2 = documents.get(2);
        assertThat(document2.getBioentityIdentifier(), is("ENSMMUG00000032178"));
        assertThat(document2.getSpecies(), is("macaca mulatta"));
        assertThat(document2.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(document2.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document2.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document2.getConditionsSearch(), is("8 year EFO_0000001 EFO_0000635 male Macaque facility in Souzhou, China OBI_0100026 snap#MaterialEntity EFO_0000786 NCBITaxon_2759 EFO_0000787 NCBITaxon_9544 UBERON_0002037 snap#Quality snap#SpecificallyDependentContinuant Macaca mulatta EFO_0000695 EFO_0001266 cerebellum EFO_0001265 female"));
        assertThat(document2.getAssayGroupId(), is("g27"));
        assertThat(document2.getExpressionLevel(), is(17.0));
    }

    @Test
    public void indexProteomicsBaselineExperimentAnalytics() {
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1");
        subject.index(experiment, 1024);

        assertThat(documents, hasSize(3366));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.getBioentityIdentifier(), is("ENSG00000000003"));
        assertThat(document.getSpecies(), is("homo sapiens"));
        assertThat(document.getExperimentAccession(), is("E-PROT-1"));
        assertThat(document.getExperimentType(), is(ExperimentType.PROTEOMICS_BASELINE));
        assertThat(document.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document.getConditionsSearch(), is("EFO_0000399 EFO_0000635 EFO_0000001 EFO_0001272 colon adult UBERON_0001155 OBI_0100026 snap#MaterialEntity NCBITaxon_9606 EFO_0000786 EFO_0000787 NCBITaxon_2759 Homo sapiens span#ProcessualEntity"));
        assertThat(document.getAssayGroupId(), is("g5"));
        assertThat(document.getExpressionLevel(), is(9.94E06));
    }

}