package uk.ac.ebi.atlas.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@Scope("prototype")
public class BaselineBioentitiesCountRowMapper implements RowMapper<BaselineBioentitiesCount> {

    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    public BaselineBioentitiesCountRowMapper( BaselineExperimentsCache baselineExperimentsCache) {
        this.baselineExperimentsCache = baselineExperimentsCache;
    }

    @Override
    public BaselineBioentitiesCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        String experimentAccession = rs.getString(AssayGroupQueryBuilder.EXPERIMENT);
        String assayGroupId = rs.getString(BaselineExpressionDao.ASSAYGROUPID);
        int count = rs.getInt(BaselineExpressionDao.COUNT_IDENTIFIER);
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

        return new BaselineBioentitiesCount(experiment.getDisplayName(),
                experiment.getSpeciesByAssayGroup(assayGroupId), experimentAccession, count);
    }
}
