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
import uk.ac.ebi.atlas.dao.DiffExpressionDao;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialBioentityExpressionsBuilder {

    private DiffExpressionDao diffExpressionDao;
    private DifferentialConditionsSearchService differentialConditionsSearchService;
    private SolrQueryService solrQueryService;
    private String condition;
    private String geneIdentifier;

    @Inject
    public DifferentialBioentityExpressionsBuilder(DiffExpressionDao diffExpressionDao,
                                                   DifferentialConditionsSearchService differentialConditionsSearchService,
                                                   SolrQueryService solrQueryService) {
        this.diffExpressionDao = diffExpressionDao;
        this.differentialConditionsSearchService = differentialConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }

    public DifferentialBioentityExpressionsBuilder withCondition(String condition){
        this.condition = condition;
        return this;
    }

    public DifferentialBioentityExpressionsBuilder withGeneIdentifier(String geneIdentifier){
        this.geneIdentifier = geneIdentifier;
        return this;
    }


    public DifferentialBioentityExpressions build() {

        if (StringUtils.isNotBlank(condition)){

            Collection<IndexedAssayGroup> contrasts = differentialConditionsSearchService.findContrasts(condition);

            if (contrasts.isEmpty()){
                return new DifferentialBioentityExpressions();
            }

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getExpressions(contrasts);
            int resultCount = diffExpressionDao.getResultCount(contrasts);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }

        if (StringUtils.isNotBlank(geneIdentifier)){

            Set<String> identifiers = expandIdentifiersToMatureRNAIds(geneIdentifier);

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getExpressions(identifiers);
            int resultCount = diffExpressionDao.getResultCount(geneIdentifier);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }

        throw new IllegalStateException("build method invoked with empty arguments");

    }

    Set<String> expandIdentifiersToMatureRNAIds(String geneIdentifier){
        Set<String> mirbaseIds = solrQueryService.findPropertyValuesForGeneId(geneIdentifier, "mirbase_id");
        if (mirbaseIds.size() > 0) {
            geneIdentifier = mirbaseIds.iterator().next();
        }
        Set<String> matureRNAIds = solrQueryService.fetchGeneIdentifiersFromSolr(geneIdentifier, "mirna", false, "hairpin_id");
        if (matureRNAIds.size() > 0) {
            return matureRNAIds;
        }
        return Sets.newHashSet(geneIdentifier);

    }

}
