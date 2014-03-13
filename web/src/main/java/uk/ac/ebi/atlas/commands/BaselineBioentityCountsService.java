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

package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.dao.BaselineExperimentDao;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("request")
public class BaselineBioentityCountsService {

    private static final Logger LOGGER = Logger.getLogger(BaselineBioentityCountsService.class);

    private final BaselineExperimentsCache baselineExperimentsCache;

    private BaselineExperimentDao baselineExperimentDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    private SolrQueryService solrQueryService;

    private static final Map<String, BaselineExperimentResult> LIMITED_BY_EXPERIMENTS = Maps.newHashMap();

    static {
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-1733", new BaselineExperimentResult("E-MTAB-1733", "Twenty seven tissues", "Homo sapiens"));
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-599", new BaselineExperimentResult("E-MTAB-599", "Six tissues", "Mus musculus"));
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-2037", new BaselineExperimentResult("E-MTAB-2037", "Seven tissues", "Oryza sativa Japonica Group"));
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-2039", new BaselineExperimentResult("E-MTAB-2039", "Nine tissues", "Oryza sativa Japonica Group"));
    }


    @Inject
    public BaselineBioentityCountsService(BaselineExperimentDao baselineExperimentDao, BaselineConditionsSearchService baselineConditionsSearchService, SolrQueryService solrQueryService, BaselineExperimentsCache baselineExperimentsCache) {
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.baselineExperimentDao = baselineExperimentDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }


    public Set<BaselineExperimentResult> query(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {
        LOGGER.info(String.format("<query> geneQuery=%s, condition=%s", requestParameters.getGeneQuery(), requestParameters.getCondition()));
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        SortedSet<BaselineExperimentResult> results =
                Sets.newTreeSet(new Comparator<BaselineExperimentResult>() {
                    @Override
                    public int compare(BaselineExperimentResult count, BaselineExperimentResult otherCount) {
                        return count.getExperimentName().compareTo(otherCount.getExperimentName());
                    }
                });


        if (requestParameters.hasCondition()) {

            SetMultimap<String, String> assayGroupsPerExperiment = baselineConditionsSearchService.findAssayGroupsPerExperiment(requestParameters.getCondition());

            for (String experimentAccession : assayGroupsPerExperiment.keySet()) {

                if (LIMITED_BY_EXPERIMENTS.containsKey(experimentAccession)) {

                    Set<String> assayGroupIds = assayGroupsPerExperiment.get(experimentAccession);

                    String species = LIMITED_BY_EXPERIMENTS.get(experimentAccession).getSpecies();

                    if (isExperimentWithGenesExpressedAboveCutOff(requestParameters, experimentAccession, species, assayGroupIds)) {
                        BaselineExperimentResult result = LIMITED_BY_EXPERIMENTS.get(experimentAccession);

                        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

                        result.setAssayGroupsWithCondition(assayGroupIds, experiment);

                        results.add(result);
                    }

                }
            }

        } else if (requestParameters.hasGeneQuery()) {

            for (String experimentAccession : LIMITED_BY_EXPERIMENTS.keySet()) {

                GeneQueryResponse geneIds = solrQueryService.findGeneIdsOrSets(requestParameters.getGeneQuery(),
                        requestParameters.isExactMatch(),
                        LIMITED_BY_EXPERIMENTS.get(experimentAccession).getSpecies().toLowerCase(),
                        requestParameters.isGeneSetMatch());

                if (!geneIds.isEmpty() && baselineExperimentDao.isExperimentWithGenesExpressedAboveCutOff(experimentAccession, geneIds.getAllGeneIds())) {
                    results.add(LIMITED_BY_EXPERIMENTS.get(experimentAccession));
                }

            }
        }

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", results.size(), stopWatch.getTotalTimeSeconds()));

        return results;
    }

    private boolean isExperimentWithGenesExpressedAboveCutOff(GeneQuerySearchRequestParameters requestParameters, String experimentAccession, String species, Collection<String> assayGroups) {
        if (requestParameters.hasGeneQuery()) {
            GeneQueryResponse geneIds = solrQueryService.findGeneIdsOrSets(requestParameters.getGeneQuery(),
                    requestParameters.isExactMatch(),
                    species.toLowerCase(),
                    requestParameters.isGeneSetMatch());

            if (!geneIds.isEmpty() && baselineExperimentDao.isExperimentWithGenesExpressedAboveCutOff(experimentAccession, assayGroups, geneIds.getAllGeneIds())) {
                return true;
            }
        } else {

            if (baselineExperimentDao.isExperimentWithAnyGenesExpressedAboveCutOff(experimentAccession, assayGroups)) {
                return true;
            }
        }
        return false;
    }

}
