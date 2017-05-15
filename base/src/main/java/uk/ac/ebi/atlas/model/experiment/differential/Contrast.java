
package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.DescribesDataColumns;

import java.util.Set;

public class Contrast extends DescribesDataColumns implements Comparable<Contrast> {
    private String arrayDesignAccession; //used only for micro-array experiments
    private AssayGroup referenceAssayGroup;
    private AssayGroup testAssayGroup;
    private String displayName;

    public Contrast(String id, String arrayDesignAccession, AssayGroup referenceAssayGroup, AssayGroup testAssayGroup, String displayName) {
        super(id);
        this.arrayDesignAccession = arrayDesignAccession;
        this.referenceAssayGroup = referenceAssayGroup;
        this.testAssayGroup = testAssayGroup;
        this.displayName = displayName;
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

    public Set<String> getAssayAccessions(){
        return Sets.newHashSet(getReferenceAssayGroup().getFirstAssayAccession()
                                , getTestAssayGroup().getFirstAssayAccession());
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

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        o.addProperty("id", id);
        o.addProperty("arrayDesignAccession", arrayDesignAccession);
        o.add("testAssayGroup", testAssayGroup.toJson());
        o.add("referenceAssayGroup", referenceAssayGroup.toJson());
        o.addProperty("displayName",displayName);
        return o;
    }

    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return ImmutableSet.<String>builder()
                .addAll(testAssayGroup.assaysAnalyzedForThisDataColumn())
                .addAll(referenceAssayGroup.assaysAnalyzedForThisDataColumn())
                .build();
    }
}
