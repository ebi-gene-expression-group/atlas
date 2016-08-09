package uk.ac.ebi.atlas.model.baseline;

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
 */
public class ExperimentalFactors implements Serializable {

    private String defaultQueryFactorType;

    private Set<Factor> defaultFilterFactors;

    private SortedSetMultimap<String, Factor> factorsByType = TreeMultimap.create();

    private LinkedHashMultimap<String, Factor> xmlFactorsByType = LinkedHashMultimap.create();

    private BiMap<String, String> factorDisplayNamesByType = HashBiMap.create();

    private SetMultimap<Factor, Factor> coOccurringFactors = TreeMultimap.create();

    private Set<String> menuFilterFactorTypes;

    private List<FactorGroup> orderedFactorGroups;

    private Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId;

    ExperimentalFactors(SortedSetMultimap<String, Factor> factorsByType,
                        Map<String, String> factorDisplayNamesByType,
                        List<FactorGroup> orderedFactorGroups,
                        Set<String> menuFilterFactorTypes,
                        Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId,
                        String defaultQueryFactorType,
                        Set<Factor> defaultFilterFactors) {
        this.factorsByType = factorsByType;
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroupId;
        this.factorDisplayNamesByType.putAll(factorDisplayNamesByType);
        this.orderedFactorGroups = orderedFactorGroups;
        this.coOccurringFactors = createCoOccurringFactors(orderedFactorGroups);
        this.menuFilterFactorTypes = menuFilterFactorTypes;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactors = defaultFilterFactors;
    }

    // TODO It might be better to have a general LinkedHashMultiMap for the factors and, depending on the XML, order it
    // or not. So we can have a single interface for ExperimentalFactors and avoid bugs such as https://www.pivotaltracker.com/story/show/97196678
    ExperimentalFactors(LinkedHashMultimap<String, Factor> factorsByType,
                        Map<String, String> factorDisplayNamesByType,
                        List<FactorGroup> orderedFactorGroups,
                        Set<String> menuFilterFactorTypes,
                        Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId,
                        String defaultQueryFactorType,
                        Set<Factor> defaultFilterFactors) {
        this.xmlFactorsByType = factorsByType;
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroupId;
        this.factorDisplayNamesByType.putAll(factorDisplayNamesByType);
        this.orderedFactorGroups = orderedFactorGroups;
        this.coOccurringFactors = createCoOccurringFactors(orderedFactorGroups);
        this.menuFilterFactorTypes = menuFilterFactorTypes;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactors = defaultFilterFactors;
    }

    static SetMultimap<Factor, Factor> createCoOccurringFactors(List<FactorGroup> orderedFactorGroups) {

        SortedSetMultimap<Factor, Factor> coOccurringFactors = TreeMultimap.create();

        for (FactorGroup factorGroup : orderedFactorGroups) {
            for (Factor factor : factorGroup) {
                for (Factor value : factorGroup) {
                    if (!value.equals(factor)) {
                        coOccurringFactors.put(factor, value);
                    }
                }
            }
        }
        return coOccurringFactors;
    }

    private boolean orderingSpecifiedByCuratorsInConfigurationFile() {
        return xmlFactorsByType != null && !xmlFactorsByType.isEmpty();
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<Factor> getDefaultFilterFactors() {
        return Collections.unmodifiableSet(defaultFilterFactors);
    }

    public String getFactorDisplayName(String type) {

        checkState(factorDisplayNamesByType.containsKey(type), "Cannot find a factor with the given factor type: " + type);

        return factorDisplayNamesByType.get(type);
    }

    public String getFactorType(String displayName) {

        checkState(factorDisplayNamesByType.inverse().containsKey(displayName), "Cannot find a factor with the given factor display name: " + displayName);

        return factorDisplayNamesByType.inverse().get(displayName);
    }

    //ToDo: this is only used to build factor filter menu, maybe should be encapsulated in a menu builder and the menu builder could be used by a menu cache loader
    public Set<Factor> getCoOccurringFactors(Factor factor) {
        return coOccurringFactors.get(factor);
    }

    public ImmutableSortedSet<Factor> getFactors(String type) {

        return ImmutableSortedSet.copyOf(factorsByType.get(type));

    }

    public ImmutableMap<String, Factor> getFactorGroupedByAssayGroupId(String factorType) {
        ImmutableMap.Builder<String, Factor> builder = ImmutableMap.builder();
        for (String groupId : orderedFactorGroupsByAssayGroupId.keySet()) {
            Factor factor = orderedFactorGroupsByAssayGroupId.get(groupId).factorOfType(factorType);
            checkNotNull(factor, String.format("No factor of type %s for assay group %s", factorType, groupId));
            builder.put(groupId, factor);
        }

        return builder.build();
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

    // match each FactorGroup with the filterFactors, and for each match return the remaining single factor
    // (if there is one and only one)
    public List<AssayGroupFactor> getComplementAssayGroupFactors(final Set<Factor> filterFactors) {

        Set<AssayGroupFactor> result =
                orderingSpecifiedByCuratorsInConfigurationFile() ? Sets.<AssayGroupFactor>newLinkedHashSet() :
                        Sets.<AssayGroupFactor>newTreeSet();

        for (String groupId : orderedFactorGroupsByAssayGroupId.keySet()) {
            List<Factor> remainingFactors;

            if (CollectionUtils.isNotEmpty(filterFactors)) {
                remainingFactors = orderedFactorGroupsByAssayGroupId.get(groupId).without(filterFactors);
            } else {
                remainingFactors = Lists.newArrayList(orderedFactorGroupsByAssayGroupId.get(groupId).iterator());
            }
            if (remainingFactors.size() == 1) {
                result.add(new AssayGroupFactor(groupId, remainingFactors.get(0)));
            }
        }

        return Lists.newArrayList(result);
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

    public SortedSet<String> getMenuFilterFactorNames() {

        SortedSet<String> factorNames = Sets.newTreeSet();

        for (String type : menuFilterFactorTypes) {

            String factorName = getFactorDisplayName(type);
            factorNames.add(factorName);
        }

        return factorNames;
    }

    public SortedSet<Factor> getAllFactors() {
        if (orderingSpecifiedByCuratorsInConfigurationFile()) {
            return ImmutableSortedSet.copyOf(xmlFactorsByType.values());
        } else {
            return ImmutableSortedSet.copyOf(factorsByType.values());
        }
    }

    public SortedSet<Factor> getAllFactorsOrderedByXML() {
        return ImmutableSortedSet.copyOf(xmlFactorsByType.values());
    }

    // ordered the same as the assay group ids in the expression levels .tsv
    public ImmutableList<FactorGroup> getFactorGroupsInOrder() {
        return ImmutableList.copyOf(orderedFactorGroups);
    }

    @Override
    public String toString() {
        return "ExperimentalFactors: orderedFactorGroups = " + orderedFactorGroups
                + ", factorsByType = " + factorsByType;
    }
}
