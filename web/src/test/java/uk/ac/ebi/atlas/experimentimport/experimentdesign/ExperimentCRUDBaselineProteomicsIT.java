package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.*;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.*;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.solr.admin.index.conditions.Condition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTraderFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentCRUDBaselineProteomicsIT {

    public static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    public static final String ORGANISM = "organism";

    private static final String E_PROT_1_LINE_1_GENE_ID = "ENSG00000000003";
    private static final double E_PROT_1_LINE_1_G5_ABUNDANCE = 9.94E+06;
    private static final double E_PROT_1_LINE_1_G15_ABUNDANCE = 6.76E+06;
    private static final double E_PROT_1_LINE_1_G16_ABUNDANCE = 2.29E-05;
    private static final double E_PROT_1_LINE_1_G18_ABUNDANCE = 7.31E-06;
    private static final double E_PROT_1_LINE_1_G22_ABUNDANCE = 6.56E+06;
    private static final double E_PROT_1_LINE_1_G28_ABUNDANCE = 5.20E-05;
    private static final String E_PROT_1_LINE_2_GENE_ID = "ENSG00000000419";
    private static final double E_PROT_1_LINE_2_G1_ABUNDANCE = 8.43E-05;
    private static final double E_PROT_1_LINE_2_G2_ABUNDANCE = 2.20E-05;
    private static final double E_PROT_1_LINE_2_G3_ABUNDANCE = 1.02E-05;
    private static final double E_PROT_1_LINE_2_G4_ABUNDANCE = 2.55E-05;
    private static final double E_PROT_1_LINE_2_G5_ABUNDANCE = 8.32E+06;
    private static final double E_PROT_1_LINE_2_G6_ABUNDANCE = 2.07E-05;
    private static final double E_PROT_1_LINE_2_G7_ABUNDANCE = 1.39E-05;
    private static final double E_PROT_1_LINE_2_G8_ABUNDANCE = 1.35E-05;
    private static final double E_PROT_1_LINE_2_G10_ABUNDANCE = 2.25E-05;
    private static final double E_PROT_1_LINE_2_G11_ABUNDANCE = 3.47E-05;
    private static final double E_PROT_1_LINE_2_G12_ABUNDANCE = 7.33E-05;
    private static final double E_PROT_1_LINE_2_G14_ABUNDANCE = 4.28E-05;
    private static final double E_PROT_1_LINE_2_G15_ABUNDANCE = 6.86E-05;
    private static final double E_PROT_1_LINE_2_G16_ABUNDANCE = 2.99E-05;
    private static final double E_PROT_1_LINE_2_G17_ABUNDANCE = 4.28E-05;
    private static final double E_PROT_1_LINE_2_G18_ABUNDANCE = 1.63E-05;
    private static final double E_PROT_1_LINE_2_G19_ABUNDANCE = 1.62E-05;
    private static final double E_PROT_1_LINE_2_G20_ABUNDANCE = 3.35E-05;
    private static final double E_PROT_1_LINE_2_G22_ABUNDANCE = 5.12E-05;
    private static final double E_PROT_1_LINE_2_G23_ABUNDANCE = 1.27E-05;
    private static final double E_PROT_1_LINE_2_G24_ABUNDANCE = 8.53E+06;
    private static final double E_PROT_1_LINE_2_G25_ABUNDANCE = 3.48E-05;
    private static final double E_PROT_1_LINE_2_G26_ABUNDANCE = 1.38E-05;
    private static final double E_PROT_1_LINE_2_G27_ABUNDANCE = 4.68E-05;
    private static final double E_PROT_1_LINE_2_G28_ABUNDANCE = 7.33E-05;
    private static final double E_PROT_1_LINE_2_G29_ABUNDANCE = 3.24E-05;
    private static final double E_PROT_1_LINE_2_G30_ABUNDANCE = 4.82E-05;


    @Inject
    private ExperimentChecker experimentChecker;

    @Mock
    BaselineAnalyticsDao baselineAnalyticsDao;

    @Mock
    AnalyticsLoaderFactory analyticsLoaderFactory;

    @Inject
    BaselineProteomicsAnalyticsInputStreamFactory baselineProteomicsAnalyticsInputStreamFactory;

    @Inject
    private ExperimentMetadataCRUDFactory experimentMetadataCRUDFactory;

    @Inject
    private ConditionsIndexTraderFactory conditionsIndexTraderFactory;

    @Mock
    private AnalyticsDao analyticsDao;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private SolrServer solrServer;

    private ExperimentCRUD subject;

    @Inject
    private ConfigurationTrader configurationTrader;

    @Captor
    ArgumentCaptor<Collection<Condition>> collectionArgumentCaptor;

    @Captor
    ArgumentCaptor<ExperimentDTO> experimentDTOArgumentCaptor;

    @Captor
    ArgumentCaptor<ExperimentDesign> experimentDesignArgumentCaptor;

    @Captor
    ArgumentCaptor<BaselineProteomicsAnalyticsInputStream> baselineProteomicsAnalyticsInputStreamArgumentCaptor;

    @Mock
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder;
    @Mock
    private ExperimentDesignFileWriter experimentDesignFileWriter;

    private static final ExperimentDTO E_PROT_1_DTO = new ExperimentDTO(E_PROT_1,
                                                   ExperimentType.PROTEOMICS_BASELINE,
                                                   Collections.<String>emptySet(),
                                                   "title", new Date(), false, UUID.randomUUID().toString());

    @Before
    public void mockOutDatabaseAndSolrAndArrayExpress() throws IOException {
        MockitoAnnotations.initMocks(this);

        ConditionsIndexTrader conditionsIndexTrader = conditionsIndexTraderFactory.create(solrServer);

        when(experimentDesignFileWriterBuilder.forExperimentAccession(anyString())).thenReturn(experimentDesignFileWriterBuilder);
        when(experimentDesignFileWriterBuilder.withExperimentType(any(ExperimentType.class))).thenReturn(experimentDesignFileWriterBuilder);
        when(experimentDesignFileWriterBuilder.build()).thenReturn(experimentDesignFileWriter);

        ExperimentMetadataCRUD experimentMetadataCRUD = experimentMetadataCRUDFactory.create(experimentDesignFileWriterBuilder, experimentDao, conditionsIndexTrader);

        subject = new ExperimentCRUD();
        subject.setAnalyticsDao(analyticsDao);

        when(analyticsLoaderFactory.getLoader(ExperimentType.PROTEOMICS_BASELINE)).thenReturn(new BaselineProteomicsAnalyticsLoader(baselineAnalyticsDao, baselineProteomicsAnalyticsInputStreamFactory));

        subject.setAnalyticsLoaderFactory(analyticsLoaderFactory);
        subject.setExperimentChecker(experimentChecker);
        subject.setConfigurationTrader(configurationTrader);
        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);
    }

    private void setupDao() {
        when(experimentDao.findExperiment(E_PROT_1, true)).thenThrow(new ResourceNotFoundException("")).thenReturn(E_PROT_1_DTO);
        when(experimentDao.findPublicExperiment(E_PROT_1)).thenReturn(E_PROT_1_DTO);
    }

    @Test
    public void importExperiment_AddsConditionsToSolr() throws IOException, SolrServerException {
        setupDao();
        subject.importExperiment(E_PROT_1, false);

        verify(solrServer).addBeans(collectionArgumentCaptor.capture());

        Collection<Condition> beans = collectionArgumentCaptor.getValue();

        assertThat(beans, hasSize(30));
        assertThat(beans, hasItem(new Condition(E_PROT_1,"g10", ImmutableList.of("EFO_0000399", "EFO_0000635", "EFO_0000001", "EFO_0001272", "adult", "UBERON_0002113", "OBI_0100026", "snap#MaterialEntity", "NCBITaxon_9606", "EFO_0000786", "EFO_0000787", "NCBITaxon_2759", "Homo sapiens", "kidney", "span#ProcessualEntity"))));
    }

    @Test
    public void importExperiment_AddsToExperimentTableInDatabase() throws IOException, SolrServerException {
        setupDao();
        subject.importExperiment(E_PROT_1, false);

        verify(experimentDao).addExperiment(experimentDTOArgumentCaptor.capture(), any(Optional.class));

        ExperimentDTO experimentDTO = experimentDTOArgumentCaptor.getValue();

        assertThat(experimentDTO.getExperimentAccession(), is(E_PROT_1));
        assertThat(experimentDTO.getExperimentType(), is(ExperimentType.PROTEOMICS_BASELINE));
        assertThat(experimentDTO.getSpecies(), contains("Homo sapiens"));
        assertThat(experimentDTO.getPubmedIds(), contains("24669763", "24870542"));
        assertThat(experimentDTO.getTitle(), is("A draft map of the human proteome"));
        assertThat(experimentDTO.isPrivate(), is(false));
    }

    @Test
    public void importExperiment_AddsToAnalyticsTableInDatabase() throws IOException {
        setupDao();
        subject.importExperiment(E_PROT_1, false);

        verify(baselineAnalyticsDao).loadAnalytics(eq(E_PROT_1), baselineProteomicsAnalyticsInputStreamArgumentCaptor.capture());

        BaselineProteomicsAnalyticsInputStream baselineProteomicsAnalyticsInputStream = baselineProteomicsAnalyticsInputStreamArgumentCaptor.getValue();

        BaselineAnalytics baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g5"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G5_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g18"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G18_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g15"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G15_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g16"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G16_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g22"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G22_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_1_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g28"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_1_G28_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g5"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G5_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g6"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G6_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g3"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G3_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g4"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G4_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g10"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G10_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g7"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G7_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g8"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G8_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g14"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G14_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g11"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G11_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g12"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G12_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g17"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G17_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g18"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G18_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g15"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G15_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g16"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G16_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g22"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G22_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g20"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G20_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g19"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G19_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g26"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G26_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g25"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G25_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g24"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G24_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g23"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G23_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g30"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G30_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g29"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G29_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g28"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G28_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g27"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G27_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g2"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G2_ABUNDANCE));

        baselineAnalytics = baselineProteomicsAnalyticsInputStream.readNext();
        assertThat(baselineAnalytics.getGeneId(), is(E_PROT_1_LINE_2_GENE_ID));
        assertThat(baselineAnalytics.getAssayGroupId(), is("g1"));
        assertThat(baselineAnalytics.getExpressionLevel(), is(E_PROT_1_LINE_2_G1_ABUNDANCE));
    }

    @Test
    public void importExperiment_WritesExpDesignFile() throws IOException, SolrServerException {
        setupDao();
        subject.importExperiment(E_PROT_1, false);

        verify(experimentDesignFileWriter).write(experimentDesignArgumentCaptor.capture());

        ExperimentDesign experimentDesign = experimentDesignArgumentCaptor.getValue();

        assertThat(experimentDesign.getFactorHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM_PART));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM, ORGANISM_PART));

        Iterator<SampleCharacteristic> sampleCharacteristicIterator = experimentDesign.getSampleCharacteristics("Adult_Ovary").iterator();

        SampleCharacteristic sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(ORGANISM_PART));
        assertThat(sampleCharacteristic.value(), Matchers.is("ovary"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://www.ebi.ac.uk/efo/EFO_0000973"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(ORGANISM));
        assertThat(sampleCharacteristic.value(), Matchers.is("Homo sapiens"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://purl.obolibrary.org/obo/NCBITaxon_9606"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(DEVELOPMENTAL_STAGE));
        assertThat(sampleCharacteristic.value(), Matchers.is("adult"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://www.ebi.ac.uk/efo/EFO_0001272"));
    }

    @Test
    public void deleteExperiment_DeletesFromSolrAndDatabase() throws IOException, SolrServerException {
        when(experimentDao.findExperiment(E_PROT_1, true)).thenReturn(E_PROT_1_DTO);

        subject.deleteExperiment(E_PROT_1);

        verify(solrServer).deleteByQuery("experiment_accession:" + E_PROT_1);
        verify(experimentDao).deleteExperiment(E_PROT_1);
        verify(baselineAnalyticsDao).deleteAnalytics(E_PROT_1);
    }
}
