package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;

// <T extends Profile, ...> would avoid the interface be implemented by BaselineExpressionsInputStream
public interface ExpressionProfileInputStream<T, K extends Expression> extends ObjectInputStream<T> {
    T createProfile();
    void addExpressionToBuilder(K expression);
    void addGeneInfoValueToBuilder(String[] values);
}
