package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class BaselineExperimentSearchResultProducer {

    private final ExperimentTrader experimentTrader;

    @Inject
    public BaselineExperimentSearchResultProducer(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public BaselineExperimentProfilesList buildProfilesForExperiments(Map<String, Map<String, Double>> expressionsPerColumnPerExperiment,
                                                                      String factorType) {
        return trimAndSort(profilesForExpressions(expressionsPerColumnPerExperiment, factorType));
    }

    BaselineExperimentProfilesList trimAndSort(Collection<BaselineExperimentProfile> profiles){
        BaselineExperimentProfilesList result = new BaselineExperimentProfilesList();
        for(BaselineExperimentProfile profile: profiles){
            if(!profile.hasAllExpressionsEqualZero()){
                result.add(profile);
            }
        }
        Collections.sort(result);
        result.setTotalResultCount(result.size());
        return result;
    }

    Collection<BaselineExperimentProfile> profilesForExpressions(Map<String, Map<String, Double>> expressionsPerColumnPerExperiment,
                                                                 final String factorType) {
        BaselineExperimentProfilesList resultRows = new BaselineExperimentProfilesList();

        for(Map.Entry<String, Map<String, Double>> e: expressionsPerColumnPerExperiment.entrySet()) {
            final BaselineExperiment experiment =
                    (BaselineExperiment) experimentTrader.getPublicExperiment(e.getKey());
            Map<String, Double> assayGroupIdAndExpression = e.getValue();

            final Set<String> commonFactorTypes =
                    RichFactorGroup.typesWithCommonValues(FluentIterable.from(assayGroupIdAndExpression.keySet()).transform(new Function<String, FactorGroup>() {
                        @Override
                        public FactorGroup apply(String idOfAssayGroupWithExpression) {
                            return experiment.getFactors(experiment.getDataColumnDescriptor(idOfAssayGroupWithExpression)).withoutTypes(ImmutableList.of(factorType));
                        }
                    }));

            for(final FactorGroup factorGroup: FluentIterable.from(experiment.getDataColumnDescriptors()).transform(new Function<AssayGroup, FactorGroup>() {
                @Override
                public FactorGroup apply(AssayGroup assayGroup) {
                    return experiment.getFactors(assayGroup).withoutTypes(ImmutableList.of(factorType)).withoutTypes(commonFactorTypes);
                }
            }).toSet()){
                BaselineExperimentProfile baselineExperimentProfile =
                        new BaselineExperimentProfile(experiment,factorGroup);
                for(AssayGroup assayGroup: FluentIterable.from(experiment.getDataColumnDescriptors()).filter(new Predicate<AssayGroup>() {
                    @Override
                    public boolean apply(AssayGroup assayGroup) {
                        return RichFactorGroup.isSubgroup(experiment.getFactors(assayGroup), factorGroup);
                    }
                })){
                    baselineExperimentProfile.add(
                            new FactorAcrossExperiments(experiment.getFactors(assayGroup).factorOfType(factorType)),
                            new BaselineExpression(Optional.fromNullable(assayGroupIdAndExpression.get(assayGroup.getId())).or(0.0d), assayGroup.getId())
                    );
                }
                resultRows.add(baselineExperimentProfile);
            }
        }
        return resultRows;
    }
}
