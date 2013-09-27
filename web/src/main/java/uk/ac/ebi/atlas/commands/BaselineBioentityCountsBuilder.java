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

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.BaselineExpressionDao;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("request")
public class BaselineBioentityCountsBuilder {

    private BaselineExpressionDao baselineExpressionDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    @Inject
    public BaselineBioentityCountsBuilder(BaselineExpressionDao baselineExpressionDao, BaselineConditionsSearchService baselineConditionsSearchService) {
        this.baselineExpressionDao = baselineExpressionDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
    }

    public List<BaselineBioentitiesCount> build(String query) {

        checkArgument(StringUtils.isNotBlank(query));

        Collection<IndexedAssayGroup> assayGroups = baselineConditionsSearchService.findAssayGroups(query);

        if (assayGroups.isEmpty()){
            return Lists.newArrayList();
        }

        return baselineExpressionDao.getBioentitiesCounts(assayGroups);

    }

}
