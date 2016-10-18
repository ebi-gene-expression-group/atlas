package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentProfileSearchService.class);

    private final BaselineExpressionDao baselineExpressionDao;

    private BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer;

    @Inject
    public BaselineExperimentProfileSearchService(BaselineExpressionDao baselineExpressionDao,
                                                  BaselineExperimentSearchResultProducer baselineExperimentSearchResultProducer) {
        this.baselineExpressionDao = baselineExpressionDao;
        this.baselineExperimentSearchResultProducer = baselineExperimentSearchResultProducer;
    }

    public BaselineExperimentSearchResult query(Set<String> geneIds)  {
        LOGGER.info("<query> geneIds={}", Joiner.on(",").join(geneIds));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        BaselineExperimentSearchResult result = baselineExperimentSearchResultProducer.buildProfilesForTissueExperiments(
                baselineExpressionDao.fetchAverageExpressionByExperimentAssayGroup(geneIds));

        stopWatch.stop();
        LOGGER.info("<query> {} results, took {} seconds", result.experimentProfiles.size(), stopWatch.getTotalTimeSeconds());

        return result;
    }

}
