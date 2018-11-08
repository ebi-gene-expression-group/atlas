package uk.ac.ebi.atlas.ebeyedump;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.internal.Diff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.naming.OperationNotSupportedException;
import java.util.Date;
import java.util.Iterator;

import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentContrastLinesTest {
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String CONTRAST_ID = "CONTRAST_ID";
    private static final String ASSAY1 = "ASSAY1";
    private static final String ASSAY2 = "ASSAY2";
    private static final String ASSAY3 = "ASSAY3";
    private static final String ASSAY4 = "ASSAY4";
    private static final String SAMPLE_HEADER = "SAMPLE_HEADER1";
    private static final String FACTOR_HEADER = "FACTOR_HEADER1";

    private static final String FACTOR_VALUE1 = "FACTOR_VALUE1";
    private static final String FACTOR_VALUE2 = "FACTOR_VALUE2";

    private static final String SAMPLE_VALUE1 = "SAMPLE_VALUE1";
    private static final String SAMPLE_VALUE2 = "SAMPLE_VALUE2";
    private static final String SAMPLE_VALUE3 = "SAMPLE_VALUE3";
    private static final String SAMPLE_VALUE4 = "SAMPLE_VALUE4";
    private static final String REFERENCE = "reference";
    private static final String CHARACTERISTIC = "characteristic";
    private static final String FACTOR = "factor";
    private static final String TEST = "test";

    private static final String HTTP_OBO = "http://purl.obolibrary.org/obo/";
    private static final String FACTOR_ONTOLOGY_ID1 = "F:1";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM1 = OntologyTerm.create(FACTOR_ONTOLOGY_ID1, "", HTTP_OBO);
    private static final String FACTOR_ONTOLOGY_ID2 = "F:2";
    private static final OntologyTerm FACTOR_ONTOLOGY_TERM2 = OntologyTerm.create(FACTOR_ONTOLOGY_ID2, "", "");

    private static final String SAMPLE_ONTOLOGY_ID1 = "S:1";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM1 = OntologyTerm.create(SAMPLE_ONTOLOGY_ID1, "", HTTP_OBO);
    private static final String SAMPLE_ONTOLOGY_ID2 = "S:2";
    private static final OntologyTerm SAMPLE_ONTOLOGY_TERM2 = OntologyTerm.create(SAMPLE_ONTOLOGY_ID2, "", "");

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    private DifferentialExperimentContrastLines subject;

    @Test
    public void contrastDetailLines() {
        AssayGroup referenceAssayGroup =
                new AssayGroup(
                        "g1",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY1), new BiologicalReplicate(ASSAY2)));
        AssayGroup testAssayGroup =
                new AssayGroup(
                        "g2",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY3), new BiologicalReplicate(ASSAY4)));
        Contrast contrast1 =
                new Contrast(
                        CONTRAST_ID, "array design accession", referenceAssayGroup, testAssayGroup, "display name");

        ExperimentDesign experimentDesign = new ExperimentDesign();

        SampleCharacteristic sampleCharacteristic1 =
                SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE1, SAMPLE_ONTOLOGY_TERM1);
        SampleCharacteristic sampleCharacteristic2 =
                SampleCharacteristic.create(SAMPLE_HEADER, SAMPLE_VALUE2, SAMPLE_ONTOLOGY_TERM2);


        experimentDesign.putSampleCharacteristic(ASSAY1, SAMPLE_HEADER, sampleCharacteristic1);
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE1, FACTOR_ONTOLOGY_TERM1);
        experimentDesign.putSampleCharacteristic(ASSAY2, SAMPLE_HEADER, sampleCharacteristic2);
        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE1, FACTOR_ONTOLOGY_TERM1);

        experimentDesign.putSampleCharacteristic(ASSAY3, SAMPLE_HEADER, SAMPLE_VALUE3);
        experimentDesign.putFactor(ASSAY3, FACTOR_HEADER, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);
        experimentDesign.putSampleCharacteristic(ASSAY4, SAMPLE_HEADER, SAMPLE_VALUE4);
        experimentDesign.putFactor(ASSAY4, FACTOR_HEADER, FACTOR_VALUE2, FACTOR_ONTOLOGY_TERM2);

        DifferentialExperiment experiment =
                new DifferentialExperiment(
                        EXPERIMENT_ACCESSION,
                        new Date(),
                        ImmutableList.of(Pair.of(contrast1, true)),
                        "description",
                        new Species("species", SpeciesProperties.UNKNOWN),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        experimentDesign);

        this.subject = new DifferentialExperimentContrastLines(experiment);

        Iterator<String[]> lines = subject.iterator();

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, CHARACTERISTIC, SAMPLE_HEADER,
                        SAMPLE_VALUE2, SAMPLE_ONTOLOGY_ID2}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, FACTOR, FACTOR_HEADER, FACTOR_VALUE1,
                        HTTP_OBO + FACTOR_ONTOLOGY_ID1}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE1,
                        HTTP_OBO + SAMPLE_ONTOLOGY_ID1}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE4, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, FACTOR, FACTOR_HEADER,
                        FACTOR_VALUE2, FACTOR_ONTOLOGY_ID2}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE3, ""}));
    }

    @Test
    public void duplicateContrastDetailLines() {
        AssayGroup referenceAssayGroup =
                new AssayGroup(
                        "g1",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY1), new BiologicalReplicate(ASSAY2)));
        AssayGroup testAssayGroup =
                new AssayGroup(
                        "g2",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY3), new BiologicalReplicate(ASSAY4)));
        Contrast contrast1 =
                new Contrast(
                        CONTRAST_ID, "array design accession", referenceAssayGroup, testAssayGroup, "display name");

        ExperimentDesign experimentDesign = new ExperimentDesign();
        //This group is for Reference Assay (added two same assay to the group)
        experimentDesign.putSampleCharacteristic(ASSAY1, SAMPLE_HEADER, SAMPLE_VALUE1);
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE1);

        experimentDesign.putSampleCharacteristic(ASSAY2, SAMPLE_HEADER, SAMPLE_VALUE1);
        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE1);

        //This group is for Test Assay
        experimentDesign.putSampleCharacteristic(ASSAY3, SAMPLE_HEADER, SAMPLE_VALUE3);
        experimentDesign.putFactor(ASSAY3, FACTOR_HEADER, FACTOR_VALUE2);
        experimentDesign.putSampleCharacteristic(ASSAY4, SAMPLE_HEADER, SAMPLE_VALUE4);
        experimentDesign.putFactor(ASSAY4, FACTOR_HEADER, FACTOR_VALUE2);

        //Adding the contrasts
        DifferentialExperiment experiment =
                new DifferentialExperiment(
                        EXPERIMENT_ACCESSION,
                        new Date(),
                        ImmutableList.of(Pair.of(contrast1, true)),
                        "description",
                        new Species("species", SpeciesProperties.UNKNOWN),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        experimentDesign);

        this.subject = new DifferentialExperimentContrastLines(experiment);

        Iterator<String[]> lines = subject.iterator();

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE1,
                        ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, FACTOR, FACTOR_HEADER, FACTOR_VALUE1, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE4, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, FACTOR, FACTOR_HEADER, FACTOR_VALUE2, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE3, ""}));
    }

    @Test
    public void emptyValuesDetailLines() {
        AssayGroup referenceAssayGroup =
                new AssayGroup(
                        "g1",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY1), new BiologicalReplicate(ASSAY2)));
        AssayGroup testAssayGroup =
                new AssayGroup(
                        "g2",
                        ImmutableSet.of(new BiologicalReplicate(ASSAY3), new BiologicalReplicate(ASSAY4)));
        Contrast contrast1 =
                new Contrast(
                        CONTRAST_ID, "array design accession", referenceAssayGroup, testAssayGroup, "display name");

        ExperimentDesign experimentDesign = new ExperimentDesign();
        //This group is for Reference Assay (added two same assay to the group)
        experimentDesign.putSampleCharacteristic(ASSAY1, SAMPLE_HEADER, "");
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE1);

        experimentDesign.putSampleCharacteristic(ASSAY2, SAMPLE_HEADER, SAMPLE_VALUE2);
        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE1);

        //This group is for Test Assay
        experimentDesign.putSampleCharacteristic(ASSAY3, SAMPLE_HEADER, SAMPLE_VALUE3);
        experimentDesign.putFactor(ASSAY3, FACTOR_HEADER, FACTOR_VALUE2);
        experimentDesign.putSampleCharacteristic(ASSAY4, SAMPLE_HEADER, SAMPLE_VALUE4);
        experimentDesign.putFactor(ASSAY4, FACTOR_HEADER, FACTOR_VALUE2);

        //Adding the contrasts
        DifferentialExperiment experiment =
                new DifferentialExperiment(
                        EXPERIMENT_ACCESSION,
                        new Date(),
                        ImmutableList.of(Pair.of(contrast1, true)),
                        "description",
                        new Species("species", SpeciesProperties.UNKNOWN),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        experimentDesign);

        this.subject = new DifferentialExperimentContrastLines(experiment);

        Iterator<String[]> lines = subject.iterator();

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE2,
                        ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, FACTOR, FACTOR_HEADER, FACTOR_VALUE1, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, REFERENCE, CHARACTERISTIC, SAMPLE_HEADER, "", ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE4, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, FACTOR, FACTOR_HEADER, FACTOR_VALUE2, ""}));

        assertThat(
                lines.next(),
                is(new String[] {
                        EXPERIMENT_ACCESSION, CONTRAST_ID, TEST, CHARACTERISTIC, SAMPLE_HEADER, SAMPLE_VALUE3, ""}));
    }
}
