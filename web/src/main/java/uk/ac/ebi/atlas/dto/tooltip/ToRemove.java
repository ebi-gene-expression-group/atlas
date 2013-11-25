package uk.ac.ebi.atlas.dto.tooltip;

import com.google.common.collect.Sets;

import java.util.Set;

public class ToRemove {
    public static void main(String[] args) {
        Set<Integer> set = Sets.newTreeSet();

        set.add(5);
        set.add(6);
        set.add(1);

        set.contains(1);

    }
}
