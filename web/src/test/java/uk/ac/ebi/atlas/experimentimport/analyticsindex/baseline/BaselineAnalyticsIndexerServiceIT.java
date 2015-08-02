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
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDao;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerMonitor;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.baseline.BaselineConditionsBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    AnalyticsIndexDao analyticsIndexDaoMock;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    private BaselineConditionsBuilder baselineConditionsBuilder;

    private BaselineAnalyticsIndexerService subject;

    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(analyticsIndexDaoMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any(), Matchers.<Integer>any())).thenAnswer(storeDocuments());
        subject = new BaselineAnalyticsIndexerService(streamFactory, efoParentsLookupService, baselineAnalyticsInputStreamFactory, proteomicsBaselineAnalyticsInputStreamFactory, analyticsIndexDaoMock, baselineConditionsBuilder);
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
        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-MTAB-2039");
        subject.index(experiment, 0);
        assertThat(documents, hasSize(14));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.getBioentityIdentifier(), is("OS12G0515800"));
        assertThat(document.getSpecies(), is("oryza sativa"));
        assertThat(document.getExperimentAccession(), is("E-MTAB-2039"));
        assertThat(document.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document.getIdentifierSearch(), is("OS12G0515800 Os.12120.1.S1_at Expressed protein; cDNA clone:J013098I09, full insert sequence  [Source:UniProtKB/TrEMBL;Acc:Q2QPV9] Q2QPV9 protein_coding"));
        assertThat(document.getConditionsSearch(), is("emerging inflorescence 60 days after sowing Oryza sativa Japonica Group Nipponbare NCBITaxon:39947 whole post-emergence inflorescence"));
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
        assertThat(document.getConditionsSearch(), is("UBERON:0002037 EFO EFO:0001272 EFO:0001266 Gallus gallus male adult approx 1 year Facility of Linkoping University, Sweden cerebellum female EFO:0001265"));
        assertThat(document.getAssayGroupId(), is("g52"));
        assertThat(document.getExpressionLevel(), is(0.4));

        AnalyticsDocument document2 = documents.get(2);
        assertThat(document2.getBioentityIdentifier(), is("ENSMMUG00000032178"));
        assertThat(document2.getSpecies(), is("macaca mulatta"));
        assertThat(document2.getExperimentAccession(), is("E-GEOD-30352"));
        assertThat(document2.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(document2.getDefaultQueryFactorType(), is("ORGANISM_PART"));
        assertThat(document2.getIdentifierSearch(), is("ENSMMUG00000032178 Endonuclease/exonuclease/phosphatase Uncharacterized protein  [Source:UniProtKB/TrEMBL;Acc:F7FU52] protein_coding F7FU52"));
        assertThat(document2.getConditionsSearch(), is("UBERON:0002037 8 year EFO female EFO:0001265 Macaque facility in Souzhou, China Macaca mulatta cerebellum EFO:0001266 male"));
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
        assertThat(document.getIdentifierSearch(), is("ENSG00000000003 biological_process g2995860_3p_at TSPAN6 O43657 39361_f_at negative regulation of viral-induced cytoplasmic pattern recognition receptor signaling pathway 4015421 209109_s_at 4015420 Tetraspanin/Peripherin signal transducer activity 64593_at 4015423 4015422 TSPAN-6 negative regulation of NIK/NF-kappaB signaling Tetraspanin, EC2 domain TM4SF6 A_24_P83262 39362_r_at A0A087WYV6 Tetraspanin extracellular vesicular exosome 8173941 g4099210_3p_a_at organelle 209108_at signal transduction 4015403 4015402 4015401 4015400 molecular_function A_23_P171143 A0A087WZU5 4015409 4015408 4015407 4015406 4015405 4015404 T245 extracellular region protein_coding tetraspanin 6 [Source:HGNC Symbol;Acc:HGNC:11858] 4015412 integral component of membrane protein binding 4015414 4015413 4015410 4015419 cellular_component 4015416 4015415 4015418 positive regulation of I-kappaB kinase/NF-kappaB signaling 4015417"));
        assertThat(document.getConditionsSearch(), is("EFO_0000399 EFO_0000635 EFO_0000001 EFO_0001272 colon adult UBERON_0001155 OBI_0100026 snap#MaterialEntity NCBITaxon_9606 EFO_0000786 EFO_0000787 NCBITaxon_2759 Homo sapiens span#ProcessualEntity"));
        assertThat(document.getAssayGroupId(), is("g5"));
        assertThat(document.getExpressionLevel(), is(9.94E06));
    }

}