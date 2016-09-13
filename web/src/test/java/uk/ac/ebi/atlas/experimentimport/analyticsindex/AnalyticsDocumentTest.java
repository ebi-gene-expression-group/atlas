package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnalyticsDocumentTest {

    @Test(expected = NullPointerException.class)
    public void cannotBuildBaselineWithoutExpressionLevel() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void cannotBuildDifferentialWithoutNumReplicates() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .factors(Collections.singleton("sex"))
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void cannotBuildDifferentialWithoutFoldChange() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .factors(Collections.singleton("sex"))
                .numReplicates(1)
                .build();
    }

    @Test
    public void rnaSeqBaselineCutoff() {
        AnalyticsDocument documentAboveCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(0.500001)
                .build();
        assertThat(documentAboveCutoff.isAboveExpressionThreshold(), is(true));

        AnalyticsDocument documentBelowCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(0.5)
                .build();
        assertThat(documentBelowCutoff.isAboveExpressionThreshold(), is(false));
    }

    @Test
    public void proteomicsBaselineCutoff() {
        AnalyticsDocument documentAboveCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.PROTEOMICS_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(0.000001)
                .build();
        assertThat(documentAboveCutoff.isAboveExpressionThreshold(), is(true));

        AnalyticsDocument documentBelowCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.PROTEOMICS_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(0)
                .build();
        assertThat(documentBelowCutoff.isAboveExpressionThreshold(), is(false));
    }

    @Test
    public void differentialCutoff() {
        AnalyticsDocument documentAboveThreshold = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .factors(Collections.singleton("time"))
                .numReplicates(1)
                .foldChange(-1.000001)
                .pValue(0.099999)
                .tStatistics(-0.184)
                .build();
        assertThat(documentAboveThreshold.isAboveExpressionThreshold(), is(true));

        AnalyticsDocument documentAbovePValueCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL)
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .factors(Collections.singleton("time"))
                .numReplicates(1)
                .foldChange(10)
                .pValue(0.1)
                .tStatistics(-0.184)
                .build();
        assertThat(documentAbovePValueCutoff.isAboveExpressionThreshold(), is(false));

        AnalyticsDocument documentBelowLogFoldChangeCutoff = AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .identifierSearch("foo")
                .species("bar")
                .kingdom("barimals")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .factors(Collections.singleton("time"))
                .numReplicates(1)
                .foldChange(0.999)
                .pValue(0.0001)
                .tStatistics(-0.184)
                .build();
        assertThat(documentBelowLogFoldChangeCutoff.isAboveExpressionThreshold(), is(false));
    }
}
