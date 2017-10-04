package uk.ac.ebi.atlas.model.experiment;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ExperimentDesignTest {

    private static final String ASSAY1 = "ASSAY1";
    private static final String FACTOR_HEADER = "FACTOR_HEADER";
    private static final String FACTOR_VALUE = "FACTOR_VALUE";
    private static final String FACTOR_ONTOLOGY_TERM_ID1 = "FACTOR_ONTOLOGY_TERM1";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM1 = OntologyTerm.create(FACTOR_ONTOLOGY_TERM_ID1);

    private static final String FACTOR_HEADER2 = "FACTOR_HEADER2";
    private static final String FACTOR_VALUE2 = "FACTOR_VALUE2";
    private static final String FACTOR_ONTOLOGY_TERM_ID2 = "FACTOR_ONTOLOGY_TERM2";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM2 = OntologyTerm.create(FACTOR_ONTOLOGY_TERM_ID2);

    private static final String ASSAY2 = "ASSAY2";

    private static final String FACTOR_HEADER3 = "FACTOR_HEADER3";
    private static final String FACTOR_VALUE3 = "FACTOR_VALUE3";
    private static final String FACTOR_ONTOLOGY_TERM_ID3 = "FACTOR_ONTOLOGY_TERM3";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM3 = OntologyTerm.create(FACTOR_ONTOLOGY_TERM_ID3);

    private static final String SAMPLE_HEADER = "SAMPLE_HEADER1";

    private static final String SAMPLE_VALUE1 = "SAMPLE_VALUE1";
    private static final String SAMPLE_VALUE2 = "SAMPLE_VALUE2";

    private static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String SAMPLE_ONTOLOGY_ID1 = "S:1";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM1 = OntologyTerm.create(SAMPLE_ONTOLOGY_ID1, HTTP_OBO);
    private static final String SAMPLE_ONTOLOGY_ID2 = "S:2";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM2 = OntologyTerm.create(SAMPLE_ONTOLOGY_ID2, "", "");


    public ExperimentDesign subject;

    public static ExperimentDesign mockExperimentDesign(List<AssayGroup> assayGroups){
        ExperimentDesign experimentDesign = new ExperimentDesign();
        for(AssayGroup assayGroup: assayGroups){
            String value1 = RandomStringUtils.random(5);
            String value2 = RandomStringUtils.random(5);
            for(String assay: assayGroup){
                experimentDesign.putFactor(assay, "type1", value1);
                experimentDesign.putFactor(assay, "type2", value2);
            }
        }
        return experimentDesign;
    }

    @Test
    public void loadMultipleFactorSet(){
        subject = new ExperimentDesign();
        subject.putFactor(ASSAY1, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);
        subject.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);

        Factor factor1 = new Factor(FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);
        Factor factor2 = new Factor(FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);
        Factor factor3 = new Factor(FACTOR_HEADER3, FACTOR_VALUE3, FACTOR_ONTOLOGY_TERM3);

        subject.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);
        subject.putFactor(ASSAY2, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);
        subject.putFactor(ASSAY2, FACTOR_HEADER3, FACTOR_VALUE3, FACTOR_ONTOLOGY_TERM3);

        assertThat(subject.getFactors(ASSAY1), containsInAnyOrder(factor2, factor1));
        assertThat(subject.getFactors(ASSAY2), containsInAnyOrder(factor3, factor2, factor1));

        assertThat(subject.getFactorValue(ASSAY1, FACTOR_HEADER), is(FACTOR_VALUE));

        SetMultimap<String, String> allOntologyTermIdsByAssayAccession = subject.getAllOntologyTermIdsByAssayAccession();
        assertThat(allOntologyTermIdsByAssayAccession.keys().elementSet(), containsInAnyOrder(ASSAY1, ASSAY2));
        assertThat(allOntologyTermIdsByAssayAccession.get(ASSAY1), containsInAnyOrder(FACTOR_ONTOLOGY_TERM_ID1, FACTOR_ONTOLOGY_TERM_ID2));
        assertThat(allOntologyTermIdsByAssayAccession.get(ASSAY2), containsInAnyOrder(FACTOR_ONTOLOGY_TERM_ID1, FACTOR_ONTOLOGY_TERM_ID2, FACTOR_ONTOLOGY_TERM_ID3));

    }

    @Test
    public void allOntologyTermIdsByAssayAccession() {
        subject = new ExperimentDesign();

        SampleCharacteristic sampleCharacteristic1 = SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE1, SAMPLE_ONTOLOGY_TERM1);
        SampleCharacteristic sampleCharacteristic2 = SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE2, SAMPLE_ONTOLOGY_TERM2);

        subject.putSampleCharacteristic(ASSAY1, SAMPLE_HEADER, sampleCharacteristic1);
        subject.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);

        subject.putSampleCharacteristic(ASSAY2, SAMPLE_HEADER, sampleCharacteristic2);
        subject.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);

        ImmutableSetMultimap<String, String> allOntologyTermIdsByAssayAccession = subject.getAllOntologyTermIdsByAssayAccession();
        assertThat(allOntologyTermIdsByAssayAccession.keys().elementSet(), containsInAnyOrder(ASSAY1, ASSAY2));
        assertThat(allOntologyTermIdsByAssayAccession.get(ASSAY1), containsInAnyOrder(FACTOR_ONTOLOGY_TERM_ID1, SAMPLE_ONTOLOGY_ID1));
        assertThat(allOntologyTermIdsByAssayAccession.get(ASSAY2), containsInAnyOrder(FACTOR_ONTOLOGY_TERM_ID2, SAMPLE_ONTOLOGY_ID2));

    }

    @Test
    public void loadFactorValueFromAssayAndHeader(){
        subject = new ExperimentDesign();
        subject.putFactor(ASSAY1, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);
        subject.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);

        subject.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGY_TERM1);
        subject.putFactor(ASSAY2, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);

        assertEquals(subject.getFactorValue(ASSAY1, FACTOR_HEADER), FACTOR_VALUE);

    }

}
