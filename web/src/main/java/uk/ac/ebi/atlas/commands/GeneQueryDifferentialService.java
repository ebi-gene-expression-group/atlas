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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.DiffExpressionDao;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.utils.spring.servlet.view.CountingVisitor;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class GeneQueryDifferentialService {

    private DiffExpressionDao diffExpressionDao;
    private DifferentialConditionsSearchService differentialConditionsSearchService;
    private SolrQueryService solrQueryService;

    @Inject
    public GeneQueryDifferentialService(DiffExpressionDao diffExpressionDao,
                                        DifferentialConditionsSearchService differentialConditionsSearchService,
                                        SolrQueryService solrQueryService) {
        this.diffExpressionDao = diffExpressionDao;
        this.differentialConditionsSearchService = differentialConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }


    public int forEachExpression(GeneQuerySearchRequestParameters requestParameters, Visitor<DifferentialBioentityExpression> visitor) {
        try {
            Collection<IndexedAssayGroup> contrasts = findContrasts(requestParameters);
            Collection<String> geneIds = findGeneIdsOrSets(requestParameters);

            CountingVisitor<DifferentialBioentityExpression> counter = new CountingVisitor<>(visitor);

            diffExpressionDao.foreachExpression(contrasts, geneIds, counter);

            return counter.getCount();

        } catch (NoResultsException e) {
            // no contrasts when condition specified, or no genes when gene ids specified,
            // so do nothing
            return 0;
        }
    }

    public List<DifferentialBioentityExpression> queryWithoutCount(String geneId) {
        return diffExpressionDao.getTopExpressions(null, Collections.singleton(geneId));
    }

    public DifferentialBioentityExpressions query(Set<String> geneIdentifiers) {

        if (CollectionUtils.isNotEmpty(geneIdentifiers)) {

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getTopExpressions(null, geneIdentifiers);
            int resultCount = diffExpressionDao.getResultCount(null, geneIdentifiers);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }
        return new DifferentialBioentityExpressions();
    }


    public DifferentialBioentityExpressions query(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {
        try {

            Collection<IndexedAssayGroup> contrasts = findContrasts(requestParameters);
            Collection<String> geneIds = findGeneIdsOrSets(requestParameters);

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getTopExpressions(contrasts, geneIds);
            int resultCount = diffExpressionDao.getResultCount(contrasts, geneIds);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        } catch (NoResultsException e) {
            // no contrasts when condition specified, or no genes when gene ids specified,
            // so return empty results
            return new DifferentialBioentityExpressions();
        }
    }

    public Collection<IndexedAssayGroup> findContrasts(GeneQuerySearchRequestParameters requestParameters) throws NoResultsException {
        Collection<IndexedAssayGroup> contrasts = null;
        String condition = requestParameters.getCondition();
        if (StringUtils.isNotBlank(condition)) {

            contrasts = differentialConditionsSearchService.findContrasts(condition);

            if (contrasts.isEmpty()) {
                throw new NoResultsException();
            }

        }
        return contrasts;
    }

    public Collection<String> findGeneIdsOrSets(GeneQuerySearchRequestParameters requestParameters) throws NoResultsException {
        Collection<String> geneIds = null;
        String geneQuery = requestParameters.getGeneQuery();
        if (StringUtils.isNotBlank(geneQuery)) {

            // search across any species
            String species = "";

            //resolve any gene keywords to identifiers
            GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                    requestParameters.isExactMatch(),
                    species,
                    requestParameters.isGeneSetMatch());

            geneIds = geneQueryResponse.getAllGeneIds();

            Set<String> matureRNAIds = solrQueryService.findMatureRNAIds(geneQuery);
            geneIds.addAll(matureRNAIds);

            if (geneIds.isEmpty()) {
                throw new NoResultsException();
            }

        }
        return geneIds;
    }

}
