package uk.ac.ebi.atlas.search.diffanalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
@Scope("prototype")
public class DiffAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffAnalyticsDao.class);
    private static final int RESULT_SIZE = 50;

    private final JdbcTemplate jdbcTemplate;
    private final DiffAnalyticsRowMapper dbeRowMapper;

    @Inject
    public DiffAnalyticsDao(DataSource dataSource, DiffAnalyticsRowMapper dbeRowMapper) {
        this(new JdbcTemplate(dataSource), dbeRowMapper) ;
    }

    DiffAnalyticsDao(JdbcTemplate jdbcTemplate, DiffAnalyticsRowMapper dbeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbeRowMapper = dbeRowMapper;
    }

    public List<DiffAnalytics> fetchTopExpressions(String geneId) {

        DatabaseQuery<Object> indexedContrastQuery = buildSelect(geneId);

        jdbcTemplate.setMaxRows(RESULT_SIZE);

        List<DiffAnalytics> results;

        try {
            results = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                    dbeRowMapper,
                    indexedContrastQuery.getParameters().toArray());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return results;

    }

    private DatabaseQuery<Object> buildSelect(String geneId) {
        return new DiffAnalyticsQueryBuilder(geneId).buildSelect();
    }

}
