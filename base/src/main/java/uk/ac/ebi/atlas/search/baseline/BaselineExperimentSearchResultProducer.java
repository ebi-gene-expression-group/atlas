package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BaselineExperimentSearchResultProducer {
    private final ExperimentTrader experimentTrader;

    public BaselineExperimentSearchResultProducer(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public BaselineExperimentProfilesList buildProfilesForExperiments(
            Map<String, Map<String, Double>> expressionsPerColumnPerExperiment, String factorType) {
        return trimAndSort(profilesForExpressions(expressionsPerColumnPerExperiment, factorType));
    }

    private BaselineExperimentProfilesList trimAndSort(Collection<BaselineExperimentProfile> profiles) {
        BaselineExperimentProfilesList result =
                profiles.stream()
                        .filter(profile -> !profile.hasAllExpressionsEqualZero())
                        .sorted()
                        .collect(Collectors.toCollection(BaselineExperimentProfilesList::new));
        result.setTotalResultCount(result.size());
        return result;
    }

    private Collection<BaselineExperimentProfile> profilesForExpressions(
            Map<String, Map<String, Double>> expressionsPerColumnPerExperiment, final String factorType) {
        BaselineExperimentProfilesList resultRows = new BaselineExperimentProfilesList();

        for (Map.Entry<String, Map<String, Double>> e: expressionsPerColumnPerExperiment.entrySet()) {
            final BaselineExperiment experiment =
                    (BaselineExperiment) experimentTrader.getPublicExperiment(e.getKey());
            Map<String, Double> assayGroupIdAndExpression = e.getValue();

            final Set<String> commonFactorTypes =
                    RichFactorGroup.typesWithCommonValues(assayGroupIdAndExpression.keySet().stream()
                            .map(idOfAssayGroupWithExpression ->
                                    experiment.getFactors(
                                            experiment.getDataColumnDescriptor(idOfAssayGroupWithExpression))
                                            .withoutTypes(ImmutableList.of(factorType)))
                            .collect(toList()));

            final Set<FactorGroup> factorGroups = experiment.getDataColumnDescriptors().stream().map(assayGroup -> {
                FactorGroup factorGroup = experiment.getFactors(assayGroup).withoutTypes(ImmutableList.of(factorType));
                if (factorGroup.withoutTypes(commonFactorTypes).size() > 0) {
                    return factorGroup.withoutTypes(commonFactorTypes);
                } else {
                    return factorGroup;
                }
            }).collect(Collectors.toSet());

            for (final FactorGroup factorGroup: factorGroups) {
                BaselineExperimentProfile baselineExperimentProfile =
                        new BaselineExperimentProfile(experiment, factorGroup);

                for (AssayGroup assayGroup :
                        experiment.getDataColumnDescriptors().stream()
                                .filter(assayGroup ->
                                        RichFactorGroup.isSubgroup(experiment.getFactors(assayGroup), factorGroup))
                                .collect(toList())) {
                    baselineExperimentProfile.add(
                            new FactorAcrossExperiments(experiment.getFactors(assayGroup).factorOfType(factorType)),
                            new BaselineExpression(
                                    Optional.ofNullable(assayGroupIdAndExpression.get(assayGroup.getId()))
                                            .orElse(0.0))
                    );
                }

                resultRows.add(baselineExperimentProfile);
            }
        }
        return resultRows;
    }
}
