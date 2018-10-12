package uk.ac.ebi.atlas.model.experiment;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AutoValue
public abstract class ExperimentDisplayDefaults {
    // The UI treats it as entirely reasonable that there can be multiple defaults.
    // The curators only specify single values, the code apparently used to let them specify multiple values separated
    // by a comma, but every now and then there is a field in the SDRF naturally containing a comma, e.g.:
    // <value>skin-derived, feeder-free conditions</value>
    // and stuff doesn't work.
    // We could agree on supporting multiple defaults - then change this to Multimap<String, String>.
    abstract Map<String, String> defaultFilterValues();
    public abstract List<String> prescribedOrderOfFilters();
    public abstract boolean preserveColumnOrder();

    public List<String> defaultFilterValuesForFactor(String factorHeader) {
        return defaultFilterValues().containsKey(Factor.normalize(factorHeader)) ?
                ImmutableList.of(defaultFilterValues().get(Factor.normalize(factorHeader))) :
                ImmutableList.of();
    }
    // The curators used to have to specify a slice of the experiment they want displayed, say which dimension to splay
    // (defaultQueryFactorType) and values for other filters. How cumbersome!
    // Note that for gene page, this what will go in the columns, and experiment + other factors will go on rows
    public String defaultQueryFactorType() {
        for (String filter: prescribedOrderOfFilters()) {
            if (!defaultFilterValues().keySet().contains(filter)) {
                return filter;
            }
        }
        return "";
    }

    /*
        See factors.xml e.g.:
            <defaultFilterFactors>
                <filterFactor>
                    <type>TIME</type>
                    <value>4 day</value>
                </filterFactor>
                <filterFactor>
                    <type>CELL_TYPE</type>
                    <value>CD4+ T cell</value>
                </filterFactor>
                <filterFactor>
                    <type>INFECT</type>
                    <value>Plasmodium chabaudi chabaudi</value>
                </filterFactor>
            </defaultFilterFactors>
            <defaultQueryFactorType>INDIVIDUAL</defaultQueryFactorType>
            <menuFilterFactorTypes>CELL_TYPE, INDIVIDUAL, INFECT, TIME</menuFilterFactorTypes>
    */
    public static ExperimentDisplayDefaults simpleDefaults() {
        return create(ImmutableMap.of(), ImmutableList.of(), false);
    }

    public static ExperimentDisplayDefaults create(Collection<Factor> defaultFilterFactors,
                                                   final String defaultQueryFactorType,
                                                   List<String> menuFilterFactorTypes, boolean preserveColumnOrder) {
        return create(
                defaultFilterFactors,
                ImmutableList.<String>builder()
                        .add(defaultQueryFactorType)
                        .addAll(
                                menuFilterFactorTypes.stream()
                                        .filter(factorType -> !factorType.equals(defaultQueryFactorType))
                                        .collect(Collectors.toList()))
                        .build(),
                preserveColumnOrder);
    }

    public static ExperimentDisplayDefaults create(Collection<Factor> defaultFilterFactors,
                                                   List<String> prescribedOrderOfFilters,
                                                   boolean preserveColumnOrder) {
        ImmutableMap.Builder<String, String> b = ImmutableMap.builder();
        for (Factor factor : defaultFilterFactors) {
            b.put(factor.getType(), factor.getValue());
        }
        return create(b.build(), prescribedOrderOfFilters, preserveColumnOrder);
    }

    private static ExperimentDisplayDefaults create(Map<String, String> defaultFilterValues,
                                                    List<String> prescribedOrderOfFilters,
                                                    boolean preserveColumnOrder) {
        ImmutableList.Builder<String> b = ImmutableList.<String>builder().addAll(prescribedOrderOfFilters);
        defaultFilterValues.keySet().stream()
                .filter(filterType -> !prescribedOrderOfFilters.contains(filterType))
                .forEach(b::add);
        return new AutoValue_ExperimentDisplayDefaults(
                defaultFilterValues, prescribedOrderOfFilters, preserveColumnOrder);
    }


}
