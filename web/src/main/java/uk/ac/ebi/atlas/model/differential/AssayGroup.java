package uk.ac.ebi.atlas.model.differential;

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
}
