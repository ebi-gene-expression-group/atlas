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
        assertThat(document.getIdentifierSearch(), is("OS12G0515800 Os.12120.1.S1_at Expressed protein; cDNA clone:J013098I09, full insert sequence  [Source:UniProtKB/TrEMBL;Acc:Q2QPV9] Os12g0515800|mRNA|AK120440|UTR Q2QPV9 protein_coding"));
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
        assertThat(document.getIdentifierSearch(), is("ENSGALG00000009623"));
        assertThat(document.getConditionsSearch(), is("EFO_0001265 EFO_0000399 Gallus gallus EFO_0000635 EFO_0000001 EFO_0001272 adult OBI_0100026 approx 1 year snap#MaterialEntity EFO_0000786 NCBITaxon_2759 EFO_0000787 NCBITaxon_9031 UBERON_0002037 female snap#SpecificallyDependentContinuant snap#Quality EFO_0000695 span#ProcessualEntity Facility of Linkoping University, Sweden cerebellum male EFO_0001266"));
        assertThat(document.getAssayGroupId(), is("g52"));
        assertThat(document.getExpressionLevel(), is(0.4));

        AnalyticsDocument document2 = documents.get(2);
        assertThat(document2.getBioentityIdentifier(), is("ENSMMUG00000032178"));
        assertThat(document2.getSpecies(), is("macaca mulatta"));
        assertThat(document2.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(document2.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document2.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document2.getIdentifierSearch(), is("ENSMMUG00000032178 Endonuclease/exonuclease/phosphatase IPR005135 Uncharacterized protein  [Source:UniProtKB/TrEMBL;Acc:F7FU52] protein_coding F7FU52"));
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
        assertThat(document.getIdentifierSearch(), is("ENSG00000000003 GO:0005515 g2995860_3p_at TSPAN6 O43657 39361_f_at negative regulation of viral-induced cytoplasmic pattern recognition receptor signaling pathway IPR018503 4015421 209109_s_at 4015420 Tetraspanin/Peripherin signal transducer activity 64593_at 4015423 4015422 TSPAN-6 GO:0043123 IPR000301 negative regulation of NIK/NF-kappaB signaling GO:0070062 TM4SF6 A_24_P83262 extracellular exosome 39362_r_at A0A087WYV6 Tetraspanin 8173941 g4099210_3p_a_at GO:1901223 209108_at signal transduction 4015403 4015402 4015401 4015400 A_23_P171143 A0A087WZU5 4015409 GO:0004871 4015408 IPR018499 4015407 4015406 4015405 4015404 Tetraspanin, conserved site T245 GO:0039532 protein_coding tetraspanin 6 [Source:HGNC Symbol;Acc:HGNC:11858] integral component of membrane 4015412 GO:0007165 protein binding 4015414 4015413 4015410 4015419 GO:0016021 4015416 4015415 4015418 positive regulation of I-kappaB kinase/NF-kappaB signaling 4015417"));
        assertThat(document.getConditionsSearch(), is("EFO_0000399 EFO_0000635 EFO_0000001 EFO_0001272 colon adult UBERON_0001155 OBI_0100026 snap#MaterialEntity NCBITaxon_9606 EFO_0000786 EFO_0000787 NCBITaxon_2759 Homo sapiens span#ProcessualEntity"));
        assertThat(document.getAssayGroupId(), is("g5"));
        assertThat(document.getExpressionLevel(), is(9.94E06));
    }

}