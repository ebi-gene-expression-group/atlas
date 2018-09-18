package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import static com.google.common.collect.ImmutableSetMultimap.flatteningToImmutableSetMultimap;

@Component
public class ConditionsLookupService {
    private final ConditionsCollector conditionsCollector;

    public ConditionsLookupService(ConditionsCollector conditionsCollector) {
        this.conditionsCollector = conditionsCollector;
    }

    public Multimap<String, String> conditionsPerDataColumnDescriptor(BaselineExperiment experiment) {
        return conditionsCollector.getConditions(experiment)
                .collect(flatteningToImmutableSetMultimap(
                        Condition::getAssayGroupId,
                        condition -> condition.getValues().stream()));
    }

    public Multimap<String, String> conditionsPerDataColumnDescriptor(DifferentialExperiment experiment) {
        return conditionsCollector.getConditions(experiment)
                .collect(flatteningToImmutableSetMultimap(
                        DifferentialCondition::getContrastId,
                        condition -> condition.getValues().stream()));
    }
}
