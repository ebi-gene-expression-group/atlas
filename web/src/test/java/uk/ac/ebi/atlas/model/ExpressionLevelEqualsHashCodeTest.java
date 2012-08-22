package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;
import uk.ac.ebi.atlas.util.ExpressionLevelBuilder;

public class ExpressionLevelEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public ExpressionLevelEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        return ExpressionLevelBuilder.createExpressionLevelInstance("id", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        return ExpressionLevelBuilder.createExpressionLevelInstance("id", 200, new FactorValue("f1", "v1"), new FactorValue("f2", "v2_2"));
    }

}
