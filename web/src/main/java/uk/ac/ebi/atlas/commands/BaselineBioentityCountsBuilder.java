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
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.BaselineExperimentDao;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Named
@Scope("request")
public class BaselineBioentityCountsBuilder {

    private BaselineExperimentDao baselineExperimentDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    private SolrQueryService solrQueryService;

    private static final Map<String, BaselineExperimentResult> LIMITED_BY_EXPERIMENTS = Maps.newHashMap();

    static {
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-513", new BaselineExperimentResult("E-MTAB-513", "Illumina Body Map", "Homo sapiens"));
        LIMITED_BY_EXPERIMENTS.put("E-MTAB-599", new BaselineExperimentResult("E-MTAB-599", "Six tissues", "Mus musculus"));
    }

    @Inject
    public BaselineBioentityCountsBuilder(BaselineExperimentDao baselineExperimentDao, BaselineConditionsSearchService baselineConditionsSearchService, SolrQueryService solrQueryService) {
        this.baselineExperimentDao = baselineExperimentDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }


    public Set<BaselineExperimentResult> build(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {

        SortedSet<BaselineExperimentResult> result =
                Sets.newTreeSet(new Comparator<BaselineExperimentResult>() {
                    @Override
                    public int compare(BaselineExperimentResult count, BaselineExperimentResult otherCount) {
                        return count.getExperimentName().compareTo(otherCount.getExperimentName());
                    }
                });


        if (requestParameters.hasCondition()) {
            Multimap<String, String> assayGroupsPerExperiment = baselineConditionsSearchService.findAssayGroupsPerExperiment(requestParameters.getCondition());

            for (String experimentAccession : assayGroupsPerExperiment.keySet()) {

                if (LIMITED_BY_EXPERIMENTS.containsKey(experimentAccession)) {

                    if (requestParameters.hasGeneQuery()) {
                        GeneQueryResponse geneIds = solrQueryService.findGeneIdsOrSets(requestParameters.getGeneQuery(),
                                requestParameters.isExactMatch(),
                                LIMITED_BY_EXPERIMENTS.get(experimentAccession).getSpecies().toLowerCase(),
                                requestParameters.isGeneSetMatch());

                        if (!geneIds.isEmpty() && baselineExperimentDao.isExperimentWithConditionsAndGenes(experimentAccession, assayGroupsPerExperiment.get(experimentAccession), geneIds.getAllGeneIds())) {
                            result.add(LIMITED_BY_EXPERIMENTS.get(experimentAccession));
                        }
                    } else {
                        if (baselineExperimentDao.isExperimentWithCondition(experimentAccession, assayGroupsPerExperiment.get(experimentAccession))) {
                            result.add(LIMITED_BY_EXPERIMENTS.get(experimentAccession));
                        }
                    }

                }
            }

        } else if (requestParameters.hasGeneQuery()) {
            for (String experimentAccession : LIMITED_BY_EXPERIMENTS.keySet()) {
                GeneQueryResponse geneIds = solrQueryService.findGeneIdsOrSets(requestParameters.getGeneQuery(),
                        requestParameters.isExactMatch(),
                        LIMITED_BY_EXPERIMENTS.get(experimentAccession).getSpecies().toLowerCase(),
                        requestParameters.isGeneSetMatch());

                if (!geneIds.isEmpty() && baselineExperimentDao.isExperimentWithGenes(experimentAccession, geneIds.getAllGeneIds())) {
                    result.add(LIMITED_BY_EXPERIMENTS.get(experimentAccession));
                }
            }
        }


        return result;
    }

}
