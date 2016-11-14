
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.MicroarrayExperimentDesignTablePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MicroarrayExperimentDesignPageEMtab1066SIT extends SeleniumFixture {

    public static final String DEFAULT = "genotype:'cycC mutant' vs 'wild type'";
    public static final String OTHER = "genotype:'cdk8 mutant' vs 'wild type'";
    private MicroarrayExperimentDesignTablePage subject;

    @Before
    public void setUp() {
        subject = new MicroarrayExperimentDesignTablePage(driver);
        subject.get();
    }

    @Test
    public void testContrastDefault() {

        // given
        assertThat(subject.getSelectedContrast(), is(DEFAULT));

        // then
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("K1", "A-AFFY-35", "3rd instar larva", "w1118; +; cdk8K185", "Drosophila melanogaster", "", "cdk8 mutant"));
        assertThat(subject.getDownloadExperimentDesignLink(), endsWith("E-MTAB-1066/experiment-design.tsv"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

    @Test
    public void testSelectAContrast() {

        // given
        subject.selectContrast("g2_g1");

        // then
        assertThat(subject.getSelectedContrast(), is(DEFAULT));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("K1", "A-AFFY-35", "3rd instar larva", "w1118; +; cdk8K185", "Drosophila melanogaster", "", "cdk8 mutant"));

        // and
        assertThat(subject.getLineColor(1), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(4), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

    @Test
    public void testSelectAnotherContrast() {

        // given
        subject.selectContrast("g2_g3");

        // then
        assertThat(subject.getSelectedContrast(), is(OTHER));
        assertThat(subject.getExperimentDesignTableHeader().size(), is(10));
        assertThat(subject.getFirstExperimentDesignTableLine(), contains("K1", "A-AFFY-35", "3rd instar larva", "w1118; +; cdk8K185", "Drosophila melanogaster", "", "cdk8 mutant"));

        // and
        assertThat(subject.getLineColor(1), is("rgba(130, 205, 205, 1)"));
        assertThat(subject.getLineColor(4), isOneOf("transparent","rgba(0, 0, 0, 0)"));
        assertThat(subject.getLineColor(9), is("rgba(255, 194, 102, 1)"));

    }

}