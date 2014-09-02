package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineExperimentProfileRowViewModel {

    private final String id;
    private final String name;
    private final BaselineExpressionViewModel[] expressions;
    private final String serializedFilterFactors;

    public BaselineExperimentProfileRowViewModel(String id, String name, BaselineExpressionViewModel[] expressions, String serializedFilterFactors) {
        this.id = id;
        this.name = name;
        this.expressions = expressions;
        this.serializedFilterFactors = serializedFilterFactors;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSerializedFilterFactors() {
        return serializedFilterFactors;
    }

    public BaselineExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
