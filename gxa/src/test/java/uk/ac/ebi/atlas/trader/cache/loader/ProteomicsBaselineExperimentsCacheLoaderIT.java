package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class ProteomicsBaselineExperimentsCacheLoaderIT {

    private static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    private static final String ORGANISM = "organism";

    @Inject
    private ProteomicsBaselineExperimentFactory proteomicsBaselineExperimentFactory;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private ArrayExpressClient arrayExpressClient;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    private ExperimentsCacheLoader<BaselineExperiment> subject;

    @Before
    public void mockOutDatabaseAndArrayExpress() {
        MockitoAnnotations.initMocks(this);

        Set<String> pubMedIds = Collections.emptySet();
        ExperimentDTO experimentDTO = new ExperimentDTO(E_PROT_1, ExperimentType.PROTEOMICS_BASELINE,
                                                        "Homo sapiens", pubMedIds, "title", new Date(),
                                                        false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(E_PROT_1, true)).thenReturn(experimentDTO);

        when(arrayExpressClient.fetchExperimentName(E_PROT_1)).thenReturn("title");

        subject = new ExperimentsCacheLoader<>(arrayExpressClient,experimentDesignParser,experimentDao,
                proteomicsBaselineExperimentFactory );

    }

    @Test
    public void correctSpeciesReadFromDatabase() throws Exception {
        //given
        BaselineExperiment experiment = subject.load(E_PROT_1);
        //then
        String species = experiment.getSpecies().getName();
        assertThat(species, is("Homo sapiens"));

    }

    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() throws IOException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        assertThat(experiment.getAnalysedRowsAccessions(), containsInAnyOrder(
                "Adult_Adrenalgland", "Adult_Bcells", "Adult_CD4Tcells", "Adult_CD8Tcells",
                "Adult_Colon", "Adult_Esophagus", "Adult_Frontalcortex", "Adult_Gallbladder",
                "Adult_Heart", "Adult_Kidney", "Adult_Liver", "Adult_Lung",
                "Adult_Monocytes", "Adult_NKcells", "Adult_Ovary", "Adult_Pancreas",
                "Adult_Platelets", "Adult_Prostate", "Adult_Rectum", "Adult_Retina",
                "Adult_Spinalcord", "Adult_Testis", "Adult_Urinarybladder", "Fetal_Brain",
                "Fetal_Gut", "Fetal_Heart", "Fetal_Liver", "Fetal_Ovary",
                "Fetal_Placenta", "Fetal_Testis"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() throws IOException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        Set<String> allAssayGroupIds = new HashSet<>();
        for(AssayGroup assayGroup : experiment.getDataColumnDescriptors()){
            allAssayGroupIds.add(assayGroup.getId());
        }

        assertThat(allAssayGroupIds, containsInAnyOrder("g1", "g2", "g3", "g4", "g5", "g6",
                "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21",
                "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"));
    }

    @Test
    public void experimentalFactors() throws IOException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        //ImmutableList<FactorGroup> allFactors = experiment.getExperimentalFactors().getFactorGroupsInOrder();
        //System.out.println("\"" + Joiner.on("\", \"").join(allFactors));

        FactorGroup adultAdrenal = new FactorSet().add(new Factor(DEVELOPMENTAL_STAGE, "adult")).add( new Factor
                (ORGANISM_PART, "adrenal gland"));
        FactorGroup fetusTestis = new FactorSet().add(new Factor(DEVELOPMENTAL_STAGE, "fetus")).add( new Factor
                (ORGANISM_PART, "testis"));
        assertThat(experiment.getExperimentalFactors().getFactorGroup("g1"), is(adultAdrenal));
        assertThat(experiment.getExperimentalFactors().getFactorGroup("g30"), is(fetusTestis));
    }

    @Test
    public void experimentDesign() throws IOException {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        assertThat(experimentDesign.getFactorHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM_PART));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM, ORGANISM_PART));

        Iterator<SampleCharacteristic> sampleCharacteristicIterator = experimentDesign.getSampleCharacteristics("Adult_Ovary").iterator();

        SampleCharacteristic sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM_PART));
        assertThat(sampleCharacteristic.value(), is("ovary"));
        assertThat(sampleCharacteristic.valueOntologyTerms().iterator().next().uri(), is("http://www.ebi.ac.uk/efo/EFO_0000973"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM));
        assertThat(sampleCharacteristic.value(), is("Homo sapiens"));
        assertThat(sampleCharacteristic.valueOntologyTerms().iterator().next().uri(), is("http://purl.obolibrary.org/obo/NCBITaxon_9606"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(DEVELOPMENTAL_STAGE));
        assertThat(sampleCharacteristic.value(), is("adult"));
        assertThat(sampleCharacteristic.valueOntologyTerms().iterator().next().uri(), is("http://www.ebi.ac.uk/efo/EFO_0001272"));

        assertThat(experimentDesign.asTableData(), hasSize(30));
        assertThat(experimentDesign.asTableData(), contains(
                arrayContaining("Adult_Adrenalgland", "adult", "Homo sapiens", "adrenal gland", "adult", "adrenal gland"),
                arrayContaining("Adult_Bcells", "adult", "Homo sapiens", "B cell", "adult", "B cell"),
                arrayContaining("Adult_CD4Tcells", "adult", "Homo sapiens", "CD4-positive T cell", "adult", "CD4-positive T cell"),
                arrayContaining("Adult_CD8Tcells", "adult", "Homo sapiens", "CD8-positive T cell", "adult", "CD8-positive T cell"),
                arrayContaining("Adult_Colon", "adult", "Homo sapiens", "colon", "adult", "colon"),
                arrayContaining("Adult_Esophagus", "adult", "Homo sapiens", "esophagus", "adult", "esophagus"),
                arrayContaining("Adult_Frontalcortex", "adult", "Homo sapiens", "frontal cortex", "adult", "frontal cortex"),
                arrayContaining("Adult_Gallbladder", "adult", "Homo sapiens", "gallbladder", "adult", "gallbladder"),
                arrayContaining("Adult_Heart", "adult", "Homo sapiens", "heart", "adult", "heart"),
                arrayContaining("Adult_Kidney", "adult", "Homo sapiens", "kidney", "adult", "kidney"),
                arrayContaining("Adult_Liver", "adult", "Homo sapiens", "liver", "adult", "liver"),
                arrayContaining("Adult_Lung", "adult", "Homo sapiens", "lung", "adult", "lung"),
                arrayContaining("Adult_Monocytes", "adult", "Homo sapiens", "monocyte", "adult", "monocyte"),
                arrayContaining("Adult_NKcells", "adult", "Homo sapiens", "natural killer cell", "adult", "natural killer cell"),
                arrayContaining("Adult_Ovary", "adult", "Homo sapiens", "ovary", "adult", "ovary"),
                arrayContaining("Adult_Pancreas", "adult", "Homo sapiens", "pancreas", "adult", "pancreas"),
                arrayContaining("Adult_Platelets", "adult", "Homo sapiens", "platelet", "adult", "platelet"),
                arrayContaining("Adult_Prostate", "adult", "Homo sapiens", "prostate", "adult", "prostate"),
                arrayContaining("Adult_Rectum", "adult", "Homo sapiens", "rectum", "adult", "rectum"),
                arrayContaining("Adult_Retina", "adult", "Homo sapiens", "retina", "adult", "retina"),
                arrayContaining("Adult_Spinalcord", "adult", "Homo sapiens", "spinal cord", "adult", "spinal cord"),
                arrayContaining("Adult_Testis", "adult", "Homo sapiens", "testis", "adult", "testis"),
                arrayContaining("Adult_Urinarybladder", "adult", "Homo sapiens", "urinary bladder", "adult", "urinary bladder"),
                arrayContaining("Fetal_Brain", "fetus", "Homo sapiens", "brain", "fetus", "brain"),
                arrayContaining("Fetal_Gut", "fetus", "Homo sapiens", "gut", "fetus", "gut"),
                arrayContaining("Fetal_Heart", "fetus", "Homo sapiens", "heart", "fetus", "heart"),
                arrayContaining("Fetal_Liver", "fetus", "Homo sapiens", "liver", "fetus", "liver"),
                arrayContaining("Fetal_Ovary", "fetus", "Homo sapiens", "ovary", "fetus", "ovary"),
                arrayContaining("Fetal_Placenta", "fetus", "Homo sapiens", "placenta", "fetus", "placenta"),
                arrayContaining("Fetal_Testis", "fetus", "Homo sapiens", "testis", "fetus", "testis")));
    }

}
