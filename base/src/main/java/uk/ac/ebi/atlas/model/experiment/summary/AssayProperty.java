package uk.ac.ebi.atlas.model.experiment.summary;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;

import java.util.Objects;

public class AssayProperty implements Comparable<AssayProperty> {
    protected String propertyName;
    protected String testValue;
    protected ContrastPropertyType contrastPropertyType;

    public AssayProperty(String propertyName, String testValue, ContrastPropertyType contrastPropertyType) {
        this.testValue = testValue;
        this.contrastPropertyType = contrastPropertyType;
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getTestValue() {
        return testValue;
    }

    public ContrastPropertyType getContrastPropertyType() {
        return contrastPropertyType;
    }

    @Override
    public int compareTo(AssayProperty otherProperty) {
        if (contrastPropertyType != otherProperty.contrastPropertyType) {
            if (contrastPropertyType == ContrastPropertyType.FACTOR) {
                return -1;
            } else {
                return 1;
            }
        }
        return propertyName.compareToIgnoreCase(otherProperty.propertyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssayProperty that = (AssayProperty) o;

        if (propertyName != null ? !propertyName.equalsIgnoreCase(that.propertyName) : that.propertyName != null) {
            return false;
        }
        return testValue != null ? testValue.equalsIgnoreCase(that.testValue) : that.testValue == null;

    }

    @Override
    public int hashCode() {
        return Objects.hash(
                propertyName != null ? propertyName.toLowerCase().hashCode() : 0,
                testValue != null ? testValue.toLowerCase().hashCode() : 0);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("propertyName", propertyName)
                .add("testValue", testValue)
                .add("contrastPropertyType", contrastPropertyType)
                .toString();
    }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("propertyName", propertyName);
        o.addProperty("testValue", testValue);
        o.addProperty("contrastPropertyType", contrastPropertyType.name());
        return o;
    }
}
