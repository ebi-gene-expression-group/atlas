package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import java.util.Comparator;

public class Contrast {
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

    public static Comparator<Contrast> orderByDisplayName() {
        return Ordering.natural().onResultOf(new Function<Contrast, Comparable>() {
            @Override
            public Comparable apply(Contrast contrast) {
                return contrast.getDisplayName();
            }
        });
    }
}
