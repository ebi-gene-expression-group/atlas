package uk.ac.ebi.atlas.profiles.differential.viewmodel;

public class DifferentialProfileRowViewModel2 {

    private final String id;
    private final String name;
    private final String designElement;
    private final DifferentialExpressionViewModel[] expressions;

    public DifferentialProfileRowViewModel2(String id, String name, String designElement, DifferentialExpressionViewModel[] expressions) {
        this.id = id;
        this.name = name;
        this.designElement = designElement;
        this.expressions = expressions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesignElement() {
        return designElement;
    }

    public DifferentialExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
