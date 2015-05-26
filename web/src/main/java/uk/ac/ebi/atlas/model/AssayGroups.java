package uk.ac.ebi.atlas.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class AssayGroups implements Iterable<AssayGroup> {

    private Map<String, AssayGroup> assayGroupsById;

    public AssayGroups(Collection<AssayGroup> assayGroups) {
        this.assayGroupsById = Maps.newLinkedHashMap();
        for (AssayGroup assayGroup : assayGroups) {
            assayGroupsById.put(assayGroup.getId(), assayGroup);
        }
    }

    @Override
    public Iterator<AssayGroup> iterator() {
        return assayGroupsById.values().iterator();
    }

    public Set<String> getAssayAccessions() {
        Set<String> assayAccessions = Sets.newHashSet();

        for (AssayGroup assayGroup : assayGroupsById.values()) {
            CollectionUtils.addAll(assayAccessions, assayGroup.iterator());
        }

        return assayAccessions;
    }

    public Set<String> getAssayGroupIds() {
        return assayGroupsById.keySet();
    }

    public AssayGroup getAssayGroup(String assayGroupId) {
        return assayGroupsById.get(assayGroupId);
    }
}
