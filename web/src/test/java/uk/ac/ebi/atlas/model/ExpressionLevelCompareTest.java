package uk.ac.ebi.atlas.model;

import org.junit.Test;
import uk.ac.ebi.atlas.util.ExpressionLevelBuilder;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ExpressionLevelCompareTest {

    @Test
    public void testCompareEqualObjects() {
        ExpressionLevel instance1 = ExpressionLevelBuilder.createExpressionLevelInstance("id", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));
        ExpressionLevel instance2 = ExpressionLevelBuilder.createExpressionLevelInstance("id", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));

        assertEquals(0, instance1.compareTo(instance2));
        assertEquals(0, instance2.compareTo(instance1));
    }

    @Test
    public void testCompareRPKMDiff() {
        ExpressionLevel instance1 = ExpressionLevelBuilder.createExpressionLevelInstance("id", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));
        ExpressionLevel instance2 = ExpressionLevelBuilder.createExpressionLevelInstance("id", 101, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));

        assertTrue(instance1.compareTo(instance2) < 0);
        assertTrue(instance2.compareTo(instance1) > 0);
    }


    @Test
    public void testCompareIDDiff() {
        ExpressionLevel instance1 = ExpressionLevelBuilder.createExpressionLevelInstance("abc", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));
        ExpressionLevel instance2 = ExpressionLevelBuilder.createExpressionLevelInstance("def", 100, new FactorValue("f1", "v1"), new FactorValue("f2", "v2"));

        assertTrue(instance1.compareTo(instance2) < 0);
        assertTrue(instance2.compareTo(instance1) > 0);
    }
}
