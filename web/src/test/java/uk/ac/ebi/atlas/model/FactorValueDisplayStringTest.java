package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FactorValueDisplayStringTest {

    private FactorValue factorValue;

    @Before
    public void initFactorValue(){
        factorValue = new FactorValue("or_gan_type", "organ", "liver");
    }

    @Test
    public void displayStringShouldBeFactorColonValue() {
    	assertThat(factorValue.getDisplayString(), is("organ:liver"));
    }
}
