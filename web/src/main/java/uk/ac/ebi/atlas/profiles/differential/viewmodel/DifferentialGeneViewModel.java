package uk.ac.ebi.atlas.profiles.differential.viewmodel;

public class DifferentialGeneViewModel {

    private final String geneId;
    private final String geneName;
    private final DifferentialExpressionViewModel[] expressions;

    public DifferentialGeneViewModel(String geneId, String geneName, DifferentialExpressionViewModel[] expressions) {
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

    public DifferentialExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
