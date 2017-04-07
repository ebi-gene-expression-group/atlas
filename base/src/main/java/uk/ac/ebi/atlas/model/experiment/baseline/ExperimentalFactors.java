package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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
    private SortedSetMultimap<String, Factor> factorsByType = TreeMultimap.create();
    private LinkedHashMultimap<String, Factor> xmlFactorsByType = LinkedHashMultimap.create();
    private BiMap<String, String> factorDisplayNamesByType = HashBiMap.create();
    private List<FactorGroup> orderedFactorGroups;
    private Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId;

    ExperimentalFactors(SortedSetMultimap<String, Factor> factorsByType,
                        Map<String, String> factorDisplayNamesByType,
                        List<FactorGroup> orderedFactorGroups,
                        Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId,
                        String defaultQueryFactorType) {
        this.factorsByType = factorsByType;
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroupId;
        this.factorDisplayNamesByType.putAll(factorDisplayNamesByType);
        this.orderedFactorGroups = orderedFactorGroups;
        this.defaultQueryFactorType = defaultQueryFactorType;
    }

    // TODO It might be better to have a general LinkedHashMultiMap for the factors and, depending on the XML, order it
    // or not. So we can have a single interface for ExperimentalFactors and avoid bugs such as https://www.pivotaltracker.com/story/show/97196678
    ExperimentalFactors(LinkedHashMultimap<String, Factor> factorsByType,
                        Map<String, String> factorDisplayNamesByType,
                        List<FactorGroup> orderedFactorGroups,
                        Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId,
                        String defaultQueryFactorType) {
        this.xmlFactorsByType = factorsByType;
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroupId;
        this.factorDisplayNamesByType.putAll(factorDisplayNamesByType);
        this.orderedFactorGroups = orderedFactorGroups;
        this.defaultQueryFactorType = defaultQueryFactorType;
    }

    private boolean orderingSpecifiedByCuratorsInConfigurationFile() {
        return xmlFactorsByType != null && !xmlFactorsByType.isEmpty();
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public FactorGroup getNonDefaultFactors(String assayGroupId) {
        FactorGroup factorGroup = orderedFactorGroupsByAssayGroupId.get(assayGroupId);
        return factorGroup.withoutType(getDefaultQueryFactorType());
    }

    // return factors for the slice specified
    public SortedSet<Factor> getComplementFactors(final FactorGroup slice) {
        return getComplementFactors(Sets.newTreeSet(slice));
    }

    public SortedSet<Factor> getComplementFactors(final Set<Factor> filterFactors) {

        if (CollectionUtils.isEmpty(filterFactors)) {
            return getAllFactors();
        }

        TreeSet<Factor> filteredFactors = Sets.newTreeSet();

        for (FactorGroup factorGroup : orderedFactorGroups) {

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

    public SortedSet<Factor> getFactors(Collection<String> assayGroupIds, String factorType) {
        SortedSet<Factor> factors = Sets.newTreeSet();
        for (String assayGroupId : assayGroupIds) {
            FactorGroup factorGroupForAssay = getFactorGroup(assayGroupId);
            Factor defaultFactorForAssay = factorGroupForAssay.factorOfType(factorType);
            factors.add(defaultFactorForAssay);
        }
        return factors;
    }

    public SortedSet<Factor> getAllFactors() {
        if (orderingSpecifiedByCuratorsInConfigurationFile()) {
            return ImmutableSortedSet.copyOf(xmlFactorsByType.values());
        } else {
            return ImmutableSortedSet.copyOf(factorsByType.values());
        }
    }

    @Override
    public String toString() {
        return "ExperimentalFactors: orderedFactorGroups = " + orderedFactorGroups
                + ", factorsByType = " + factorsByType;
    }

}
