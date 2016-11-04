package uk.ac.ebi.atlas.baseline;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentalFactorsBuilder {

    private String defaultQueryType;

    private Set<Factor> defaultFilterFactors;

    private Map<String, String> factorNamesByType = new HashMap<>();

    private List<FactorGroup> orderedFactorGroups;

    private Map<String, FactorGroup> orderedFactorGroupsByAssayGroupId;

    private Set<String> menuFilterFactorTypes;

    public ExperimentalFactorsBuilder withDefaultQueryType(String defaultQueryType) {
        this.defaultQueryType = defaultQueryType;
        return this;
    }

    public ExperimentalFactorsBuilder withDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        this.defaultFilterFactors = defaultFilterFactors;
        return this;
    }

    public ExperimentalFactorsBuilder withFactorNamesByType(Map<String, String> factorNamesByType) {
        this.factorNamesByType = factorNamesByType;
        return this;
    }

    public ExperimentalFactorsBuilder withOrderedFactorGroups(List<FactorGroup> orderedFactorGroups) {
        this.orderedFactorGroups = orderedFactorGroups;
        return this;
    }

    public ExperimentalFactorsBuilder withOrderedFactorGroupsByAssayGroupId(Map<String, FactorGroup> orderedFactorGroupsByAssayGroup) {
        this.orderedFactorGroupsByAssayGroupId = orderedFactorGroupsByAssayGroup;
        return this;
    }

    public ExperimentalFactorsBuilder withMenuFilterFactorTypes(Set<String> menuFilterFactorTypes) {

        this.menuFilterFactorTypes = menuFilterFactorTypes;
        return this;
    }

    public ExperimentalFactors create() {
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");

        return new ExperimentalFactors(buildFactorsByType(), factorNamesByType, orderedFactorGroups,
                menuFilterFactorTypes, orderedFactorGroupsByAssayGroupId, defaultQueryType, defaultFilterFactors);
    }

    public ExperimentalFactors createFromXML() {
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");

        return new ExperimentalFactors(buildXmlFactorsByType(), factorNamesByType, orderedFactorGroups,
                menuFilterFactorTypes, orderedFactorGroupsByAssayGroupId, defaultQueryType, defaultFilterFactors);
    }

    SortedSetMultimap<String, Factor> buildFactorsByType() {
        SortedSetMultimap<String, Factor> factorsByType = TreeMultimap.create();

        for (FactorGroup factorGroup : orderedFactorGroups) {
            for (Factor factor : factorGroup) {
                factorsByType.put(factor.getType(), factor);
            }
        }
        return factorsByType;
    }

    private LinkedHashMultimap<String, Factor> buildXmlFactorsByType() {
        LinkedHashMultimap<String, Factor> xmlFactorsByType = LinkedHashMultimap.create();

        for(FactorGroup factorGroup : orderedFactorGroups) {
            for (Factor factor : factorGroup) {
                xmlFactorsByType.put(factor.getType(), factor);
            }
        }

        return xmlFactorsByType;

    }
}
