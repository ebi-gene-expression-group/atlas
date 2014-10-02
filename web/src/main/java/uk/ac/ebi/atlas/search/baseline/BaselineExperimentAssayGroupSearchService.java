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

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Named
@Scope("request")
//TODO: merge this class with BaselineExperimentProfileSearchService
public class BaselineExperimentAssayGroupSearchService {

    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentAssayGroupSearchService.class);

    private final BaselineExperimentsCache baselineExperimentsCache;

    private BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    private SolrQueryService solrQueryService;

    @Inject
    public BaselineExperimentAssayGroupSearchService(BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao, BaselineConditionsSearchService baselineConditionsSearchService, SolrQueryService solrQueryService, BaselineExperimentsCache baselineExperimentsCache) {
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.baselineExperimentAssayGroupsDao = baselineExperimentAssayGroupsDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }

    boolean isEmpty(Optional<? extends Collection<?>> coll) {
        return (!coll.isPresent() || coll.get().isEmpty());
    }

    public Set<BaselineExperimentAssayGroup> query(String geneQuery, String condition, String specie, boolean isExactMatch) {
        LOGGER.info(String.format("<query> geneQuery=%s, condition=%s", geneQuery, condition));
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        Optional<ImmutableSet<IndexedAssayGroup>> indexedAssayGroups = fetchAssayGroupsForCondition(condition);

        String species = StringUtils.isNotBlank(specie) ? specie : "";

        //TODO: move outside into caller, because this is called twice, here and in DiffAnalyticsSearchService
        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

        SetMultimap<String, String> assayGroupsWithExpressionByExperiment = baselineExperimentAssayGroupsDao.fetchExperimentAssayGroupsWithNonSpecificExpression(indexedAssayGroups, geneIds);

        boolean conditionSearch = !isEmpty(indexedAssayGroups);

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = Sets.newTreeSet();
        if(conditionSearch ||
                StringUtils.isNotEmpty(geneQuery) && StringUtils.isEmpty(condition)) {

            baselineExperimentAssayGroups = buildResults(assayGroupsWithExpressionByExperiment, conditionSearch, species);
        }

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", baselineExperimentAssayGroups.size(), stopWatch.getTotalTimeSeconds()));

        return baselineExperimentAssayGroups;
    }

    SortedSet<BaselineExperimentAssayGroup> buildResults(SetMultimap<String, String> assayGroupsWithExpressionByExperiment, boolean conditionSearch, String selectedSpecie) {
        SortedSet<BaselineExperimentAssayGroup> results = Sets.newTreeSet();

        for (Map.Entry<String, Collection<String>> exprAssayGroups : assayGroupsWithExpressionByExperiment.asMap().entrySet()) {

            String experimentAccession = exprAssayGroups.getKey();
            Collection<String> assayGroupIds = exprAssayGroups.getValue();

            BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

            Multimap<FactorGroup, String> assayGroupIdsByFilterFactors = experiment.getExperimentalFactors().groupAssayGroupIdsByNonDefaultFactor(assayGroupIds);

            for (Map.Entry<FactorGroup, Collection<String>> assayGroupIdsAndFilterFactor : assayGroupIdsByFilterFactors.asMap().entrySet()) {
                String species = experiment.getOrganisms().size() > 1 ? "Multi-species" : experiment.getFirstOrganism();
                //If the search has a selected specie, we need to find the experiments that match the same specie
                if (StringUtils.isBlank(selectedSpecie) || (StringUtils.isNotBlank(selectedSpecie) && species.toLowerCase().equals(selectedSpecie)) ||
                        (StringUtils.isNotBlank(selectedSpecie) && species.equals("Multi-species") && !assayGroupIdsAndFilterFactor.getKey().isEmpty()
                                && assayGroupIdsAndFilterFactor.getKey().getFactorByType("ORGANISM").getValue().toLowerCase().equals(selectedSpecie))) {

                    BaselineExperimentAssayGroup result = new BaselineExperimentAssayGroup(experiment.getAccession(), experiment.getDisplayName(), species, experiment.getExperimentalFactors().getDefaultQueryFactorType());
                    result.setFilterFactors(assayGroupIdsAndFilterFactor.getKey());
                    if (conditionSearch) {
                        result.setAssayGroupsWithCondition(ImmutableSet.copyOf(assayGroupIdsAndFilterFactor.getValue()), experiment);
                    }
                    results.add(result);
                }
            }

        }
        return results;
    }

    private Optional<ImmutableSet<IndexedAssayGroup>> fetchAssayGroupsForCondition(String condition) {
        if (StringUtils.isBlank(condition)) {
            return Optional.absent();
        }

        SetMultimap<String, String> assayGroupsPerExperiment = baselineConditionsSearchService.findAssayGroupsPerExperiment(condition);

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = createSetOfIndexedAssayGroups(assayGroupsPerExperiment);

        return Optional.of(indexedAssayGroups);
    }

    static ImmutableSet<IndexedAssayGroup> createSetOfIndexedAssayGroups(SetMultimap<String, String> assayGroupsPerExperiment) {
        ImmutableSet.Builder<IndexedAssayGroup> builder = ImmutableSet.builder();

        for (Map.Entry<String, String> entry : assayGroupsPerExperiment.entries()) {
            builder.add(new IndexedAssayGroup(entry.getKey(), entry.getValue()));
        }

        return builder.build();
    }

}
