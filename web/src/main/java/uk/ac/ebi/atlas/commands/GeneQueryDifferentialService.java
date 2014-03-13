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

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.dao.diffexpression.DiffExpressionDao;
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
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class GeneQueryDifferentialService {

    private static final Logger LOGGER = Logger.getLogger(GeneQueryDifferentialService.class);

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

        Optional<Collection<IndexedAssayGroup>> contrastsResult = findContrasts(requestParameters);
        Optional<Collection<String>> geneIdsResult = expandGeneQueryIntoGeneIds(requestParameters);

        if (geneIdsResult.isPresent() && geneIdsResult.get().isEmpty()
                 || contrastsResult.isPresent() && contrastsResult.get().isEmpty()) {
             // no contrasts when condition specified, or no genes when gene ids specified,
             // so return empty results
             return 0;
         }

        CountingVisitor<DifferentialBioentityExpression> counter = new CountingVisitor<>(visitor);

        diffExpressionDao.foreachExpression(contrastsResult, geneIdsResult, counter);

        return counter.getCount();

    }

    public List<DifferentialBioentityExpression> queryWithoutCount(String geneId) {
        Collection<String> geneIds = Lists.newArrayList(geneId);
        return diffExpressionDao.getTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.of(geneIds));
    }

    public DifferentialBioentityExpressions query(Collection<String> geneIdentifiers) {

        if (CollectionUtils.isNotEmpty(geneIdentifiers)) {

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(),
                    Optional.of(geneIdentifiers));

            int resultCount = diffExpressionDao.getResultCount(Optional.<Collection<IndexedAssayGroup>>absent(),
                                Optional.of(geneIdentifiers));

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }
        return new DifferentialBioentityExpressions();
    }


    public DifferentialBioentityExpressions query(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {


        Optional<Collection<IndexedAssayGroup>> contrastsResult = findContrasts(requestParameters);
        Optional<Collection<String>> geneIdsResult = expandGeneQueryIntoGeneIds(requestParameters);


        if (geneIdsResult.isPresent() && geneIdsResult.get().isEmpty()
                || contrastsResult.isPresent() && contrastsResult.get().isEmpty()) {
            // no contrasts when condition specified, or no genes when gene ids specified,
            // so return empty results
            return new DifferentialBioentityExpressions();
        }

        List<DifferentialBioentityExpression> expressions = diffExpressionDao.getTopExpressions(contrastsResult, geneIdsResult);
        int resultCount = diffExpressionDao.getResultCount(contrastsResult, geneIdsResult);

        return new DifferentialBioentityExpressions(expressions, resultCount);


    }

    public Optional<Collection<IndexedAssayGroup>> findContrasts(GeneQuerySearchRequestParameters requestParameters) {
        if (!requestParameters.hasCondition()) {
            return Optional.absent();
        }

        String condition = requestParameters.getCondition();

        Collection<IndexedAssayGroup> contrasts = differentialConditionsSearchService.findContrasts(condition);

        return Optional.of(contrasts);
    }

    public Optional<Collection<String>> expandGeneQueryIntoGeneIds(GeneQuerySearchRequestParameters requestParameters) {
        if (!requestParameters.hasGeneQuery()) {
            return Optional.absent();
        }

        LOGGER.info(String.format("<expandGeneQueryIntoGeneIds> geneQuery=" + requestParameters.getGeneQuery()));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        String geneQuery = requestParameters.getGeneQuery();

        // search across any species
        String species = "";

        //resolve any gene keywords to identifiers
        GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                requestParameters.isExactMatch(),
                species,
                requestParameters.isGeneSetMatch());

        Collection<String> geneIds = geneQueryResponse.getAllGeneIds();

        Set<String> matureRNAIds = solrQueryService.findMatureRNAIds(geneQuery);
        geneIds.addAll(matureRNAIds);

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", geneIds.size(), stopWatch.getTotalTimeSeconds()));

        return Optional.of(geneIds);
    }

}
