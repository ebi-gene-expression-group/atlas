package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Set;

public class AssayGroup implements Iterable<String>{

    private Set<String> accessions;

    public AssayGroup(String... accessions) {
        this.accessions = Sets.newHashSet(accessions);
    }

    @Override
    public Iterator<String> iterator() {
        return accessions.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.accessions, ((AssayGroup) other).accessions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accessions);
    }
}
