package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
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

        return buildProfilesForExpressions(extractAverageExpressionLevel(response), factorType);

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

    private Map<String, FactorAcrossExperiments> factorsPerColumnId(BaselineExperiment experiment, String factorType) {
        Map<String, FactorAcrossExperiments> result = new HashMap<>();
        for(AssayGroup assayGroup: experiment.getDataColumnDescriptors()) {
            result.put(
                    assayGroup.getId(),
                    new FactorAcrossExperiments(experiment.getFactors(assayGroup).factorOfType(factorType)));
        }
        return result;
    }


    BaselineExperimentSearchResult buildProfilesForExpressions(List<BaselineExperimentExpression> expressions,
                                                               String factorType) {


        BaselineExperimentProfilesList resultRows = new BaselineExperimentProfilesList();
        SortedSet<FactorAcrossExperiments> resultHeaders = new TreeSet<>();

        Multimap<String, BaselineExperimentExpression> expressionsPerExperiment =
                Multimaps.index(expressions, new Function<BaselineExperimentExpression, String>() {
                    @Override
                    public String apply(BaselineExperimentExpression baselineExperimentExpression) {
                        return baselineExperimentExpression.experimentAccession();
                    }
                });

        for(String experimentAccession: expressionsPerExperiment.keySet()) {
            BaselineExperiment experiment =
                    (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);
            Map<String, FactorAcrossExperiments> columnIdsByFactorValue = factorsPerColumnId(experiment, factorType);

            Multimap<FactorGroup, BaselineExperimentExpression>
                    rowsWithExpressionAboveCutoffForThisExperiment = LinkedListMultimap.create();

            for(BaselineExperimentExpression baselineExperimentExpression : expressionsPerExperiment.get(experimentAccession)) {
                AssayGroup assayGroup = experiment.getDataColumnDescriptor(baselineExperimentExpression.assayGroupId());

                rowsWithExpressionAboveCutoffForThisExperiment.put(
                        experiment.getFactors(assayGroup).withoutTypes(ImmutableList.of(factorType)),
                        baselineExperimentExpression);
            }


            Multimap<FactorGroup, BaselineExperimentExpression> rowsForThisExperimentWithAllExpressions =
                    addExpressionsBelowCutOffToRows(rowsWithExpressionAboveCutoffForThisExperiment, factorType, experiment);


            Set<String> commonFactorTypes = RichFactorGroup.typesWithCommonValues(rowsForThisExperimentWithAllExpressions.keySet());

            for(Map.Entry<FactorGroup, Collection<BaselineExperimentExpression>> e:
                    rowsForThisExperimentWithAllExpressions.asMap().entrySet()) {

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

    private Multimap<FactorGroup, BaselineExperimentExpression> addExpressionsBelowCutOffToRows(
            Multimap<FactorGroup, BaselineExperimentExpression> rowsWithExpressionsAboveCutoff,
            String factorType, BaselineExperiment experiment) {

        Multimap<FactorGroup, BaselineExperimentExpression> rowsWithAllExpressions = LinkedListMultimap.create();

        for (Map.Entry<FactorGroup, Collection<BaselineExperimentExpression>> e: rowsWithExpressionsAboveCutoff.asMap().entrySet()) {

            ImmutableList.Builder<BaselineExperimentExpression> belowCutoffExpressions = ImmutableList.builder();
            for (AssayGroup assayGroup : allAssayGroupsBut(experiment, e.getValue())) {
                if (e.getKey().equals(experiment.getFactors(assayGroup).withoutTypes(ImmutableList.of(factorType)))) {
                    belowCutoffExpressions.add(BaselineExperimentExpression.create(experiment.getAccession(), assayGroup.getId(), 0.0D));
                }
            }

            rowsWithAllExpressions.putAll(e.getKey(), e.getValue());
            rowsWithAllExpressions.putAll(e.getKey(), belowCutoffExpressions.build());
        }

        return rowsWithAllExpressions;
    }

    private Set<AssayGroup> allAssayGroupsBut(BaselineExperiment experiment,
                                              Collection<BaselineExperimentExpression> baselineExperimentExpressions) {

        ImmutableSet<AssayGroup> allAssayGroups = ImmutableSet.copyOf(experiment.getDataColumnDescriptors());

        ImmutableSet.Builder<AssayGroup>  assayGroupsInExpressions = ImmutableSet.builder();
        for (BaselineExperimentExpression baselineExperimentExpression : baselineExperimentExpressions) {
            assayGroupsInExpressions.add(experiment.getDataColumnDescriptor(baselineExperimentExpression.assayGroupId()));
        }

        return Sets.difference(allAssayGroups, assayGroupsInExpressions.build());
    }

}
