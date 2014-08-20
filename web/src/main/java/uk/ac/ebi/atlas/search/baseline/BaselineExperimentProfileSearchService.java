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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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

    public BaselineTissueExperimentSearchResult query(String geneQuery, String species, boolean isExactMatch) throws GenesNotFoundException {
        LOGGER.info(String.format("<query> geneQuery=%s", geneQuery));

        checkArgument(StringUtils.isNotBlank(species), "Species must be specified");

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

        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchNonSpecificExpression(geneIds.get());

        return buildProfilesForTissueExperiments(expressions);
    }

    BaselineTissueExperimentSearchResult buildProfilesForTissueExperiments(List<RnaSeqBslnExpression> expressions) {

        ImmutableListMultimap<String, RnaSeqBslnExpression> expressionsByExperiment = groupByExperimentAccession(expressions);

        BaselineProfilesList profiles = new BaselineProfilesList();

        ImmutableSortedSet.Builder<Factor> factors = ImmutableSortedSet.naturalOrder();

        for (String experimentAccession : expressionsByExperiment.keySet()) {

            BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

            if (experiment.isTissueExperiment()) {
                factors.addAll(experiment.getExperimentalFactors().getFactorsByType("ORGANISM_PART"));

                BaselineProfile profile = new BaselineProfile(experimentAccession, experiment.getDisplayName());

                for (RnaSeqBslnExpression rnaSeqBslnExpression : expressionsByExperiment.get(experimentAccession)) {
                    BaselineExpression expression = createBaselineExpression(experiment, rnaSeqBslnExpression);
                    profile.add("ORGANISM_PART", expression);
                }

                profiles.add(profile);
            }
        }

        return new BaselineTissueExperimentSearchResult(profiles, factors.build());
    }


    private ImmutableListMultimap<String, RnaSeqBslnExpression> groupByExperimentAccession(List<RnaSeqBslnExpression> expressions) {
        Function<RnaSeqBslnExpression, String> getExperimentAccession = new Function<RnaSeqBslnExpression, String>() {
            public String apply(RnaSeqBslnExpression input) {
                return input.getExperimentAccession();
            }
        };

        return Multimaps.index(expressions, getExperimentAccession);
    }

    private BaselineExpression createBaselineExpression(BaselineExperiment experiment, RnaSeqBslnExpression rnaSeqBslnExpression) {
        double level = rnaSeqBslnExpression.getExpressionLevel();

        String assayGroupId = rnaSeqBslnExpression.getAssayGroupId();
        FactorGroup factorGroup = experiment.getExperimentalFactors().getFactorGroupByAssayGroupId(assayGroupId);

        return new BaselineExpression(level, factorGroup);
    }


}
