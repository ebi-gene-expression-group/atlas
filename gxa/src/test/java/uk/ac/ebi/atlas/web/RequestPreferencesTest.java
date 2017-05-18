package uk.ac.ebi.atlas.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class RequestPreferencesTest {

    public RnaSeqBaselineRequestPreferences rnaSeqBaselineDefault = (RnaSeqBaselineRequestPreferences) new BeanWrapperImpl(new RnaSeqBaselineRequestPreferences()).getRootInstance();
    public ProteomicsBaselineRequestPreferences proteomicsBaselineDefault = (ProteomicsBaselineRequestPreferences) new BeanWrapperImpl(new ProteomicsBaselineRequestPreferences()).getRootInstance();
    public DifferentialRequestPreferences differentialDefault = (DifferentialRequestPreferences) new BeanWrapperImpl(new
            DifferentialRequestPreferences()).getRootInstance();
    public MicroarrayRequestPreferences microarrayDefault = (MicroarrayRequestPreferences) new BeanWrapperImpl(new
            MicroarrayRequestPreferences()).getRootInstance();

    @Test
    public void testDefault(){
        assertEquals(new RnaSeqBaselineRequestPreferences().getDefaultGeneQuery(), rnaSeqBaselineDefault.getGeneQuery());
        assertThat(rnaSeqBaselineDefault.getGeneQuery().description(), Matchers.containsString("protein_coding"));

        assertEquals(new ProteomicsBaselineRequestPreferences().getDefaultGeneQuery(), proteomicsBaselineDefault.getGeneQuery());
        assertThat(proteomicsBaselineDefault.getGeneQuery().description(), Matchers.containsString("protein_coding"));

        assertThat(differentialDefault.getGeneQuery().size(), is(0));
        assertThat(microarrayDefault.getGeneQuery().size(), is(0));
    }


}