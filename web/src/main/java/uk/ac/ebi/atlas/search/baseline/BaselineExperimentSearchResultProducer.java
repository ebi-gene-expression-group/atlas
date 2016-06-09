package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
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

    public BaselineExperimentSearchResult buildProfilesForTissueExperiments(List<BaselineExperimentExpression> expressions) {
        return buildProfilesForExperiments(expressions, "ORGANISM_PART");

    }

    public BaselineExperimentSearchResult buildProfilesForExperiments(List<BaselineExperimentExpression> expressions, String defaultQueryFactorType) {

        ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice = groupByExperimentSlice(expressions);
        ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> tissueExperimentsBySlice = filter(expressionsByExperimentSlice, defaultQueryFactorType);

        SortedSet<Factor> tissueFactorsAcrossAllExperiments = extractAllNonFilterFactors(tissueExperimentsBySlice);
        BaselineExperimentProfilesList profiles = createBaselineExperimentProfiles(tissueExperimentsBySlice, tissueFactorsAcrossAllExperiments, defaultQueryFactorType);

        return new BaselineExperimentSearchResult(profiles, tissueFactorsAcrossAllExperiments);
    }

    private static ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> filter(ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice, String defaultQueryFactorType) {
        ImmutableListMultimap.Builder<BaselineExperimentSlice, BaselineExperimentExpression> builder = ImmutableListMultimap.builder();

        for (Map.Entry<BaselineExperimentSlice, Collection<BaselineExperimentExpression>> baselineExperimentSliceCollectionEntry : expressionsByExperimentSlice.asMap().entrySet()) {
            BaselineExperiment experiment = baselineExperimentSliceCollectionEntry.getKey().experiment();
            if (experiment.getExperimentalFactors().getDefaultQueryFactorType().equals(defaultQueryFactorType)) {
                builder.putAll(baselineExperimentSliceCollectionEntry.getKey(), baselineExperimentSliceCollectionEntry.getValue());
            }
        }

        return builder.build();
    }

    private static BaselineExperimentProfilesList createBaselineExperimentProfiles(ImmutableListMultimap<BaselineExperimentSlice, BaselineExperimentExpression> expressionsByExperimentSlice, SortedSet<Factor> tissueFactorsAcrossAllExperiments, String defaultQueryFactorType) {
        BaselineExperimentProfilesList profiles = new BaselineExperimentProfilesList();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {

            BaselineExperiment experiment = experimentSlice.experiment();

            Set<Factor> factorDifference = Sets.difference(tissueFactorsAcrossAllExperiments, experimentSlice.nonFilterFactors());

            BaselineExperimentProfile profile = new BaselineExperimentProfile(experimentSlice);

            for (BaselineExperimentExpression baselineExpression : expressionsByExperimentSlice.get(experimentSlice)) {
                BaselineExpression expression = createBaselineExpression(experiment, baselineExpression);
                profile.add(defaultQueryFactorType, expression);
            }

            // For the nonFilterFactors which don't have expression, create new expression with NT level
            for (Factor factor : factorDifference) {
                FactorGroup factorGroup = new FactorSet(factor);
                BaselineExpression baselineExpression = new BaselineExpression("NT", factorGroup);
                profile.add(defaultQueryFactorType, baselineExpression);
            }

            // For the expressed factors below cutoff
            for (Factor factor : tissueFactorsAcrossAllExperiments) {
                if (profile.getExpression(factor) == null) {
                    FactorGroup factorGroup = new FactorSet(factor);
                    BaselineExpression baselineExpression = new BaselineExpression("NA", factorGroup);
                    profile.add(defaultQueryFactorType, baselineExpression);
                }
            }

            profiles.add(profile);
        }

        Collections.sort(profiles);

        profiles.setTotalResultCount(profiles.size());
        return profiles;
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

    private static uk.ac.ebi.atlas.model.baseline.BaselineExpression createBaselineExpression(BaselineExperiment experiment, BaselineExperimentExpression baselineExpression) {
        double level = baselineExpression.expressionLevel();

        String assayGroupId = baselineExpression.assayGroupId();
        FactorGroup factorGroup = experiment.getExperimentalFactors().getFactorGroup(assayGroupId);

        return new uk.ac.ebi.atlas.model.baseline.BaselineExpression(level, factorGroup);
    }


}
