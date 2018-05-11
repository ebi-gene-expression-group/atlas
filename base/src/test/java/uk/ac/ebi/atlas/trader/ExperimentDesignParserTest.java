package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.trader.ExperimentDesignParser.SAMPLE_COLUMN_HEADER_PATTERN;
import static uk.ac.ebi.atlas.trader.ExperimentDesignParser.extractMatchingContent;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentDesignParserTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String ASSAY = "Assay";
    private static final String ARRAY = "Array";

    private static final String ASSAY_ACCESSION_1 = "C1";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String RD_INSTAR_LARVA = "3rd instar larva";
    private static final String SPECIES_1 = "Drosophila melanogaster";
    private static final String CYC_C_MUTANT = "cycC mutant";

    private static final String ASSAY_ACCESSION_2 = "WT3";
    private static final String DUMMY = "dummy";
    private static final String SAMPLE_NAME_1 = "DevelopmentalStage";
    private static final String SAMPLE_NAME_2 = "StrainOrLine";
    private static final String GENOTYPE = "GENOTYPE";
    private static final String OREGON_R = "Oregon R";

    private static final String[] HEADER_LINE = new String[] {
            ASSAY,
            ARRAY,
            "Sample Characteristic[DevelopmentalStage]",
            "Sample Characteristic[Genotype]",
            "Sample Characteristic[Organism]",
            "Sample Characteristic[StrainOrLine]",
            "Factor Value[GENOTYPE]" };

    private static final String[] FIRST_LINE = new String[] {
            ASSAY_ACCESSION_1,
            A_AFFY_35,
            RD_INSTAR_LARVA,
            "w1118; +; cycCY5",
            SPECIES_1,
            "",
            CYC_C_MUTANT };

    private static final String[] LAST_LINE = new String[] {
            ASSAY_ACCESSION_2,
            A_AFFY_35,
            RD_INSTAR_LARVA,
            "wild_type",
            SPECIES_1,
            OREGON_R,
            "wild_type" };

    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, LAST_LINE);

    private static MockDataFileHub dataFileHub;

    private ExperimentDesignParser subject;

    @BeforeClass
    public static void setUpClass() {
        dataFileHub = MockDataFileHub.create();
    }

    @Before
    public void setUp() {
        dataFileHub.addExperimentDesignFile(EXPERIMENT_ACCESSION, DATA);
        subject = new ExperimentDesignParser(dataFileHub);
    }

    @Test
    public void testParseHeaders() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorHeaders(), IsIterableContainingInOrder.contains(GENOTYPE));
        assertThat(experimentDesign.getSampleHeaders(), IsIterableContainingInOrder.contains(SAMPLE_NAME_1, "Genotype", "Organism", SAMPLE_NAME_2));
    }

    @Test
    public void testParseFactors() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, GENOTYPE), is(CYC_C_MUTANT));
        assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getFactorValue(DUMMY, GENOTYPE), is(nullValue()));
    }

    @Test
    public void testParseSamples() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, SAMPLE_NAME_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, SAMPLE_NAME_1), is(RD_INSTAR_LARVA));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, SAMPLE_NAME_2), is(""));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, SAMPLE_NAME_2), is(OREGON_R));
        assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, DUMMY), is(nullValue()));
        assertThat(experimentDesign.getSampleCharacteristicValue(DUMMY, SAMPLE_NAME_1), is(nullValue()));
    }

    @Test
    public void testAssays() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_1), is(A_AFFY_35));
        assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_2), is(A_AFFY_35));
    }

    @Test
    public void testAssayHeaders() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAssayHeaders(), IsIterableContainingInOrder.contains(ASSAY, ARRAY));
    }

    @Test
    public void testGetAllRunOrAssay() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        assertThat(experimentDesign.getAllRunOrAssay(), IsIterableContainingInOrder.contains(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
    }

    @Test
    public void testGetSpeciesForAssays() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        String species = experimentDesign.getSpeciesForAssays(Sets.newHashSet(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
        assertThat(species, is(SPECIES_1));
    }

    @Test
    public void testExtractGroup1Match() {
        String matchingString = extractMatchingContent("Assay Bello", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent("Assay Bello[assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent("Sample Characteristic[bello assai] ", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample Characteristic[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample  Characteristic[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));
    }
}