package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class AssayGroup implements Iterable<String>{

    private String id;

    private Set<String> assayAccessions;

    public AssayGroup(String id, String... assayAccessions) {
        this.assayAccessions = Sets.newHashSet(assayAccessions);
    }

    @Override
    public Iterator<String> iterator() {
        return assayAccessions.iterator();
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {return Objects.hash(id, assayAccessions);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final AssayGroup other = (AssayGroup) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.assayAccessions, other.assayAccessions);
    }
}
