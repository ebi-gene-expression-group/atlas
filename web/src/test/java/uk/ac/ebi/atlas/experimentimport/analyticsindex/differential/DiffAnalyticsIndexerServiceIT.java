package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import au.com.bytecode.opencsv.CSVReader;
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
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.atlas.commons.efo.EFOTreeNodesTrader;
import uk.ac.ebi.atlas.experimentimport.EFOParentsLookupService;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsIndexerServiceIT {

    @Inject
    DiffAnalyticsDocumentStreamFactory streamFactory;


    EFOParentsLookupService efoParentsLookupService;

    @Mock
    EFOTreeNodesTrader efoTreeNodesTrader;

    @Mock
    private RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;

    @Mock
    AnalyticsIndexDao analyticsIndexDaoMock;

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    private DifferentialConditionsBuilder conditionsBuilder;

    private DiffAnalyticsIndexerService subject;

    private ImmutableList<AnalyticsDocument> documents;

    @Before
    public void before() {
        String tsv38400 =
                "Gene ID\tGene Name\tg1_g4.p-value\tg1_g4.log2foldchange\tg1_g3.p-value\tg1_g3.log2foldchange\tg1_g2.p-value\tg1_g2.log2foldchange\n" +
                "AT3G18710\tPUB29\t1\t-0.0979807106778182\t1\t0.0452223119926126\t1\t0.311218971678632\n" +
                "AT4G25880\tAPUM6\t1\t0.0836848323581764\t1\t-0.0621211768519467\t1\t-0.000894189281532053\n" +
                "AT1G71695\tPER12\t0.709632908784351\t-1.09780207038468\t1\t-0.185084447053408\t1\t-0.250199686097725\n" +
                "AT5G41480\t\t1\t0.207244599170542\t1\t0.0726273346499253\t1\t0.0666913090347075";

        MockitoAnnotations.initMocks(this);
        when(analyticsIndexDaoMock.addDocuments(Matchers.<Iterable<AnalyticsDocument>>any(), anyInt())).thenAnswer(storeDocuments());
        Reader reader38400 = new StringReader(tsv38400);
        CSVReader csvReader38400 = new CSVReader(reader38400, '\t');
        RnaSeqDifferentialAnalyticsInputStream inputStream38400 = new RnaSeqDifferentialAnalyticsInputStream(csvReader38400, "38400 mock");
        when(rnaSeqDifferentialAnalyticsInputStreamFactory.create("E-GEOD-38400")).thenReturn(inputStream38400);
        DiffAnalyticsDocumentStreamIndexer streamIndexer = new DiffAnalyticsDocumentStreamIndexer(rnaSeqDifferentialAnalyticsInputStreamFactory, streamFactory, analyticsIndexDaoMock);

        when(efoTreeNodesTrader.getTreeNodes()).thenReturn(Collections.<String, EFONode>emptyMap());
        efoParentsLookupService = new EFOParentsLookupService(efoTreeNodesTrader);
        subject = new DiffAnalyticsIndexerService(efoParentsLookupService, conditionsBuilder, streamIndexer);
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

        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment("E-GEOD-38400");
        subject.index(experiment, 1024);
        assertThat(documents, hasSize(12));

        AnalyticsDocument document = documents.get(0);
        assertThat(document.getBioentityIdentifier(), is("AT3G18710"));
        assertThat(document.getSpecies(), is("arabidopsis thaliana"));
        assertThat(document.getExperimentAccession(), is("E-GEOD-38400"));
        assertThat(document.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL));
        assertThat(document.getIdentifierSearch(), is("AT3G18710 Zinc finger, RING/FYVE/PHD-type U box domain 257748_at protein ubiquitination plasmodesma ubiquitin-protein transferase activity protein_coding transmembrane receptor protein serine/threonine kinase binding PUB29 Armadillo-like helical binding plant U-box 29 [Source:TAIR;Acc:AT3G18710] Q9LSA6 Armadillo-type fold response to chitin ATPUB29 ubiquitin ligase complex ligase activity"));
        assertThat(document.getConditionsSearch(), is("NCBITaxon#NCBITaxon_3702 Arabidopsis thaliana seedling Col-0 2 to 3 weeks wild type genotype idn2-1"));
        assertThat(document.getContrastId(), is("g1_g4"));
        assertThat(document.getFactors(), contains("genotype"));
        assertThat(document.getNumReplicates(), is(3));
        assertThat(document.getFoldChange(), is(-0.0979807106778182));
        assertThat(document.getRegulation(), is(Regulation.DOWN));

        AnalyticsDocument document2 = documents.get(1);
        assertThat(document2.getBioentityIdentifier(), is("AT3G18710"));
        assertThat(document2.getContrastId(), is("g1_g3"));
        assertThat(document.getNumReplicates(), is(3));
        assertThat(document2.getConditionsSearch(), is("NCBITaxon#NCBITaxon_3702 Arabidopsis thaliana seedling Col-0 2 to 3 weeks wild type genotype swi3b +/-"));
        assertThat(document2.getFoldChange(), is(0.0452223119926126));
        assertThat(document2.getRegulation(), is(Regulation.UP));

        AnalyticsDocument document3 = documents.get(2);
        assertThat(document3.getBioentityIdentifier(), is("AT3G18710"));
        assertThat(document3.getContrastId(), is("g1_g2"));
        assertThat(document3.getFoldChange(), is(0.311218971678632));

        AnalyticsDocument document4 = documents.get(3);
        assertThat(document4.getBioentityIdentifier(), is("AT4G25880"));
        assertThat(document4.getIdentifierSearch(), is("AT4G25880 chloroplast plastid RNA binding Pumilio RNA-binding repeat pumilio 6 [Source:TAIR;Acc:AT4G25880] protein_coding regulation of translation mRNA binding Q9C5E7 nucleotide biosynthetic process Armadillo-like helical cytoplasm binding APUM6 Armadillo-type fold 254045_at PUM6"));
        assertThat(document4.getContrastId(), is("g1_g4"));
        assertThat(document4.getFoldChange(), is(0.0836848323581764));

    }

}