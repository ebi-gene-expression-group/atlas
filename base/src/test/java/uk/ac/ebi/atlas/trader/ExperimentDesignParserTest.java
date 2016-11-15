package uk.ac.ebi.atlas.trader;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.MatcherAssertionErrors;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.util.List;

import static uk.ac.ebi.atlas.trader.ExperimentDesignParser.SAMPLE_COLUMN_HEADER_PATTERN;
import static uk.ac.ebi.atlas.trader.ExperimentDesignParser.extractMatchingContent;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentDesignParserTest {

    private static final String EXPERIMENT_ACCESSION = "ACCESSION";
    private static final String ASSAY_ACCESSION_1 = "C1";
    private static final String RD_INSTAR_LARVA = "3rd instar larva";
    private static final String ASSAY_ACCESSION_2 = "WT3";
    private static final String DUMMY = "dummy";
    private static final String SAMPLE_NAME_1 = "DevelopmentalStage";
    private static final String SAMPLE_NAME_2 = "StrainOrLine";
    private static final String GENOTYPE = "GENOTYPE";
    private static final String OREGON_R = "Oregon R";
    private static final String CYC_C_MUTANT = "cycC mutant";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String ASSAY = "Assay";
    private static final String ARRAY = "Array";
    private static final String SPECIES_1 = "Drosophila melanogaster";

    private static final String[] HEADER_LINE = new String[]{ASSAY, ARRAY, "Sample Characteristic[DevelopmentalStage]", "Sample Characteristic[Genotype]", "Sample Characteristic[Organism]", "Sample Characteristic[StrainOrLine]", "Factor Value[GENOTYPE]"};
    private static final String[] FIRST_LINE = new String[]{ASSAY_ACCESSION_1, A_AFFY_35, RD_INSTAR_LARVA, "w1118; +; cycCY5", SPECIES_1, "", CYC_C_MUTANT};
    private static final String[] LAST_LINE = new String[]{ASSAY_ACCESSION_2, A_AFFY_35, RD_INSTAR_LARVA, "wild_type", SPECIES_1, OREGON_R, "wild_type"};
    private static final List<String[]> DATA = Lists.newArrayList(HEADER_LINE, FIRST_LINE, LAST_LINE);

    @Mock
    private FileTsvReaderBuilder fileTsvReaderBuilderMock;

    @Mock
    private TsvReader tsvReaderMock;

    DataFileHub dataFileHub = MockDataFileHub.get();

    private ExperimentDesignParser subject;

    @Before
    public void setUp() throws Exception {
        Mockito.when(fileTsvReaderBuilderMock.forTsvFilePathTemplate(Matchers.anyString())).thenReturn(fileTsvReaderBuilderMock);
        Mockito.when(fileTsvReaderBuilderMock.withExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(fileTsvReaderBuilderMock);
        Mockito.when(fileTsvReaderBuilderMock.build()).thenReturn(tsvReaderMock);

        Mockito.when(tsvReaderMock.readAll()).thenReturn(DATA);

        subject = new ExperimentDesignParser(dataFileHub);
    }

    @Test
    public void testParseHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getFactorHeaders(), IsIterableContainingInOrder.contains(GENOTYPE));
        MatcherAssert.assertThat(experimentDesign.getSampleHeaders(), IsIterableContainingInOrder.contains(SAMPLE_NAME_1, "Genotype", "Organism", SAMPLE_NAME_2));
    }

    @Test
    public void testParseFactors() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, GENOTYPE), CoreMatchers.is(CYC_C_MUTANT));
        MatcherAssert.assertThat(experimentDesign.getFactorValue(ASSAY_ACCESSION_1, DUMMY), CoreMatchers.is(Matchers
                .isNull()));
        MatcherAssert.assertThat(experimentDesign.getFactorValue(DUMMY, GENOTYPE), CoreMatchers.is(Matchers.isNull()));
    }

    @Test
    public void testParseSamples() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, SAMPLE_NAME_1), CoreMatchers.is(RD_INSTAR_LARVA));
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, SAMPLE_NAME_1), CoreMatchers.is(RD_INSTAR_LARVA));
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, SAMPLE_NAME_2), CoreMatchers.is(""));
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_2, SAMPLE_NAME_2), CoreMatchers.is(OREGON_R));
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(ASSAY_ACCESSION_1, DUMMY),
                CoreMatchers.is(Matchers.isNull()));
        MatcherAssert.assertThat(experimentDesign.getSampleCharacteristicValue(DUMMY, SAMPLE_NAME_1), CoreMatchers.is
                (Matchers.isNull()));
    }

    @Test
    public void testAssays() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_1), CoreMatchers.is(A_AFFY_35));
        MatcherAssert.assertThat(experimentDesign.getArrayDesign(ASSAY_ACCESSION_2), CoreMatchers.is(A_AFFY_35));
    }

    @Test
    public void testAssayHeaders() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getAssayHeaders(), IsIterableContainingInOrder.contains(ASSAY, ARRAY));
    }

    @Test
    public void testGetAllRunOrAssay() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        MatcherAssert.assertThat(experimentDesign.getAllRunOrAssay(), IsIterableContainingInOrder.contains(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
    }

    @Test
    public void testGetSpeciesForAssays() {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);
        String species = experimentDesign.getSpeciesForAssays(Sets.newHashSet(ASSAY_ACCESSION_1, ASSAY_ACCESSION_2));
        MatcherAssert.assertThat(species, CoreMatchers.is(SPECIES_1));
    }

    @Test
    public void testExtractGroup1Match() throws Exception {
        String matchingString = extractMatchingContent("Assay Bello", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, CoreMatchers.is(Matchers.isNull()));

        matchingString = extractMatchingContent("Assay Bello[assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, CoreMatchers.is(Matchers.isNull()));

        matchingString = extractMatchingContent("Sample Characteristic[bello assai] ", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, CoreMatchers.is("bello assai"));

        matchingString = extractMatchingContent("Sample Characteristic[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, CoreMatchers.is("bello assai"));

        matchingString = extractMatchingContent("Sample  Characteristic[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        MatcherAssertionErrors.assertThat(matchingString, CoreMatchers.is(Matchers.isNull()));
    }
}