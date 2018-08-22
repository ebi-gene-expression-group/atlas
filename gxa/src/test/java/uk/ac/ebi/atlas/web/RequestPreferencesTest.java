package uk.ac.ebi.atlas.web;

import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import uk.ac.ebi.atlas.model.ExpressionUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RequestPreferencesTest {
    private RnaSeqBaselineRequestPreferences rnaSeqBaselineDefault =
            (RnaSeqBaselineRequestPreferences)
                    new BeanWrapperImpl(new RnaSeqBaselineRequestPreferences()).getRootInstance();

    private ProteomicsBaselineRequestPreferences proteomicsBaselineDefault =
            (ProteomicsBaselineRequestPreferences)
                    new BeanWrapperImpl(new ProteomicsBaselineRequestPreferences()).getRootInstance();

    private DifferentialRequestPreferences differentialDefault =
            (DifferentialRequestPreferences)
                    new BeanWrapperImpl(new DifferentialRequestPreferences()).getRootInstance();

    private MicroarrayRequestPreferences microarrayDefault =
            (MicroarrayRequestPreferences)
                    new BeanWrapperImpl(new MicroarrayRequestPreferences()).getRootInstance();

    @Test
    public void testDefaultGeneQuery() {
        assertThat(
                new RnaSeqBaselineRequestPreferences().getDefaultGeneQuery(),
                is(rnaSeqBaselineDefault.getGeneQuery()));
        assertThat(rnaSeqBaselineDefault.getGeneQuery().description(), containsString("protein_coding"));

        assertThat(
                new ProteomicsBaselineRequestPreferences().getDefaultGeneQuery(),
                is(proteomicsBaselineDefault.getGeneQuery()));
        assertThat(proteomicsBaselineDefault.getGeneQuery().description(), containsString("protein_coding"));

        assertThat(differentialDefault.getGeneQuery().size(), is(0));
        assertThat(microarrayDefault.getGeneQuery().size(), is(0));
    }

    @Test
    public void testDefaultUnit() {
        assertThat(rnaSeqBaselineDefault.getUnit(), is(ExpressionUnit.Absolute.Rna.TPM));
    }
}
