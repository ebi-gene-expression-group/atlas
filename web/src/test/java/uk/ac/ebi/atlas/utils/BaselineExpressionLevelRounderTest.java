package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BaselineExpressionLevelRounderTest {

    BaselineExpressionLevelRounder subject = new BaselineExpressionLevelRounder();

    @Test
    public void formatBaselineExpressionLevel_SmallValues() {
        assertThat(subject.format(0.9), is("0.9"));
        assertThat(subject.format(0.05176479), is("0.0517648"));
        assertThat(subject.format(0.90), is("0.9"));
        assertThat(subject.format(0.95), is("1"));

        assertThat(subject.format(1.0), is("1"));
        assertThat(subject.format(1.1), is("1"));
        assertThat(subject.format(1.4), is("1"));
        assertThat(subject.format(1.5), is("2"));
    }

    @Test
    public void formatBaselineExpressionLevel_LargeValues() {
        assertThat(subject.format(0.9), is("0.9"));
        assertThat(subject.format(0.90), is("0.9"));
        assertThat(subject.format(0.95), is("1"));

        assertThat(subject.format(1.0), is("1"));
        assertThat(subject.format(1.1), is("1"));
        assertThat(subject.format(1.4), is("1"));
        assertThat(subject.format(1.5), is("2"));
    }


}