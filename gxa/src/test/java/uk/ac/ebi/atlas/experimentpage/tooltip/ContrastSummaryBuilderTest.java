package uk.ac.ebi.atlas.experimentpage.tooltip;

import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ContrastSummaryBuilderTest {

    private static final String REF_ASSAY = "REF_ASSAY";
    private static final String FACTOR_HEADER = "factor header";
    private static final String FACTOR_VALUE = "factor value";

    private static final String TEST_ASSAY = "TEST_ASSAY";
    private static final String FACTOR_HEADER2 = "factor header 2";
    private static final String FACTOR_VALUE2 = "factor value 2";

    private static final String SAMPLE_HEADER = "sample header";
    private static final String SAMPLE_VALUE1 = "sample value 1";
    private static final String SAMPLE_VALUE2 = "sample value 2";
    private static final String EXPERIMENT_DESCRIPTION = "experiment description";
    private static final String CONTRAST_ID = "contrast id";
    private static final String ARRAY_DESIGN_ACCESSION = "array design accession";
    private static final AssayGroup REF_ASSAY_GROUP = new AssayGroup("ref_id", REF_ASSAY);
    private static final AssayGroup TEST_ASSAY_GROUP = new AssayGroup("test_id", TEST_ASSAY);
    private static final String CONTRAST_NAME = "contrast description";
    private static final Contrast CONTRAST = new Contrast(CONTRAST_ID, ARRAY_DESIGN_ACCESSION, REF_ASSAY_GROUP, TEST_ASSAY_GROUP, CONTRAST_NAME);
    private static final String FACTOR_VALUE3 = "factor value 3";
    private static final String FACTOR_VALUE4 = "factor value 4";

    @Test
    public void build(){
        ExperimentDesign experimentDesign = new ExperimentDesign();
        experimentDesign.putFactor(REF_ASSAY, FACTOR_HEADER, FACTOR_VALUE);
        experimentDesign.putFactor(REF_ASSAY, FACTOR_HEADER2, FACTOR_VALUE2);

        experimentDesign.putFactor(TEST_ASSAY, FACTOR_HEADER, FACTOR_VALUE3);
        experimentDesign.putFactor(TEST_ASSAY, FACTOR_HEADER2, FACTOR_VALUE4);

        experimentDesign.putSampleCharacteristic(REF_ASSAY, SAMPLE_HEADER, SAMPLE_VALUE1);
        experimentDesign.putSampleCharacteristic(TEST_ASSAY, SAMPLE_HEADER, SAMPLE_VALUE2);

        ContrastSummaryBuilder subject = new ContrastSummaryBuilder().
                withExperimentDescription(EXPERIMENT_DESCRIPTION).forContrast(CONTRAST).
                withExperimentDesign(experimentDesign);

        ContrastSummary contrastSummary = subject.build();

        Iterator<ContrastProperty> contrastSummaryIterator = contrastSummary.iterator();

        ContrastProperty contrastProperty1 = contrastSummaryIterator.next();
        ContrastProperty contrastProperty2 = contrastSummaryIterator.next();
        ContrastProperty contrastProperty3 = contrastSummaryIterator.next();
        ContrastProperty contrastProperty4 = contrastSummaryIterator.next();
        assertThat(contrastSummaryIterator.hasNext(), is(false));

        assertThat(contrastProperty1, is(new ContrastProperty(FACTOR_HEADER, FACTOR_VALUE3, FACTOR_VALUE,ContrastPropertyType.FACTOR)));
        assertThat(contrastProperty2, is(new ContrastProperty(FACTOR_HEADER2, FACTOR_VALUE4, FACTOR_VALUE2,ContrastPropertyType.FACTOR)));
        assertThat(contrastProperty3, is(new ContrastProperty("array design", "", "",ContrastPropertyType.SAMPLE)));
        assertThat(contrastProperty4, is(new ContrastProperty(SAMPLE_HEADER, SAMPLE_VALUE2, SAMPLE_VALUE1,ContrastPropertyType.SAMPLE)));

    }

}
