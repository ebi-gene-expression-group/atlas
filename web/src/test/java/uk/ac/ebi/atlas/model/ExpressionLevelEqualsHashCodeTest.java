package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class ExpressionLevelEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public ExpressionLevelEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        return createExpressionLevelInstance("id", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        return createExpressionLevelInstance("id", 200, new FactorValue("f1", "v1"), new FactorValue("f2", "v2_2"));
    }

    private ExpressionLevel createExpressionLevelInstance(String identifier, int rpkm, FactorValue... factorValues) {
        ExpressionLevel expressionLevel = new ExpressionLevel(identifier, rpkm);
        for (FactorValue factorValue : factorValues) {
            expressionLevel.addFactorValue(factorValue);

        }
        return expressionLevel;
    }

}
