package uk.ac.ebi.atlas.experimentimport.experimentdesign;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentDesignFileWriterTest {

    private static final String ASSAY_1 = "ASSAY_1";
    private static final String ASSAY_2 = "ASSAY_2";
    private static final String ASSAY_3 = "ASSAY_3";
    private static final String CHARACTERISTIC_1_HEADER = "CHARACTERISTIC_1";
    private static final String CHARACTERISTIC_2_HEADER = "CHARACTERISTIC_2";
    private static final String CHARACTERISTIC_3_HEADER = "CHARACTERISTIC_3";
    private static final String FACTOR_HEADER = "FACTOR_1";
    private static final String FACTOR2_HEADER = "FACTOR_2";

    private static final String UBERON_1 = "UBERON:0000001";
    private static final String UBERON_2 = "UBERON:0000002";
    private static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String HTTP_OBO_UBERON_1 = HTTP_OBO + UBERON_1;
    private static final String CHAR_1 = "CHAR_1";
    private static final String CHAR_2 = "CHAR_2";
    private static final String CHAR_3 = "CHAR_3";
    private static final String ASSAY_1_FACTOR_VALUE = "ASSAY1_FACTOR";
    private static final String ASSAY_2_FACTOR_VALUE = "ASSAY2_FACTOR";
    private static final String ASSAY_3_FACTOR_VALUE = "ASSAY3_FACTOR";
    private static final String ASSAY_1_FACTOR2_VALUE = "ASSAY1_FACTOR2";
    private static final String ASSAY_2_FACTOR2_VALUE = "ASSAY2_FACTOR2";
    private static final String ASSAY_3_FACTOR2_VALUE = "ASSAY3_FACTOR2";
    private static final String F_1 = "F:1";
    private static final String F_2 = "F:2";

    private static final OntologyTerm[] ABSENT_ONTOLOGY_TERMS = {};

    @Mock
    private TsvWriter tsvWriter;

    private ExperimentDesign experimentDesign;

    private ExperimentDesignFileWriter subject;

    @Before
    public void buildExperimentDesign() {
        subject = new ExperimentDesignFileWriter(tsvWriter, ExperimentType.RNASEQ_MRNA_BASELINE);
        experimentDesign = new ExperimentDesign();

        SampleCharacteristic sampleCharacteristic1 =
                SampleCharacteristic.create("C1", CHAR_1, OntologyTerm.createFromURI(HTTP_OBO_UBERON_1));
        SampleCharacteristic sampleCharacteristic2 =
                SampleCharacteristic.create("C2", CHAR_2, OntologyTerm.createFromURI(UBERON_2));
        SampleCharacteristic sampleCharacteristic3 =
                SampleCharacteristic.create("C3", CHAR_3);

        experimentDesign.putFactor(
                ASSAY_1, FACTOR_HEADER, ASSAY_1_FACTOR_VALUE, OntologyTerm.create(F_1, "", HTTP_OBO));
        experimentDesign.putFactor(
                ASSAY_1, FACTOR2_HEADER, ASSAY_1_FACTOR2_VALUE, ABSENT_ONTOLOGY_TERMS);
        experimentDesign.putSampleCharacteristic(ASSAY_1, CHARACTERISTIC_1_HEADER, sampleCharacteristic1);
        experimentDesign.putSampleCharacteristic(ASSAY_1, CHARACTERISTIC_2_HEADER, sampleCharacteristic2);
        experimentDesign.putSampleCharacteristic(ASSAY_1, CHARACTERISTIC_3_HEADER, sampleCharacteristic3);

        experimentDesign.putFactor(
                ASSAY_2, FACTOR_HEADER, ASSAY_2_FACTOR_VALUE, OntologyTerm.create(F_2, "", ""));
        experimentDesign.putFactor(
                ASSAY_2, FACTOR2_HEADER, ASSAY_2_FACTOR2_VALUE, ABSENT_ONTOLOGY_TERMS);
        experimentDesign.putSampleCharacteristic(ASSAY_2, CHARACTERISTIC_1_HEADER, sampleCharacteristic1);
        experimentDesign.putSampleCharacteristic(ASSAY_2, CHARACTERISTIC_2_HEADER, sampleCharacteristic2);
        experimentDesign.putSampleCharacteristic(ASSAY_2, CHARACTERISTIC_3_HEADER, sampleCharacteristic3);

        experimentDesign.putFactor(
                ASSAY_3, FACTOR_HEADER, ASSAY_3_FACTOR_VALUE, ABSENT_ONTOLOGY_TERMS);
        experimentDesign.putFactor(
                ASSAY_3, FACTOR2_HEADER, ASSAY_3_FACTOR2_VALUE, ABSENT_ONTOLOGY_TERMS);
        experimentDesign.putSampleCharacteristic(ASSAY_3, CHARACTERISTIC_1_HEADER, sampleCharacteristic1);
        experimentDesign.putSampleCharacteristic(ASSAY_3, CHARACTERISTIC_2_HEADER, sampleCharacteristic2);
        experimentDesign.putSampleCharacteristic(ASSAY_3, CHARACTERISTIC_3_HEADER, sampleCharacteristic3);
    }

    @Test
    public void testHeaders() {
        String[] headers = subject.buildColumnHeaders(ExperimentType.RNASEQ_MRNA_BASELINE, experimentDesign);

        assertThat(
                headers,
                is(new String[] {
                        "Run",
                        "Sample Characteristic[CHARACTERISTIC_1]",
                        "Sample Characteristic Ontology Term[CHARACTERISTIC_1]",
                        "Sample Characteristic[CHARACTERISTIC_2]",
                        "Sample Characteristic Ontology Term[CHARACTERISTIC_2]",
                        "Sample Characteristic[CHARACTERISTIC_3]",
                        "Sample Characteristic Ontology Term[CHARACTERISTIC_3]",
                        "Factor Value[FACTOR_1]",
                        "Factor Value Ontology Term[FACTOR_1]",
                        "Factor Value[FACTOR_2]",
                        "Factor Value Ontology Term[FACTOR_2]"
                }));
    }

    @Test
    public void test() {
        List<String[]> rows = subject.asTableOntologyTermsData(experimentDesign);

        assertThat(
                rows.get(0),
                is(new String[] {
                        "ASSAY_1",
                        "CHAR_1",
                        "http://purl.obolibrary.org/obo/UBERON:0000001",
                        "CHAR_2", "UBERON:0000002",
                        "CHAR_3",
                        null,
                        "ASSAY1_FACTOR",
                        "http://purl.obolibrary.org/obo/F:1",
                        "ASSAY1_FACTOR2",
                        null
                }));
        assertThat(
                rows.get(1),
                is(new String[] {
                        "ASSAY_2",
                        "CHAR_1",
                        "http://purl.obolibrary.org/obo/UBERON:0000001",
                        "CHAR_2",
                        "UBERON:0000002",
                        "CHAR_3",
                        null,
                        "ASSAY2_FACTOR",
                        "F:2",
                        "ASSAY2_FACTOR2",
                        null
                }));
        assertThat(
                rows.get(2),
                is(new String[] {
                        "ASSAY_3",
                        "CHAR_1",
                        "http://purl.obolibrary.org/obo/UBERON:0000001",
                        "CHAR_2",
                        "UBERON:0000002",
                        "CHAR_3",
                        null,
                        "ASSAY3_FACTOR",

                        null,
                        "ASSAY3_FACTOR2",
                        null
                }));
    }
}
