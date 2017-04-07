package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*
 *  ExperimentalFactors has factor information per _assay group_.
 *  ExperimentalFactors is used to render the experiment page.
 *  At experiment load time it is built from a ExperimentDesign and list of assay group ids.
 *
 *  ExperimentDesign also stores factors but per _assay_. It is used to render the
 *  experiment design page.
 *
 *  This is currently tied up because of the widget code.
 *
 */
@Deprecated //replaced with ExperimentDisplayDefaults and a AssayGroup->FactorGroup map
public class ExperimentalFactors implements Serializable {

    private String defaultQueryFactorType;
    private Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId;

    ExperimentalFactors(Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId,
                        String defaultQueryFactorType) {
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroupId;
        this.defaultQueryFactorType = defaultQueryFactorType;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public FactorGroup getNonDefaultFactors(String assayGroupId) {
        FactorGroup factorGroup = orderedFactorGroupsByAssayGroupId.get(assayGroupId);
        return factorGroup.withoutType(getDefaultQueryFactorType());
    }

    // return factors for the slice specified
    public Set<Factor> getComplementFactors(final FactorGroup slice) {
        return getComplementFactors(Sets.newTreeSet(slice));
    }

    public Set<Factor> getComplementFactors(final Set<Factor> filterFactors) {

        if (CollectionUtils.isEmpty(filterFactors)) {
            return getAllFactors();
        }

        TreeSet<Factor> filteredFactors = Sets.newTreeSet();

        for (FactorGroup factorGroup : orderedFactorGroupsByAssayGroupId.values()) {

            List<Factor> remainingFactors = factorGroup.without(filterFactors);
            if (remainingFactors.size() == 1) {
                filteredFactors.add(remainingFactors.get(0));
            }
        }

        return filteredFactors;
    }

    public FactorGroup getFactorGroup(String assayGroupId) {
        return orderedFactorGroupsByAssayGroupId.get(assayGroupId);
    }

    public Set<Factor> getAllFactors() {
        return FluentIterable.concat(orderedFactorGroupsByAssayGroupId.values()).toSet();
    }

}
