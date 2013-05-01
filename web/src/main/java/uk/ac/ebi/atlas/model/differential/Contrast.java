package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Ordering;

import java.util.Comparator;

public class Contrast implements Comparable<Contrast>{
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

    @Override
    public boolean equals(Object other){
        if (other == null || !(other instanceof Contrast)){
            return false;
        }
        return Objects.equal(id, ((Contrast)other).id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Contrast o) {

        if (o == null){
            return 1;
        }
        return this.getDisplayName().compareTo(o.getDisplayName());
    }
}
