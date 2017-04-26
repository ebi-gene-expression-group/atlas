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
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
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

    public BaselineExperimentSearchResult buildProfilesForExperiments(List<Map<String, Object>> response,
                                                                      String factorType) {

        return buildProfilesForExpressions(extractAverageExpressionForEachColumnInExperiment(response), factorType);

    }

    static Map<String, Map<String, Double>> extractAverageExpressionForEachColumnInExperiment(List<Map<String, Object>> results) {

        Map<String, Map<String, Double>> result = new HashMap<>();

        for (Map<String, Object> experiment : results) {
            String experimentAccession = (String) experiment.get("val");
            int numberOfGenesExpressedAcrossAllAssayGroups = (int) experiment.get("uniqueIdentifiers");

            @SuppressWarnings("unchecked")
            Map<String, Object> assayGroupIdRoot = (Map<String, Object>) experiment.get("assayGroupId");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) assayGroupIdRoot.get("buckets");

            for(Map<String, Object> assayGroup : buckets)  {
                String assayGroupId = (String) assayGroup.get("val");
                double sumExpressionLevel;
                sumExpressionLevel = ((Number) assayGroup.get("sumExpressionLevel")).doubleValue();

                double expression =
                        BaselineExpressionLevelRounder.round(
                                sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);

                if(!result.containsKey(experimentAccession)){
                    result.put(experimentAccession, new HashMap<String, Double>());
                }
                result.get(experimentAccession).put(assayGroupId, expression);
            }
        }

        return result;

    }


    BaselineExperimentSearchResult buildProfilesForExpressions(Map<String, Map<String, Double>> expressionsPerColumnPerExperiment,
                                                               final String factorType) {


        BaselineExperimentProfilesList resultRows = new BaselineExperimentProfilesList();
        SortedSet<FactorAcrossExperiments> resultHeaders = new TreeSet<>();



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
                    FactorAcrossExperiments f = new FactorAcrossExperiments(experiment.getFactors(assayGroup).factorOfType(factorType));
                    baselineExperimentProfile.add(
                            f,
                            new BaselineExpression(Optional.fromNullable(assayGroupIdAndExpression.get(assayGroup.getId())).or(0.0d), assayGroup.getId())
                    );
                    resultHeaders.add(f);

                }
                resultRows.add(baselineExperimentProfile);
            }
        }

        Collections.sort(resultRows);
        resultRows.setTotalResultCount(resultRows.size());

        return new BaselineExperimentSearchResult(resultRows, ImmutableList.copyOf(resultHeaders));
    }

}
