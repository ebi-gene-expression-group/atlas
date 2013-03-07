package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.*;

public class DifferentialExperiment {

    private SortedSet<Contrast> contrasts = Sets.newTreeSet(orderByDisplayName());

    private String description;

    private boolean hasExtraInfoFile;

    public DifferentialExperiment(Set<Contrast> contrasts, String description, boolean hasExtraInfoFile) {
        this.contrasts.addAll(contrasts);
        this.description = description;
        this.hasExtraInfoFile = hasExtraInfoFile;
    }

    public SortedSet<Contrast> getContrasts() {
        return Collections.unmodifiableSortedSet(contrasts);
    }

    public String getDescription() {
        return description;
    }

    public boolean hasExtraInfoFile() {
        return hasExtraInfoFile;
    }

    private Comparator<Contrast> orderByDisplayName(){
        return Ordering.natural().onResultOf(new Function<Contrast, Comparable>() {
            @Override
            public Comparable apply(Contrast contrast) {
                return contrast.getDisplayName();
            }
        });
    }

}
