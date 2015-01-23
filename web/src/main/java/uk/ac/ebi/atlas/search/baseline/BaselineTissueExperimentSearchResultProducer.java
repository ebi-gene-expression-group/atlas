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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Named

public class BaselineTissueExperimentSearchResultProducer {

    private final ExperimentTrader experimentTrader;

    @Inject
    public BaselineTissueExperimentSearchResultProducer(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public BaselineTissueExperimentSearchResult buildProfilesForTissueExperiments(List<RnaSeqBslnExpression> expressions) {

        ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> expressionsByExperimentSlice = groupByExperimentSlice(expressions);

        BaselineExperimentProfilesList profiles = new BaselineExperimentProfilesList();

        ImmutableSortedSet.Builder<Factor> factors = ImmutableSortedSet.naturalOrder();

        int count = 0;

        for(BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {
            if(experimentSlice.isTissueExperiment()) {
                factors.addAll(experimentSlice.nonFilterFactors());
            }
        }

        SortedSet<Factor> tissueFactorsAcrossAllExperiments = factors.build();

        for (BaselineExperimentSlice experimentSlice : expressionsByExperimentSlice.keySet()) {

            if (experimentSlice.isTissueExperiment()) {
                BaselineExperiment experiment = experimentSlice.experiment();

                Set<Factor> factorDifference = Sets.difference(tissueFactorsAcrossAllExperiments, experimentSlice.nonFilterFactors());

                BaselineExperimentProfile profile = new BaselineExperimentProfile(experimentSlice);

                for (RnaSeqBslnExpression rnaSeqBslnExpression : expressionsByExperimentSlice.get(experimentSlice)) {
                    BaselineExpression expression = createBaselineExpression(experiment, rnaSeqBslnExpression);
                    //check expression level string if the factor
                    profile.add("ORGANISM_PART", expression);
                }

                //For the nonFilterFactors which don't have expression, create new expression with NT level
                for (Factor factor : factorDifference) {
                    FactorGroup factorGroup = new FactorSet(factor);
                    BaselineExpression baselineExpression = new BaselineExpression("NT", factorGroup);
                    profile.add("ORGANISM_PART", baselineExpression);
                }

                count++;

                profiles.add(profile);
            }
        }

        Collections.sort(profiles);

        profiles.setTotalResultCount(count);

        return new BaselineTissueExperimentSearchResult(profiles, tissueFactorsAcrossAllExperiments);
    }


    private ImmutableListMultimap<BaselineExperimentSlice, RnaSeqBslnExpression> groupByExperimentSlice(List<RnaSeqBslnExpression> expressions) {
        Function<RnaSeqBslnExpression, BaselineExperimentSlice> createExperimentSlice = new Function<RnaSeqBslnExpression, BaselineExperimentSlice>() {
            public BaselineExperimentSlice apply(RnaSeqBslnExpression input) {
                String experimentAccession = input.experimentAccession();
                String assayGroupId = input.assayGroupId();

                BaselineExperiment experiment = (BaselineExperiment)(experimentTrader.getPublicExperiment(experimentAccession));
                FactorGroup filterFactors = experiment.getExperimentalFactors().getNonDefaultFactors(assayGroupId);

                return BaselineExperimentSlice.create(experiment, filterFactors);
            }
        };

        return Multimaps.index(expressions, createExperimentSlice);
    }

    private BaselineExpression createBaselineExpression(BaselineExperiment experiment, RnaSeqBslnExpression rnaSeqBslnExpression) {
        double level = rnaSeqBslnExpression.expressionLevel();

        String assayGroupId = rnaSeqBslnExpression.assayGroupId();
        FactorGroup factorGroup = experiment.getExperimentalFactors().getFactorGroup(assayGroupId);

        return new BaselineExpression(level, factorGroup);
    }





}
