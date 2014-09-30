/*
* Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*
* For further details of the Gene Expression Atlas project, including source code,
* downloads and documentation, please see:
*
* http://gxa.github.com/gxa
*/

package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("request")
public class BaselineExperimentProfileSearchService {

    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentProfileSearchService.class);

    private RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao;

    private SolrQueryService solrQueryService;

    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    public BaselineExperimentProfileSearchService(RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao, SolrQueryService solrQueryService, BaselineExperimentsCache baselineExperimentsCache) {
        this.rnaSeqBslnExpressionDao = rnaSeqBslnExpressionDao;
        this.solrQueryService = solrQueryService;
        this.baselineExperimentsCache = baselineExperimentsCache;
    }

    boolean isEmpty(Optional<? extends Collection<?>> coll) {
        return (!coll.isPresent() || coll.get().isEmpty());
    }

    public BaselineTissueExperimentSearchResult query(String geneQuery, String species, boolean isExactMatch)  {
        LOGGER.info(String.format("<query> geneQuery=%s", geneQuery));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        //TODO: here or by caller?
        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

        BaselineTissueExperimentSearchResult result = fetchTissueExperimentProfiles(geneIds);

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", result.experimentProfiles.size(), stopWatch.getTotalTimeSeconds()));

        return result;
    }

    BaselineTissueExperimentSearchResult fetchTissueExperimentProfiles(Optional<? extends Set<String>> geneIds) {

        if (isEmpty(geneIds)) {
            return new BaselineTissueExperimentSearchResult();
        }

        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchAverageExpressionByExperimentAssayGroup(geneIds.get());

        return buildProfilesForTissueExperiments(expressions);
    }

    BaselineTissueExperimentSearchResult buildProfilesForTissueExperiments(List<RnaSeqBslnExpression> expressions) {

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

                BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
                FactorGroup filterFactors = experiment.getExperimentalFactors().getNonDefaultFactors(assayGroupId);

                return BaselineExperimentSlice.create(experiment, filterFactors);
            }
        };

        return Multimaps.index(expressions, createExperimentSlice);
    }

    private BaselineExpression createBaselineExpression(BaselineExperiment experiment, RnaSeqBslnExpression rnaSeqBslnExpression) {
        double level = rnaSeqBslnExpression.expressionLevel();

        String assayGroupId = rnaSeqBslnExpression.assayGroupId();
        FactorGroup factorGroup = experiment.getExperimentalFactors().getFactorGroupByAssayGroupId(assayGroupId);

        return new BaselineExpression(level, factorGroup);
    }


}
