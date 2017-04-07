package uk.ac.ebi.atlas.search.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@AutoValue
public abstract class BaselineExperimentSlice {

    public static BaselineExperimentSlice create(BaselineExperiment experiment, AssayGroup assayGroup) {
        return new AutoValue_BaselineExperimentSlice(experiment, assayGroup);
    }

    public abstract BaselineExperiment experiment();
    public abstract AssayGroup assayGroup();
    public FactorGroup filterFactors(){
        return experiment().getFactors(assayGroup()).withoutType(experiment().getDisplayDefaults().defaultQueryFactorType());
    }

    // these will typically be the tissue (ie: ORGANISM_PART) factors for the slice
    public Set<Factor> nonFilterFactors() {
        return getComplementFactors(filterFactors());
    }

    public String species() {
        return experiment().getSpecies().getName();
    }

    public String experimentAccession() {
        return experiment().getAccession();
    }

    public String experimentDisplayName() {
        return experiment().getDisplayName();
    }

    public ExperimentType getExperimentType() {
        return experiment().getType();
    }



    /*
    TODO I got moved here from ExperimentalFactors.java and my logic is incredibly questionable and abstruse.
     */
    // return factors for the slice specified
    Set<Factor> getComplementFactors(final FactorGroup slice) {
        return getComplementFactors(Sets.newTreeSet(slice));
    }

    Set<Factor> getComplementFactors(final Set<Factor> filterFactors) {

        if (CollectionUtils.isEmpty(filterFactors)) {
            return getAllFactors();
        }

        TreeSet<Factor> filteredFactors = Sets.newTreeSet();

        for (AssayGroup assayGroup : experiment().getDataColumnDescriptors()) {
            FactorGroup factorGroup = experiment().getFactors(assayGroup);
            List<Factor> remainingFactors = factorGroup.without(filterFactors);
            if (remainingFactors.size() == 1) {
                filteredFactors.add(remainingFactors.get(0));
            }
        }

        return filteredFactors;
    }

    Set<Factor> getAllFactors() {
        return FluentIterable.from(experiment().getDataColumnDescriptors()).transformAndConcat(new Function<AssayGroup, Iterable<Factor>>() {
            @Override
            public Iterable<Factor> apply(AssayGroup assayGroup) {
                return experiment().getFactors(assayGroup);
            }
        }).toSet();
    }
}
