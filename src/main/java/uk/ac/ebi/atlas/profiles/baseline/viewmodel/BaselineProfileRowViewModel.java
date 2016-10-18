package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineProfileRowViewModel {

    private final String id;
    private final String name;
    private final BaselineExpressionViewModel[] expressions;

    public BaselineProfileRowViewModel(String id, String name, BaselineExpressionViewModel[] expressions) {
        this.id = id;
        this.name = name;
        this.expressions = expressions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BaselineExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
