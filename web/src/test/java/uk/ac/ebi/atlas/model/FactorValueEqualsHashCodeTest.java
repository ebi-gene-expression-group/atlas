package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class FactorValueEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public FactorValueEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        return new FactorValue("factor", "name");
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        return new FactorValue("factor_1", "name_1");
    }
}
