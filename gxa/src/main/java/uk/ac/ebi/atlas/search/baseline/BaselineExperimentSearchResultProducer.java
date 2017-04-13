package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
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
                                                                      String defaultQueryFactorType) {

        return buildProfilesForExpressions(extractAverageExpressionLevel(response), defaultQueryFactorType);

    }

    static List<BaselineExperimentExpression> extractAverageExpressionLevel(List<Map<String, Object>> results) {

        ImmutableList.Builder<BaselineExperimentExpression> builder = ImmutableList.builder();

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
                BaselineExperimentExpression bslnExpression =
                        BaselineExperimentExpression.create(experimentAccession, assayGroupId, expression);

                builder.add(bslnExpression);
            }
        }

        return builder.build();

    }

    Map<String, FactorAcrossExperiments> factorsPerColumnId(BaselineExperiment experiment, String factorType){
        Map<String, FactorAcrossExperiments> result = new HashMap<>();
        for(AssayGroup assayGroup: experiment.getDataColumnDescriptors()){
            result.put(assayGroup.getId(), new FactorAcrossExperiments(experiment.getFactors(assayGroup).factorOfType(factorType)));
        }
        return result;
    }


    BaselineExperimentSearchResult buildProfilesForExpressions(List<BaselineExperimentExpression> expressions,
                                                                  String defaultQueryFactorType) {
        BaselineExperimentProfilesList resultRows = new BaselineExperimentProfilesList();
        SortedSet<FactorAcrossExperiments> resultHeaders = new TreeSet<>();

        Multimap<String, BaselineExperimentExpression> expressionsPerExperiment = Multimaps.index(expressions, new Function<BaselineExperimentExpression, String>() {
            @Override
            public String apply(BaselineExperimentExpression baselineExperimentExpression) {
                return baselineExperimentExpression.experimentAccession();
            }
        });

        for(String experimentAccession: expressionsPerExperiment.keySet()){
            BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);
            Map<String, FactorAcrossExperiments> columnIdsByFactorValue = factorsPerColumnId(experiment, defaultQueryFactorType);

            Multimap<FactorGroup, BaselineExperimentExpression> rowsForThisExperiment = LinkedListMultimap.create();
            for(BaselineExperimentExpression baselineExperimentExpression: expressionsPerExperiment.get(experimentAccession)){
                AssayGroup assayGroup = experiment.getDataColumnDescriptor(baselineExperimentExpression.assayGroupId());
                rowsForThisExperiment.put(experiment.getFactors(assayGroup).withoutTypes(ImmutableList.of(defaultQueryFactorType)), baselineExperimentExpression);
            }

            Set<String> commonFactorTypes = RichFactorGroup.typesWithCommonValues(rowsForThisExperiment.keySet());

            for(Map.Entry<FactorGroup, Collection<BaselineExperimentExpression>> e: rowsForThisExperiment.asMap().entrySet()){
                BaselineExperimentProfile baselineExperimentProfile =
                        new BaselineExperimentProfile(experiment,
                                e.getKey().withoutTypes(commonFactorTypes));
                for(BaselineExperimentExpression baselineExperimentExpression: e.getValue()){
                    FactorAcrossExperiments f = columnIdsByFactorValue.get(baselineExperimentExpression.assayGroupId());
                    baselineExperimentProfile.add(f, new BaselineExpression(baselineExperimentExpression.expressionLevel(), f.getId()));
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
