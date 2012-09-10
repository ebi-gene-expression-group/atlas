package uk.ac.ebi.atlas.model;

import junitx.extensions.EqualsHashCodeTestCase;

public class ExpressionLevelEqualsHashCodeTest extends EqualsHashCodeTestCase {


    public ExpressionLevelEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        TranscriptExpressionLevel transcriptExpressionLevel = new TranscriptExpressionLevel("id", 100).addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2");
        return transcriptExpressionLevel;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        TranscriptExpressionLevel transcriptExpressionLevel = new TranscriptExpressionLevel("id", 200).addFactorValue("f1", "v1")
                .addFactorValue("f2", "v2_2");
        return transcriptExpressionLevel;
    }

}
