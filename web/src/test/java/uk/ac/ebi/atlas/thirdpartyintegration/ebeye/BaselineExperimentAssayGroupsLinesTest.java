package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentAssayGroupsLinesTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String ASSAY1 = "ASSAY1";
    private static final String ASSAY2 = "ASSAY2";
    private static final String ASSAY3 = "ASSAY3";
    private static final String SAMPLE_HEADER = "SAMPLE_HEADER1";
    private static final String FACTOR_HEADER = "FACTOR_HEADER1";

    private static final String FACTOR_VALUE1 = "FACTOR_VALUE1";
    private static final String FACTOR_VALUE2 = "FACTOR_VALUE2";
    private static final String FACTOR_VALUE3 = "FACTOR_VALUE3";

    private static final String SAMPLE_VALUE1 = "SAMPLE_VALUE1";
    private static final String SAMPLE_VALUE2 = "SAMPLE_VALUE2";
    private static final String SAMPLE_VALUE3 = "SAMPLE_VALUE3";
    private static final String CHARACTERISTIC = "characteristic";
    private static final String FACTOR = "factor";

    private static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String FACTOR_ONTOLOGY_ID1 = "F:1";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM1 = new OntologyTerm(FACTOR_ONTOLOGY_ID1, HTTP_OBO);
    private static final String FACTOR_ONTOLOGY_ID2 = "F:2";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM2 = new OntologyTerm(FACTOR_ONTOLOGY_ID2, null);

    private static final String SAMPLE_ONTOLOGY_ID1 = "S:1";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM1 = new OntologyTerm(SAMPLE_ONTOLOGY_ID1, HTTP_OBO);
    private static final String SAMPLE_ONTOLOGY_ID2 = "S:2";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM2 = new OntologyTerm(SAMPLE_ONTOLOGY_ID2, null);

    private static final String G1 = "g1";
    private static final AssayGroup ASSAY_GROUP1 = new AssayGroup(G1, ASSAY1);
    private static final String G2 = "g2";
    private static final AssayGroup ASSAY_GROUP2 = new AssayGroup(G2, ASSAY2);
    private static final String G3 = "g3";
    private static final AssayGroup ASSAY_GROUP3 = new AssayGroup(G3, ASSAY3);

    @Mock
    private BaselineExperiment baselineExperiment;

    @Test
    public void lines() {

        ExperimentDesign experimentDesign = new ExperimentDesign();

        SampleCharacteristic sampleCharacteristic1 = SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE1, SAMPLE_ONTOLOGY_TERM1);
        SampleCharacteristic sampleCharacteristic2 = SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE2, SAMPLE_ONTOLOGY_TERM2);


        experimentDesign.putSampleCharacteristic(ASSAY1, SAMPLE_HEADER, sampleCharacteristic1);
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE1, FACTOR_ONTOLOGY_TERM1);

        experimentDesign.putSampleCharacteristic(ASSAY2, SAMPLE_HEADER, sampleCharacteristic2);
        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);

        experimentDesign.putSampleCharacteristic(ASSAY3, SAMPLE_HEADER, SAMPLE_VALUE3);
        experimentDesign.putFactor(ASSAY3, FACTOR_HEADER, FACTOR_VALUE3);


        when(baselineExperiment.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(baselineExperiment.getAssayGroups()).thenReturn(new AssayGroups(ImmutableSet.of(ASSAY_GROUP1, ASSAY_GROUP2, ASSAY_GROUP3)));
        when(baselineExperiment.getExperimentDesign()).thenReturn(experimentDesign);

        BaselineExperimentAssayGroupsLines subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        Iterator<String[]> lines = subject.iterator();

        print(subject);

        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G2, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE2, SAMPLE_ONTOLOGY_ID2}));
        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G2, FACTOR, FACTOR_HEADER, FACTOR_VALUE2, FACTOR_ONTOLOGY_ID2}));

        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G1, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE1, HTTP_OBO + SAMPLE_ONTOLOGY_ID1}));
        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G1, FACTOR, FACTOR_HEADER, FACTOR_VALUE1, HTTP_OBO + FACTOR_ONTOLOGY_ID1}));

        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G3, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE3, ""}));
        assertThat(lines.next(), is(new String[]{EXPERIMENT_ACCESSION, G3, FACTOR, FACTOR_HEADER, FACTOR_VALUE3, ""}));
    }

    private void print(Iterable<String[]> rows) {
        for (String[] row: rows) {
            print(row);
        }
    }

    private void print(String[] row) {
        System.out.println("\"" + Joiner.on("\",\"").useForNull("").join(row) + "\"");
    }


}
