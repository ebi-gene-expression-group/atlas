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
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

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
    private Set<String> geneIdentifiers;
    private Set<String> geneIdentifiersToExpandToMatureRNAIds;

    @Inject
    public DifferentialBioentityExpressionsBuilder(DiffExpressionDao diffExpressionDao,
                                                   DifferentialConditionsSearchService differentialConditionsSearchService,
                                                   SolrQueryService solrQueryService, BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.diffExpressionDao = diffExpressionDao;
        this.differentialConditionsSearchService = differentialConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }

    public DifferentialBioentityExpressionsBuilder withCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public DifferentialBioentityExpressionsBuilder withGeneIdentifiers(Set<String> geneIdentifiers) {
        this.geneIdentifiers = geneIdentifiers;
        if (geneIdentifiersToExpandToMatureRNAIds == null) {
            this.geneIdentifiersToExpandToMatureRNAIds = geneIdentifiers;
        }
        return this;
    }

    // specify the gene identifiers to expand to mature RNA ids. By default these are the geneIdentifiers from
    // withGeneIdentifiers, but the set can be overridden here
    public DifferentialBioentityExpressionsBuilder withGeneIdentifiersToExpandToMatureRNAIds(Set<String> geneIdentifiersToExpandToMatureRNAIds) {
        this.geneIdentifiersToExpandToMatureRNAIds = geneIdentifiersToExpandToMatureRNAIds;
        return this;
    }

    public DifferentialBioentityExpressions build() {

        if (StringUtils.isNotBlank(condition)) {

            Collection<IndexedAssayGroup> contrasts = differentialConditionsSearchService.findContrasts(condition);

            if (contrasts.isEmpty()) {
                return new DifferentialBioentityExpressions();
            }

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getExpressions(contrasts);
            int resultCount = diffExpressionDao.getResultCount(contrasts);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }

        if (CollectionUtils.isNotEmpty(geneIdentifiers)) {

            Set<String> expandedIdentifiers = solrQueryService.findMatureRNAIds(geneIdentifiersToExpandToMatureRNAIds);

            expandedIdentifiers.addAll(geneIdentifiers);

            List<DifferentialBioentityExpression> expressions = diffExpressionDao.getExpressions(expandedIdentifiers);
            int resultCount = diffExpressionDao.getResultCount(expandedIdentifiers);

            return new DifferentialBioentityExpressions(expressions, resultCount);

        }

        throw new IllegalArgumentException("build method invoked with empty arguments");

    }

    public DifferentialBioentityExpressions build(GeneQuerySearchRequestParameters requestParameters) throws GenesNotFoundException {
        // search across any species
        String species = "";

        //resolve any gene keywords to identifiers
        String geneQuery = requestParameters.getGeneQuery();

        GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                requestParameters.isExactMatch(),
                species,
                requestParameters.isGeneSetMatch());

        Collection<String> resolvedGeneIds = geneQueryResponse.getAllGeneIds();

        if (resolvedGeneIds.size() == 0) {
            throw new GenesNotFoundException();
        }


        return null;
    }


}
