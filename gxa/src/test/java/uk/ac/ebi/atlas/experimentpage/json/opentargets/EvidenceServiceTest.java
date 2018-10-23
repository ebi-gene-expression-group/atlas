package uk.ac.ebi.atlas.experimentpage.json.opentargets;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.DifferentialProfileStreamFactory;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.testutils.MockExperiment.createDifferentialExperiment;

public class EvidenceServiceTest {
    private static final String E_GEOD_59612 = "E-GEOD-59612";

    private MockDataFileHub mockDataFileHub;

    @Mock
    private DifferentialProfileStreamFactory
            <DifferentialExpression, DifferentialExperiment, DifferentialProfileStreamOptions, RnaSeqProfile>
            differentialProfileStreamFactory;

    private AssayGroup referenceAssay1 = AssayGroupFactory.create("g1", "assay1");
    private AssayGroup testAssay1 = AssayGroupFactory.create("g2", "assay2");
    private AssayGroup referenceAssay2 = AssayGroupFactory.create("g3", "assay3");
    private AssayGroup testAssay2 = AssayGroupFactory.create("g4", "assay41", "assay42");

    private Contrast c1 = new Contrast("g1_g2", null, referenceAssay1, testAssay1, "first contrast");
    private Contrast c2 = new Contrast("g3_g4", null, referenceAssay2, testAssay2, "second contrast");

    private DifferentialExperiment experiment = createDifferentialExperiment(E_GEOD_59612, ImmutableList.of(c1, c2));

    private EvidenceService<DifferentialExpression, DifferentialExperiment, ?, ?> subject;

    @Before
    public void setUp() {
        mockDataFileHub = MockDataFileHub.create();
        this.subject =
                new EvidenceService<>(differentialProfileStreamFactory, mockDataFileHub, "expressionAtlasVersion");
    }

    @Test
    public void testGetPercentileRanks() {
        mockDataFileHub.addPercentileRanksFile(E_GEOD_59612, ImmutableList.of(
                "GeneId g1_g2 g3_g4".split(" "),
                "ENSG00000000003 89 97".split(" "),
                "ENSG00000000005 56 53".split(" ")));

        assertThat(subject.getPercentileRanks(experiment).get("ENSG00000000003").get(c1), is(89));
    }

    @Test
    public void understandNaAsLackOfValue() {
        mockDataFileHub.addPercentileRanksFile(E_GEOD_59612, ImmutableList.of(
                "GeneId g1_g2 g3_g4".split(" "),
                "ENSG00000000003 89 NA".split(" ")));

        assertThat(subject.getPercentileRanks(experiment).get("ENSG00000000003").get(c1), is(89));
        assertThat(subject.getPercentileRanks(experiment).get("ENSG00000000003").get(c2), is(nullValue()));
    }

    @Test
    public void pValuesRoundedLikeWeDid() {
        assertThat(
                subject.getPValue(new DifferentialExpression(5.21107983317421e-10, 0.0)),
                is(Double.valueOf("5.21e-10")));
    }

    @Test
    public void
    cellLineAsSampleCharacteristicButNoDiseaseAsFactorExcludeTypicalExperimentUsingDiseaseCellLinesForSomething() {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putSampleCharacteristic("g1", "cell line", "A1");
        experimentDesign.putSampleCharacteristic("g2", "cell line", "A1");
        experimentDesign.putSampleCharacteristic("g1", "disease", "cancer");
        experimentDesign.putSampleCharacteristic("g2", "disease", "cancer");
        experimentDesign.putSampleCharacteristic("g1", "lighting conditions", "light");
        experimentDesign.putSampleCharacteristic("g2", "lighting conditions", "dark");
        experimentDesign.putFactor("g1", "lighting conditions", "light");
        experimentDesign.putFactor("g2", "lighting conditions", "dark");

        assertThat(subject.cellLineAsSampleCharacteristicButNoDiseaseAsFactor(experimentDesign), is(true));
    }

    @Test
    public void cellLineAsSampleCharacteristicButNoDiseaseAsFactorDoNotExcludeExperimentStudyingDiseases() {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putSampleCharacteristic("g1", "cell line", "A1");
        experimentDesign.putSampleCharacteristic("g2", "cell line", "A2");
        experimentDesign.putSampleCharacteristic("g1", "disease", "cancer");
        experimentDesign.putFactor("g1", "disease", "cancer");
        experimentDesign.putSampleCharacteristic("g2", "disease", "normal");
        experimentDesign.putSampleCharacteristic("g2", "disease", "normal");


        assertThat(subject.cellLineAsSampleCharacteristicButNoDiseaseAsFactor(experimentDesign), is(false));
    }

    @Test
    public void cellLineAsSampleCharacteristicButNoDiseaseAsFactorDoNotExcludeTypicalExperiment() {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putSampleCharacteristic("g1", "disease", "cancer");
        experimentDesign.putSampleCharacteristic("g2", "disease", "normal");
        experimentDesign.putSampleCharacteristic("g1", "age", "32");
        experimentDesign.putSampleCharacteristic("g2", "age", "25");
        experimentDesign.putFactor("g1", "disease", "cancer");
        experimentDesign.putFactor("g2", "disease", "normal");

        assertThat(subject.cellLineAsSampleCharacteristicButNoDiseaseAsFactor(experimentDesign), is(false));
    }
}
