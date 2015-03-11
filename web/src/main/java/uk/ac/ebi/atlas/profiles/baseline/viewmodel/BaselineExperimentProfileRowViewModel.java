package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.model.ExperimentType;

public class BaselineExperimentProfileRowViewModel {

    private final String id;
    private final String name;
    private final String experimentType;

    private final BaselineExpressionViewModel[] expressions;
    private final String serializedFilterFactors;

    public BaselineExperimentProfileRowViewModel(String id, String name, BaselineExpressionViewModel[] expressions, String serializedFilterFactors, String experimentType) {
        this.id = id;
        this.name = name;
        this.expressions = expressions;
        this.serializedFilterFactors = serializedFilterFactors;
        this.experimentType = experimentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExperimentType() {
        return experimentType;
    }


    public String getSerializedFilterFactors() {
        return serializedFilterFactors;
    }

    public BaselineExpressionViewModel[] getExpressions() {
        return expressions;
    }
}
