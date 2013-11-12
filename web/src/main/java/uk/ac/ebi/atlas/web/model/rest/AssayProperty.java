package uk.ac.ebi.atlas.web.model.rest;

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

}
