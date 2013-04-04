package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

public class AssayGroup implements Iterable<String>{

    private Set<String> assayAccessions;

    public AssayGroup(String... assayAccessions) {
        this.assayAccessions = Sets.newHashSet(assayAccessions);
    }

    @Override
    public Iterator<String> iterator() {
        return assayAccessions.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.assayAccessions, ((AssayGroup) other).assayAccessions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(assayAccessions);
    }
}
