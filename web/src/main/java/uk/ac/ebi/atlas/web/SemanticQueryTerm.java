package uk.ac.ebi.atlas.web;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.StringUtils;

@AutoValue
public abstract class SemanticQueryTerm {
    public abstract String value();
    public abstract String category();

    public static SemanticQueryTerm create(String value) {
        return create(value, "");
    }

    public static SemanticQueryTerm create(String value, String category) {
        return new AutoValue_SemanticQueryTerm(value, category);
    }

    public boolean hasNoCategory() {
        return StringUtils.isBlank(category());
    }

    public boolean hasValue() {
        return !StringUtils.isBlank(value());
    }

    public String description() {
        if (hasNoCategory()) {
            return (String.format("\"%s\"", value()));
        } else {
            return (String.format("\"%s\" (%s)", value(), category()));
        }
    }

    @Override
    public String toString() {
        if (hasNoCategory()) {
            return (String.format("\"%s\"", value()));
        } else {
            return (String.format("\"%s:{%s}\"", category(), value()));
        }
    }
}
