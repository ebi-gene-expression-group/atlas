package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineGeneViewModel {

    private final String geneId;
    private final String geneName;
    private final BaselineExpressionViewModel[] expressions;

    public BaselineGeneViewModel(String geneId, String geneName, BaselineExpressionViewModel[] expressions) {
        this.geneId = geneId;
        this.geneName = geneName;
        this.expressions = expressions;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public BaselineExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
