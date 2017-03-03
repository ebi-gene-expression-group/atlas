package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.collect.*;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
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

    public BaselineExperimentSearchResult buildProfilesForExperiments(List<Map<String, Object>> response, String defaultQueryFactorType) {

        return buildProfilesForExpressions(extractAverageExpressionLevel(response), defaultQueryFactorType);
    }

    @Deprecated //stop producing BaselineExperimentExpression outside this class, and remove me!
    public BaselineExperimentSearchResult buildProfilesForTissueExperiments(List<BaselineExperimentExpression> expressions) {
        return buildProfilesForExpressions(expressions, "ORGANISM_PART");

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

                double expression = BaselineExpressionLevelRounder.round(sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);
                BaselineExperimentExpression bslnExpression = BaselineExperimentExpression.create(experimentAccession, assayGroupId, expression);

                builder.add(bslnExpression);
            }
        }

        return builder.build();
    }


    BaselineExperimentSearchResult buildProfilesForExpressions(List<BaselineExperimentExpression> expressions,
                                                                  String defaultQueryFactorType) {

        ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice = filter(groupByExperimentSlice(expressions), defaultQueryFactorType);

        SortedSet<Factor> tissueFactorsAcrossAllExperiments = extractAllNonFilterFactors(expressionsByExperimentSlice);

        BaselineExperimentProfilesList profiles = new BaselineExperimentProfilesList();
        SortedSet<FactorAcrossExperiments> allFactorsAcrossAllExperiments = new TreeSet<>();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {

            BaselineExperiment experiment = experimentSlice.experiment();


            Set<Factor> factorDifference = Sets.difference(tissueFactorsAcrossAllExperiments, experimentSlice.nonFilterFactors());

            BaselineExperimentProfile profile = new BaselineExperimentProfile(experimentSlice);

            for (BaselineExperimentExpression baselineExpression : expressionsByExperimentSlice.get(experimentSlice)) {
                String assayGroupId = baselineExpression.assayGroupId();
                FactorAcrossExperiments f = new FactorAcrossExperiments(experiment.getExperimentalFactors().getFactorGroup(assayGroupId)
                        .factorOfType(defaultQueryFactorType));
                allFactorsAcrossAllExperiments.add(f);
                profile.add(f, new BaselineExpression(baselineExpression.expressionLevel(), f.getId()));
            }

            // For the nonFilterFactors which don't have expression, create new expression with NT level
            for (Factor factor : factorDifference) {
                FactorAcrossExperiments f = new FactorAcrossExperiments(factor);
                allFactorsAcrossAllExperiments.add(f);
                profile.add(f, new BaselineExpression("NT", f.getId()));
            }

            // For the expressed factors below cutoff
            for (Factor factor : tissueFactorsAcrossAllExperiments) {
                FactorAcrossExperiments f = new FactorAcrossExperiments(factor);
                allFactorsAcrossAllExperiments.add(f);
                if (profile.getExpression(f) == null) {
                    profile.add(f, new BaselineExpression("NA", f.getId()));
                }
            }

            profiles.add(profile);
        }

        Collections.sort(profiles);

        profiles.setTotalResultCount(profiles.size());
        return new BaselineExperimentSearchResult(profiles, ImmutableList.copyOf(allFactorsAcrossAllExperiments));
    }

    private static ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> filter(ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice, String defaultQueryFactorType) {
        ImmutableListMultimap.Builder<BaselineExperimentSlice, BaselineExperimentExpression> builder = ImmutableListMultimap.builder();

        for (Map.Entry<BaselineExperimentSlice, Collection<BaselineExperimentExpression>> baselineExperimentSliceCollectionEntry : expressionsByExperimentSlice.asMap().entrySet()) {
            BaselineExperiment experiment = baselineExperimentSliceCollectionEntry.getKey().experiment();
            if (experiment.getExperimentalFactors().getDefaultQueryFactorType().equalsIgnoreCase(defaultQueryFactorType)) {
                builder.putAll(baselineExperimentSliceCollectionEntry.getKey(), baselineExperimentSliceCollectionEntry.getValue());
            }
        }

        return builder.build();
    }

    private SortedSet<Factor> extractAllNonFilterFactors(ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice) {
        ImmutableSortedSet.Builder<Factor> factors = ImmutableSortedSet.naturalOrder();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {
            factors.addAll(experimentSlice.nonFilterFactors());
        }

        return factors.build();
    }


    private ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> groupByExperimentSlice(List<BaselineExperimentExpression> expressions) {
        Function<BaselineExperimentExpression, BaselineExperimentSlice> createExperimentSlice = new Function<BaselineExperimentExpression, BaselineExperimentSlice>() {
            public BaselineExperimentSlice apply(BaselineExperimentExpression input) {
                String experimentAccession = input.experimentAccession();
                String assayGroupId = input.assayGroupId();

                BaselineExperiment experiment = (BaselineExperiment) (experimentTrader.getPublicExperiment(experimentAccession));
                FactorGroup filterFactors = experiment.getExperimentalFactors().getNonDefaultFactors(assayGroupId);

                return BaselineExperimentSlice.create(experiment, filterFactors);
            }
        };

        return Multimaps.index(expressions, createExperimentSlice);
    }

}
