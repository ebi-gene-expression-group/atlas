package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.Experiment;

import java.util.*;

public class DifferentialExperiment extends Experiment{

    private SortedSet<Contrast> contrasts = Sets.newTreeSet(orderByDisplayName());

    public DifferentialExperiment(String accession, Set<Contrast> contrasts, String description, boolean hasExtraInfoFile) {
        super(accession, description, hasExtraInfoFile);
        this.contrasts.addAll(contrasts);
    }

    public SortedSet<Contrast> getContrasts() {
        return Collections.unmodifiableSortedSet(contrasts);
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
