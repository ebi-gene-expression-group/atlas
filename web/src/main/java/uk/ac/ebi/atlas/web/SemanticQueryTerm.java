package uk.ac.ebi.atlas.web;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.StringUtils;

@AutoValue
public abstract class SemanticQueryTerm {
    public static SemanticQueryTerm create(String value) {
        return create(value, "");
    }

    public static SemanticQueryTerm create(String value, String category) {
        return new AutoValue_SemanticQueryTerm(value, category);
    }

    public abstract String value();
    public abstract String category();

    public boolean hasNoCategory() {
        return StringUtils.isBlank(category());
    }

    public boolean hasValue() {
        return !StringUtils.isBlank(value());
    }
}
