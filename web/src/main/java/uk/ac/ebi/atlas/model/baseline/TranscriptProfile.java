package uk.ac.ebi.atlas.model.baseline;

import java.util.Collections;
import java.util.List;

public class TranscriptProfile {

    private String identifier;

    private List<Double> expressions;

    public TranscriptProfile(String identifier, List<Double> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Double> getExpressions() {
        return Collections.unmodifiableList(expressions);
    }
}
