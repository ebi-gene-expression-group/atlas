package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.AssayGroup;

public class Contrast implements Comparable<Contrast> {
    private String id;
    private String arrayDesignAccession;
    private AssayGroup referenceAssayGroup;
    private AssayGroup testAssayGroup;
    private String displayName;

    public Contrast(String id, String arrayDesignAccession, AssayGroup referenceAssayGroup, AssayGroup testAssayGroup, String displayName) {
        this.id = id;
        this.arrayDesignAccession = arrayDesignAccession;
        this.referenceAssayGroup = referenceAssayGroup;
        this.testAssayGroup = testAssayGroup;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getArrayDesignAccession() {
        return arrayDesignAccession;
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
                ", arrayDesignAccesion=" + arrayDesignAccession +
                ", referenceAssayGroup=" + referenceAssayGroup +
                ", testAssayGroup=" + testAssayGroup +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Contrast) {
            return Objects.equal(id, ((Contrast) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Contrast o) {

        if (o == null) {
            return 1;
        }
        return this.getDisplayName().compareTo(o.getDisplayName());
    }
}
