package model;

import junitx.extensions.EqualsHashCodeTestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * Date: 17/08/2012
 */
public class ExpressionLevelEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public ExpressionLevelEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Set<FactorValue> factorValues = new HashSet<>();
        factorValues.add(new FactorValue("f1", "v1"));
        factorValues.add(new FactorValue("f2", "v2"));

        return new ExpressionLevel("id1", factorValues, 100);
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Set<FactorValue> factorValues = new HashSet<>();
                factorValues.add(new FactorValue("f1", "v1"));
                factorValues.add(new FactorValue("f2", "v2"));

                return new ExpressionLevel("id1", factorValues, 200);
    }
}
