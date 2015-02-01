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

    public BaselineExperimentSearchResult buildProfilesForTissueExperiments(List<RnaSeqBslnExpression> expressions) {
        return buildProfilesForExperiments(expressions, "ORGANISM_PART");

    }

    public BaselineExperimentSearchResult buildProfilesForExperiments(List<RnaSeqBslnExpression> expressions, String defaultQueryFactorType) {

        ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> expressionsByExperimentSlice = groupByExperimentSlice(expressions);

        ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> tissueExperimentsBySlice = filter(expressionsByExperimentSlice, defaultQueryFactorType);

        SortedSet<Factor> tissueFactorsAcrossAllExperiments = extractAllNonFilterFactors(tissueExperimentsBySlice);

        BaselineExperimentProfilesList profiles = createBaselineExperimentProfiles(tissueExperimentsBySlice, tissueFactorsAcrossAllExperiments, defaultQueryFactorType);

        return new BaselineExperimentSearchResult(profiles, tissueFactorsAcrossAllExperiments);
    }

    private static ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> filter(ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> expressionsByExperimentSlice, String defaultQueryFactorType) {
        ImmutableListMultimap.Builder<BaselineExperimentSlice, RnaSeqBslnExpression> builder = ImmutableListMultimap.builder();

        for (Map.Entry<BaselineExperimentSlice, Collection<RnaSeqBslnExpression>> baselineExperimentSliceCollectionEntry : expressionsByExperimentSlice.asMap().entrySet()) {
            BaselineExperiment experiment = baselineExperimentSliceCollectionEntry.getKey().experiment();
            if (experiment.getExperimentalFactors().getDefaultQueryFactorType().equals(defaultQueryFactorType)) {
                builder.putAll(baselineExperimentSliceCollectionEntry.getKey(), baselineExperimentSliceCollectionEntry.getValue());
            }
        }

        return builder.build();
    }

    private static BaselineExperimentProfilesList createBaselineExperimentProfiles(ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> expressionsByExperimentSlice, SortedSet<Factor> tissueFactorsAcrossAllExperiments, String defaultQueryFactorType) {
        BaselineExperimentProfilesList profiles = new BaselineExperimentProfilesList();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {

            BaselineExperiment experiment = experimentSlice.experiment();

            Set<Factor> factorDifference = Sets.difference(tissueFactorsAcrossAllExperiments, experimentSlice.nonFilterFactors());

            BaselineExperimentProfile profile = new BaselineExperimentProfile(experimentSlice);

            for (RnaSeqBslnExpression rnaSeqBslnExpression : expressionsByExperimentSlice.get(experimentSlice)) {
                BaselineExpression expression = createBaselineExpression(experiment, rnaSeqBslnExpression);
                //check expression level string if the factor
                profile.add(defaultQueryFactorType, expression);
            }

            //For the nonFilterFactors which don't have expression, create new expression with NT level
            for (Factor factor : factorDifference) {
                FactorGroup factorGroup = new FactorSet(factor);
                BaselineExpression baselineExpression = new BaselineExpression("NT", factorGroup);
                profile.add(defaultQueryFactorType, baselineExpression);
            }

            profiles.add(profile);
        }

        Collections.sort(profiles);

        profiles.setTotalResultCount(profiles.size());
        return profiles;
    }

    private SortedSet<Factor> extractAllNonFilterFactors(ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> expressionsByExperimentSlice) {
        ImmutableSortedSet.Builder<Factor> factors = ImmutableSortedSet.naturalOrder();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {
            factors.addAll(experimentSlice.nonFilterFactors());
        }

        return factors.build();
    }


    private ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> groupByExperimentSlice(List<RnaSeqBslnExpression> expressions) {
        Function<RnaSeqBslnExpression, BaselineExperimentSlice> createExperimentSlice = new Function<RnaSeqBslnExpression, BaselineExperimentSlice>() {
            public BaselineExperimentSlice apply(RnaSeqBslnExpression input) {
                String experimentAccession = input.experimentAccession();
                String assayGroupId = input.assayGroupId();

                BaselineExperiment experiment = (BaselineExperiment) (experimentTrader.getPublicExperiment(experimentAccession));
                FactorGroup filterFactors = experiment.getExperimentalFactors().getNonDefaultFactors(assayGroupId);

                return BaselineExperimentSlice.create(experiment, filterFactors);
            }
        };

        return Multimaps.index(expressions, createExperimentSlice);
    }

    private static BaselineExpression createBaselineExpression(BaselineExperiment experiment, RnaSeqBslnExpression rnaSeqBslnExpression) {
        double level = rnaSeqBslnExpression.expressionLevel();

        String assayGroupId = rnaSeqBslnExpression.assayGroupId();
        FactorGroup factorGroup = experiment.getExperimentalFactors().getFactorGroupByAssayGroupId(assayGroupId);

        return new BaselineExpression(level, factorGroup);
    }


}
