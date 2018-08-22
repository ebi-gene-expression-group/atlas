package uk.ac.ebi.atlas.model.experiment.summary;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;

import java.util.Objects;

public class ContrastProperty extends AssayProperty {
    private final String referenceValue;

    public ContrastProperty(String propertyName,
                            String testValue,
                            String referenceValue,
                            ContrastPropertyType contrastPropertyType) {
        super(propertyName, testValue, contrastPropertyType);
        this.referenceValue = referenceValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContrastProperty)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ContrastProperty that = (ContrastProperty) o;

        return referenceValue != null ?
                referenceValue.equalsIgnoreCase(that.referenceValue) :
                that.referenceValue == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceValue != null ? referenceValue.toLowerCase().hashCode() : 0);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("propertyName", propertyName)
                .add("referenceValue", referenceValue)
                .add("testValue", testValue)
                .toString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject o = super.toJson();
        o.addProperty("referenceValue", referenceValue);
        return o;
    }
}
