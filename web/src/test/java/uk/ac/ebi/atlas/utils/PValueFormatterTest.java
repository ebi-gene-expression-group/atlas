
package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.PValueFormatter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PValueFormatterTest {

    private PValueFormatter subject = new PValueFormatter();

    @Test
    public void formatPValueAsScientificNotationEdges() {
        assertThat(subject.formatPValueAsScientificNotation(10.0d), is("1E1"));
        assertThat(subject.formatPValueAsScientificNotation(-10.0d), is("-1E1"));

        assertThat(subject.formatPValueAsScientificNotation(9.999d), is("1E1"));
        assertThat(subject.formatPValueAsScientificNotation(-9.999d), is("-1E1"));

        assertThat(subject.formatPValueAsScientificNotation(9.99d), is("9.99"));
        assertThat(subject.formatPValueAsScientificNotation(-9.99d), is("-9.99"));
    }

    @Test
    public void formatPValueAsScientificNotation() {
        assertThat(subject.formatPValueAsScientificNotation(0.0d), is("0"));

        assertThat(subject.formatPValueAsScientificNotation(1.2E-11d), is("<1E-10"));
        assertThat(subject.formatPValueAsScientificNotation(-1.2E-11d), is("-1.2E-11"));

        assertThat(subject.formatPValueAsScientificNotation(1.2E-5d), is("1.2E-5"));
        assertThat(subject.formatPValueAsScientificNotation(-1.2E-5d), is("-1.2E-5"));

        assertThat(subject.formatPValueAsScientificNotation(123.456d), is("1.23E2"));
        assertThat(subject.formatPValueAsScientificNotation(-123.456d), is("-1.23E2"));

        assertThat(subject.formatPValueAsScientificNotation(123.567d), is("1.24E2"));
        assertThat(subject.formatPValueAsScientificNotation(-123.567d), is("-1.24E2"));

        assertThat(subject.formatPValueAsScientificNotation(10.123d), is("1.01E1"));
        assertThat(subject.formatPValueAsScientificNotation(-10.123d), is("-1.01E1"));

        assertThat(subject.formatPValueAsScientificNotation(10.623d), is("1.06E1"));
        assertThat(subject.formatPValueAsScientificNotation(-10.623d), is("-1.06E1"));

        assertThat(subject.formatPValueAsScientificNotation(0.123d), is("0.123"));
        assertThat(subject.formatPValueAsScientificNotation(-0.123d), is("-0.123"));

        assertThat(subject.formatPValueAsScientificNotation(0.1267d), is("0.127"));
        assertThat(subject.formatPValueAsScientificNotation(-0.1267d), is("-0.127"));

        assertThat(subject.formatPValueAsScientificNotation(1.32d), is("1.32"));
        assertThat(subject.formatPValueAsScientificNotation(-1.32d), is("-1.32"));

        assertThat(subject.formatPValueAsScientificNotation(1.356d), is("1.356"));
        assertThat(subject.formatPValueAsScientificNotation(-1.356d), is("-1.356"));

        assertThat(subject.formatPValueAsScientificNotation(1.3567d), is("1.357"));
        assertThat(subject.formatPValueAsScientificNotation(-1.3567d), is("-1.357"));

        assertThat(subject.formatPValueAsScientificNotation(9.02d), is("9.02"));

    }

}
