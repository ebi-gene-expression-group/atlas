package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class GeneExpressionEqualsHashCodeTest extends EqualsHashCodeTestCase {

    private ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION");


    public GeneExpressionEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        GeneExpression geneExpression = new GeneExpression("id", 100, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2");
        return geneExpression;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        GeneExpression geneExpression = new GeneExpression("id", 200, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2_2");
        return geneExpression;
    }

}
