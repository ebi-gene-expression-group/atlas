
package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.map.LazyMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named("filterFactorMenuBuilder")
@Scope("prototype")
public class FilterFactorMenuBuilder {

    private ExperimentalFactors experimentalFactors;
    private SortedSet<Factor> factors;

    public FilterFactorMenuBuilder withExperimentalFactors(ExperimentalFactors experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
        return this;
    }

    public FilterFactorMenuBuilder forFilterFactors(Set<Factor> setOfFactor) {
        this.factors = new TreeSet<>(setOfFactor);
        return this;
    }

    public SortedSet<FilterFactorMenuVoice> build() {
        checkState(experimentalFactors != null, "Please provide experimental factors");
        checkState(CollectionUtils.isNotEmpty(factors), "Please provide a set of factors");

        return extractVoicesForFactors(factors);
    }

    protected SortedSet<FilterFactorMenuVoice> extractVoicesForFactors(Set<Factor> setOfFactor) {

        Map<String, Set<Factor>> factorNames = extractFactorNames(setOfFactor);

        SortedSet<FilterFactorMenuVoice> result = new TreeSet<>();
        for (String factorName : factorNames.keySet()) {
            FilterFactorMenuVoice voice = new FilterFactorMenuVoice(factorName);
            voice.setType(experimentalFactors.getFactorType(factorName));

            SortedSet<FilterFactorMenuVoice> children = buildVoices(factorNames.get(factorName), setOfFactor);
            voice.setChildren(children);
            result.add(voice);
        }

        return result;
    }

    protected SortedSet<FilterFactorMenuVoice> buildVoices(Set<Factor> factorsForFactorName, Set<Factor> allFactors) {

        SortedSet<FilterFactorMenuVoice> result = new TreeSet<>();
        for (Factor factor : factorsForFactorName) {
            String value = factor.getValue();
            FilterFactorMenuVoice voice = new FilterFactorMenuVoice(value);
            voice.setFactor(factor);
            result.add(voice);

            // limit set of factors for following levels
            Set<Factor> withoutCurrent = Sets.newHashSet(allFactors);
            withoutCurrent.removeAll(factorsForFactorName);

            // prepare recursion
            Set<Factor> remaining = filterRemainingFactors(factor, withoutCurrent);
            if (CollectionUtils.isNotEmpty(remaining)) {
                SortedSet<FilterFactorMenuVoice> children = extractVoicesForFactors(remaining);
                voice.setChildren(children);
            }
        }

        return result;
    }

    protected Set<Factor> filterRemainingFactors(Factor factor, Set<Factor> allFactors) {
        Set<Factor> remaining = new HashSet<>(experimentalFactors.getCoOccurringFactors(factor));
        remaining.retainAll(allFactors);

        return remaining;
    }

    protected Map<String, Set<Factor>> extractFactorNames(Set<Factor> setOfFactor) {

        @SuppressWarnings("unchecked")
        Map<String, Set<Factor>> result = LazyMap.decorate(new HashMap<>(), new Factory() {
            @Override
            public Object create() {
                return new HashSet<>();
            }
        });

        for (Factor factor : setOfFactor) {
            String type = factor.getType();
            String factorName = experimentalFactors.getFactorDisplayName(type);
            result.get(factorName).add(factor);
        }

        return result;
    }

    public String getLink(String queryFactorType, Factor... factors) {
        return getJsonUrl(queryFactorType, factors);
    }

    protected String getJsonUrl(String queryFactorType, Factor... factors) {
        return new Gson().toJson(buildFactorsCombination(queryFactorType, factors));
    }

    private FactorsCombination buildFactorsCombination(String queryFactorType, Factor... factors) {
        return new FactorsCombination(queryFactorType, factors);
    }

    private final class FactorsCombination {

        private String queryFactorType;

        private String serializedFactors;

        FactorsCombination(String queryFactorType, Factor... factors) {
            this.queryFactorType = queryFactorType;
            serializedFactors = FilterFactorsConverter.serialize(Lists.newArrayList(factors));
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getSerializedFactors() {
            return serializedFactors;
        }

    }

}