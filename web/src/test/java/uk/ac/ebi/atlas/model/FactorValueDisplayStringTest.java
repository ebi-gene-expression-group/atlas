package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

public class FactorValueDisplayStringTest {

    private FactorValue factorValue;

    @Before
    public void initFactorValue(){
        factorValue = new FactorValue("organ", "liver");
    }

    @Test
    public void displayStringShouldBeFactorColonValue() {

    }
}
