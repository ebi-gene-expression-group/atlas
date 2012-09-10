package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class ExpressionLevelEqualsHashCodeTest extends EqualsHashCodeTestCase {

    private ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION");


    public ExpressionLevelEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        TranscriptExpression transcriptExpressionLevel = new TranscriptExpression("id", 100, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2");
        return transcriptExpressionLevel;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        TranscriptExpression transcriptExpressionLevel = new TranscriptExpression("id", 200, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2_2");
        return transcriptExpressionLevel;
    }

}
