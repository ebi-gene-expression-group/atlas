package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class TranscriptExpressionEqualsHashCodeTest extends EqualsHashCodeTestCase {

    private ExperimentRun experimentRun = new ExperimentRun("RUN_ACCESSION");


    public TranscriptExpressionEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        TranscriptExpression transcriptExpression = new TranscriptExpression("id", 100, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2");
        return transcriptExpression;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        TranscriptExpression transcriptExpression = new TranscriptExpression("id", 200, experimentRun)
                .addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2_2");
        return transcriptExpression;
    }

}
