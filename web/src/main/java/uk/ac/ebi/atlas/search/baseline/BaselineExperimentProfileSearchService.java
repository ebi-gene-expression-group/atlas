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

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("request")
public class BaselineExperimentProfileSearchService {

    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentProfileSearchService.class);

    private final RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao;

    private final SolrQueryService solrQueryService;

    private BaselineTissueExperimentSearchResultProducer baselineTissueExperimentSearchResultProducer;

    @Inject
    public BaselineExperimentProfileSearchService(RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao, SolrQueryService solrQueryService, BaselineTissueExperimentSearchResultProducer baselineTissueExperimentSearchResultProducer) {
        this.rnaSeqBslnExpressionDao = rnaSeqBslnExpressionDao;
        this.solrQueryService = solrQueryService;
        this.baselineTissueExperimentSearchResultProducer = baselineTissueExperimentSearchResultProducer;
    }

    boolean isEmpty(Optional<? extends Collection<?>> coll) {
        return (!coll.isPresent() || coll.get().isEmpty());
    }

    // query(Set<String> geneIds) to be used going forward, see TODO below
    @Deprecated
    public BaselineTissueExperimentSearchResult query(String geneQuery, String species, boolean isExactMatch)  {
        LOGGER.info(String.format("<query> geneQuery=%s", geneQuery));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        //TODO: more efficient to have the caller do this, otherwise we are repeating this query
        //for both baseline and differential.
        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

        BaselineTissueExperimentSearchResult result = fetchTissueExperimentProfiles(geneIds);

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", result.experimentProfiles.size(), stopWatch.getTotalTimeSeconds()));

        return result;
    }

    public BaselineTissueExperimentSearchResult query(Set<String> geneIds)  {
        LOGGER.info(String.format("<query> geneIds=%s", Joiner.on(",").join(geneIds)));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        BaselineTissueExperimentSearchResult result = fetchTissueExperimentProfiles(Optional.of(geneIds));

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", result.experimentProfiles.size(), stopWatch.getTotalTimeSeconds()));

        return result;
    }

    BaselineTissueExperimentSearchResult fetchTissueExperimentProfiles(Optional<? extends Set<String>> geneIds) {

        if (isEmpty(geneIds)) {
            return new BaselineTissueExperimentSearchResult();
        }

        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchAverageExpressionByExperimentAssayGroup(geneIds.get());

        return baselineTissueExperimentSearchResultProducer.buildProfilesForTissueExperiments(expressions);
    }

}
