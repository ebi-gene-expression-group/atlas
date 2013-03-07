package uk.ac.ebi.atlas.model.differential;

public class Contrast{
    private String id;
    private AssayGroup referenceAssayGroup;
    private AssayGroup testAssayGroup;

    private String displayName;


    public Contrast(String id, AssayGroup referenceAssayGroup, AssayGroup testAssayGroup, String displayName) {
        this.id = id;
        this.referenceAssayGroup = referenceAssayGroup;
        this.testAssayGroup = testAssayGroup;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public AssayGroup getReferenceAssayGroup() {
        return referenceAssayGroup;
    }

    public AssayGroup getTestAssayGroup() {
        return testAssayGroup;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Contrast{" +
                "id='" + id + '\'' +
                ", referenceAssayGroup=" + referenceAssayGroup +
                ", testAssayGroup=" + testAssayGroup +
                ", displayName='" + displayName + '\'' +
                '}';
    }

}
