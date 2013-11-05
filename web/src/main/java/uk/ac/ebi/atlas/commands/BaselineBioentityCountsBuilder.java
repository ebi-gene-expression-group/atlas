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

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.BaselineExpressionDao;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;
import uk.ac.ebi.atlas.web.SearchRequest;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("request")
public class BaselineBioentityCountsBuilder {

    private BaselineExpressionDao baselineExpressionDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    private SolrQueryService solrQueryService;

    @Inject
    public BaselineBioentityCountsBuilder(BaselineExpressionDao baselineExpressionDao, BaselineConditionsSearchService baselineConditionsSearchService, SolrQueryService solrQueryService) {
        this.baselineExpressionDao = baselineExpressionDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }

    public Set<BaselineBioentitiesCount> build(String condition) {

        checkArgument(StringUtils.isNotBlank(condition), "Please specify a condition");

        Collection<IndexedAssayGroup> assayGroups = baselineConditionsSearchService.findAssayGroups(condition);

        if (assayGroups.isEmpty()){
            return Sets.newHashSet();
        }

        return baselineExpressionDao.getBioentitiesCounts(assayGroups);

    }

    public Set<BaselineBioentitiesCount> build(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {

        if (StringUtils.isNotBlank(requestParameters.getCondition())) {
            Collection<IndexedAssayGroup> assayGroups = baselineConditionsSearchService.findAssayGroups(requestParameters.getCondition());

            if (assayGroups.isEmpty()){
                return Sets.newHashSet();
            }

            return baselineExpressionDao.getBioentitiesCounts(assayGroups);
        }

        if(StringUtils.isNotBlank(requestParameters.getGeneQuery())) {

            // sort alphabetically by experiment name
            SortedSet<BaselineBioentitiesCount> counts =
                Sets.newTreeSet(new Comparator<BaselineBioentitiesCount>() {
                    @Override
                    public int compare(BaselineBioentitiesCount count, BaselineBioentitiesCount otherCount) {
                        return count.getExperimentName().compareTo(otherCount.getExperimentName());
                    }
                });

            // experiments checked for genes are currently hard-coded to only: E-MTAB-513, and E-MTAB-599
            BaselineBioentitiesCount eMtab513Count = baselineCountsInExperimentForGeneQuery(requestParameters, "Homo sapiens", "E-MTAB-513", "Illumina Body Map");
            if (eMtab513Count.getCount() > 0) {
                counts.add(eMtab513Count);
            }

            BaselineBioentitiesCount eMtab599Count = baselineCountsInExperimentForGeneQuery(requestParameters, "Mus musculus", "E-MTAB-599", "Six tissues");
            if (eMtab599Count.getCount() > 0) {
                counts.add(eMtab599Count);
            }

            return counts;

        }
        return null;
    }

    private BaselineBioentitiesCount baselineCountsInExperimentForGeneQuery(SearchRequest requestParameters, String species, String experimentAccession, String experimentName) throws GenesNotFoundException {
        GeneQueryResponse geneIds = solrQueryService.findGeneIdsOrSets(requestParameters.getGeneQuery(),
                requestParameters.isExactMatch(), species.toLowerCase(), requestParameters.isGeneSetMatch());

        // for performance reasons, don't get the count, just determine if the genes exist in the experiment
        int count = baselineExpressionDao.atLeastOneGeneIdExistsInExperiment(geneIds.getAllGeneIds(), experimentAccession) ? 1 : 0;

        BaselineBioentitiesCount bioentitiesCount = new BaselineBioentitiesCount(experimentName, species,
                experimentAccession, count);

        return bioentitiesCount;
    }

}
