package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CondensedSdrfParserTest {

    private static final String ORGANISM_PART = "organism part";
    private static final String THYROID = "thyroid";
    private static final String THYROID_ONTOLOGY_URI = "http://purl.obolibrary.org/obo/UBERON_0002046";
    private static final String TESTIS = "testis";
    private static final String TESTIS_ONTOLOGY_URI = "http://purl.obolibrary.org/obo/UBERON_0000473";
    private static final String OVARY = "ovary";
    private static final String OVARY_ONTOLOGY_URI = "http://www.ebi.ac.uk/efo/EFO_0000973";
    private static final String LEUKOCYTE = "leukocyte";
    private static final String LEUKOCYTE_ONTOLOGY_URI = "http://purl.obolibrary.org/obo/CL_0000738";

    private static final String SEX = "sex";
    // private static final String FEMALE = "female";
    // private static final String FEMALE_ONTOLOGY_URI = "http://purl.obolibrary.org/obo/PATO_0000383";
    private static final String MALE = "male";
    private static final String MALE_ONTOLOGY_URI = "http://purl.obolibrary.org/obo/PATO_0000384";

    private static final String ETHNIC_GROUP = "ethnic group";
    private static final String CAUCASIAN = "Caucasian";
    private static final String CAUCASIAN_EFO_URI = "http://www.ebi.ac.uk/efo/EFO_0003156";
    private static final String AFRICAN_AMERICAN = "African American";
    private static final String AFRICAN_AMERICAN_EFO_URI = "http://www.ebi.ac.uk/efo/EFO_0003150";

    private static final String ORGANISM = "organism";
    private static final String HOMO_SAPIENS = "Homo sapiens";
    private static final String HOMO_SAPIENS_EFO_URI = "http://purl.obolibrary.org/obo/NCBITaxon_9606";

    private static final String AGE = "age";

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String[] ASSAYS = {"ERR030872", "ERR030873", "ERR030874", "ERR030875"};

    private static final String[][] E_MTAB_513_CONDENSED_SDRF_ARRAY = {
        {E_MTAB_513, "", ASSAYS[0], "characteristic", AGE, "60 year"},
        {E_MTAB_513, "", ASSAYS[0], "characteristic", ETHNIC_GROUP, CAUCASIAN, CAUCASIAN_EFO_URI},
        {E_MTAB_513, "", ASSAYS[0], "characteristic", ORGANISM, HOMO_SAPIENS, HOMO_SAPIENS_EFO_URI},
        {E_MTAB_513, "", ASSAYS[0], "characteristic", ORGANISM_PART, THYROID, THYROID_ONTOLOGY_URI},
        // {E_MTAB_513, "", ASSAYS[0], "characteristic", SEX, FEMALE, FEMALE_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[0], "factor", ORGANISM_PART, THYROID, THYROID_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[1], "characteristic", AGE, "19 year"},
        {E_MTAB_513, "", ASSAYS[1], "characteristic", ETHNIC_GROUP, CAUCASIAN, CAUCASIAN_EFO_URI},
        {E_MTAB_513, "", ASSAYS[1], "characteristic", ORGANISM, HOMO_SAPIENS, HOMO_SAPIENS_EFO_URI},
        {E_MTAB_513, "", ASSAYS[1], "characteristic", ORGANISM_PART, TESTIS, TESTIS_ONTOLOGY_URI},
        // {E_MTAB_513, "", ASSAYS[1], "characteristic", SEX, MALE, MALE_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[1], "factor", ORGANISM_PART, TESTIS, TESTIS_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[2], "characteristic", AGE, "47 year"},
        {E_MTAB_513, "", ASSAYS[2], "characteristic", ETHNIC_GROUP, AFRICAN_AMERICAN, AFRICAN_AMERICAN_EFO_URI},
        {E_MTAB_513, "", ASSAYS[2], "characteristic", ORGANISM, HOMO_SAPIENS, HOMO_SAPIENS_EFO_URI},
        {E_MTAB_513, "", ASSAYS[2], "characteristic", ORGANISM_PART, OVARY, OVARY_ONTOLOGY_URI},
        // {E_MTAB_513, "", ASSAYS[2], "characteristic", SEX, FEMALE, FEMALE_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[2], "factor", ORGANISM_PART, OVARY, OVARY_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[3], "characteristic", AGE, "58 year"},
        {E_MTAB_513, "", ASSAYS[3], "characteristic", ETHNIC_GROUP, CAUCASIAN, CAUCASIAN_EFO_URI},
        {E_MTAB_513, "", ASSAYS[3], "characteristic", ORGANISM, HOMO_SAPIENS, HOMO_SAPIENS_EFO_URI},
        {E_MTAB_513, "", ASSAYS[3], "characteristic", ORGANISM_PART, LEUKOCYTE, LEUKOCYTE_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[3], "characteristic", SEX, MALE, MALE_ONTOLOGY_URI},
        {E_MTAB_513, "", ASSAYS[3], "factor", ORGANISM_PART, LEUKOCYTE, LEUKOCYTE_ONTOLOGY_URI}
    };

    private static final String E_MEXP_1810 = "E-MEXP-1810";
    private static final String H_RIL_14_NON_DAUER_1 = "H_RIL-14 non-dauer 1";
    private static final String COMPOUND_VALUE = "none";
    private static final String DOSE_VALUE = "0 microliter";

    private static final String[][] E_MEXP_1810_CONDENSED_SDRF_ARRAY = {
            {
                E_MEXP_1810,
                "A-AFFY-60",
                H_RIL_14_NON_DAUER_1, "factor", "strain", "RIL-14"},
            {
                E_MEXP_1810,
                "A-AFFY-60", H_RIL_14_NON_DAUER_1, "factor", "compound",
                COMPOUND_VALUE, "http://www.ebi.ac.uk/efo/EFO_0001461"
            },
            {
                E_MEXP_1810,
                "A-AFFY-60",
                H_RIL_14_NON_DAUER_1,
                "factor", "dose",
                DOSE_VALUE
            }
    };

    private static final String[][] MULTIPLE_ARRAY_DESIGNS_CONDENSED_SDRF_ARRAY = {
            {E_MEXP_1810, "A-AFFY-60", H_RIL_14_NON_DAUER_1, "factor", "strain", "RIL-14"},
            {E_MEXP_1810, "A-AFFY-75", H_RIL_14_NON_DAUER_1, "factor", "strain", "RIL-14"},
    };

    private static final String[][] NO_FACTOR_OR_CHARACTERISTIC_CONDENSED_SDRF_ARRAY = {
            {E_MEXP_1810, "A-AFFY-60", H_RIL_14_NON_DAUER_1, "foobar", "strain", "RIL-14"}
    };

    private static MockDataFileHub dataFileHub;

    private CondensedSdrfParser subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        dataFileHub = MockDataFileHub.create();
    }

    @Before
    public void setUp() {
        subject = new CondensedSdrfParser(dataFileHub);
    }

    @Test
    public void parse() {
        dataFileHub.addCondensedSdrfFile(E_MTAB_513, Arrays.asList(E_MTAB_513_CONDENSED_SDRF_ARRAY));

        CondensedSdrfParserOutput output = subject.parse(E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE);
        assertThat(
                output.getExperimentAccession(),
                is(E_MTAB_513));
        assertThat(
                output.getExperimentType(),
                is(ExperimentType.RNASEQ_MRNA_BASELINE));

        ExperimentDesign experimentDesign = output.getExperimentDesign();
        assertThat(
                experimentDesign.getFactorHeaders(),
                hasItems(ORGANISM_PART));
        assertThat(
                experimentDesign.getAllRunOrAssay(),
                containsInAnyOrder(ASSAYS));
        assertThat(
                experimentDesign.getFactorHeaders(),
                containsInAnyOrder(ORGANISM_PART));
        assertThat(
                experimentDesign.getFactors(ASSAYS[0]).size(),
                is(1));
        assertThat(
                experimentDesign.getSampleHeaders(),
                containsInAnyOrder(AGE, ORGANISM_PART, ETHNIC_GROUP, SEX, ORGANISM));
        assertThat(
                experimentDesign.getSampleCharacteristic(ASSAYS[2], ETHNIC_GROUP).value(),
                is(AFRICAN_AMERICAN));
        assertThat(
                experimentDesign.getSampleCharacteristics(ASSAYS[0]).size(),
                is(4));
        assertThat(
                experimentDesign.getSampleCharacteristics(ASSAYS[3]).size(),
                is(5));
        assertThat(
                output.getSpecies(),
                is(HOMO_SAPIENS));
    }

    @Test
    public void parseRunWithoutFactorIsDiscarded() {
        dataFileHub.addCondensedSdrfFile(
                E_MTAB_513,
                ImmutableList.copyOf(
                        Arrays.copyOfRange(
                                E_MTAB_513_CONDENSED_SDRF_ARRAY, 0, E_MTAB_513_CONDENSED_SDRF_ARRAY.length - 1)));

        CondensedSdrfParserOutput output = subject.parse(E_MTAB_513, ExperimentType.RNASEQ_MRNA_BASELINE);

        ExperimentDesign experimentDesign = output.getExperimentDesign();
        assertThat(
                experimentDesign.getAllRunOrAssay(),
                containsInAnyOrder(ASSAYS[0], ASSAYS[1], ASSAYS[2]));
        assertThat(
                experimentDesign.getSampleHeaders(),
                containsInAnyOrder(AGE, ORGANISM_PART, ETHNIC_GROUP, ORGANISM));
    }

    @Test
    public void parseAssayWithCompoundAndDose() {
        dataFileHub.addCondensedSdrfFile(E_MEXP_1810, Arrays.asList(E_MEXP_1810_CONDENSED_SDRF_ARRAY));

        CondensedSdrfParserOutput output = subject.parse(E_MEXP_1810, ExperimentType.RNASEQ_MRNA_BASELINE);

        ExperimentDesign experimentDesign = output.getExperimentDesign();
        assertThat(
                experimentDesign.getFactor(H_RIL_14_NON_DAUER_1, "compound").getValue(),
                is(COMPOUND_VALUE + " " + DOSE_VALUE));
    }

    @Test
    public void parseAssayWithCompoundAndNoDose() {
        dataFileHub.addCondensedSdrfFile(
                E_MEXP_1810,
                ImmutableList.of(E_MEXP_1810_CONDENSED_SDRF_ARRAY[0], E_MEXP_1810_CONDENSED_SDRF_ARRAY[1]));

        CondensedSdrfParserOutput output = subject.parse(E_MEXP_1810, ExperimentType.RNASEQ_MRNA_BASELINE);

        ExperimentDesign experimentDesign = output.getExperimentDesign();
        assertThat(experimentDesign.getFactor(H_RIL_14_NON_DAUER_1, "compound").getValue(), is(COMPOUND_VALUE));
    }

    @Test
    public void parseAssayWithDoseAndNoCompoundOrIrradiate() {
        thrown.expect(CondensedSdrfParser.CondensedSdrfParserException.class);

        dataFileHub.addCondensedSdrfFile(
                E_MEXP_1810,
                ImmutableList.of(E_MEXP_1810_CONDENSED_SDRF_ARRAY[0], E_MEXP_1810_CONDENSED_SDRF_ARRAY[2]));

        subject.parse(E_MEXP_1810, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void parseMultipleArrayDesigns() {
        thrown.expect(CondensedSdrfParser.CondensedSdrfParserException.class);

        dataFileHub.addCondensedSdrfFile(
                E_MEXP_1810,
                ImmutableList.copyOf(MULTIPLE_ARRAY_DESIGNS_CONDENSED_SDRF_ARRAY));

        subject.parse(E_MEXP_1810, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
    }

    @Test
    public void parseOtherThanFactorOrCharacteristic() {
        thrown.expect(CondensedSdrfParser.CondensedSdrfParserException.class);

        dataFileHub.addCondensedSdrfFile(
                E_MEXP_1810,
                ImmutableList.copyOf(NO_FACTOR_OR_CHARACTERISTIC_CONDENSED_SDRF_ARRAY));

        subject.parse(E_MEXP_1810, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
    }
}
