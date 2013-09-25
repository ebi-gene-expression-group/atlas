package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

public class BaselineExpressionDao {

    static final String COUNT_IDENTIFIER = "COUNT(IDENTIFIER)";

    static final String ASSAYGROUPID = "ASSAYGROUPID";

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ").append(AssayGroupQueryBuilder.EXPERIMENT).append(", ")
            .append(COUNT_IDENTIFIER)
            .append(" RNASEQ_BSLN_EXPRESSIONS  subpartition( ABOVE_CUTOFF ) ")
            .toString();

    private ExperimentTrader experimentTrader;

    private JdbcTemplate jdbcTemplate;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private BaselineBioentitiesCountRowMapper rowMapper;

    @Inject
    public BaselineExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, ExperimentTrader experimentTrader, AssayGroupQueryBuilder assayGroupQueryBuilder) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.experimentTrader = experimentTrader;
    }

    public List<BaselineBioentitiesCount> getBioentitiesCounts(Collection<IndexedAssayGroup> indexedAssayGroups) {
        AssayGroupQuery query = assayGroupQueryBuilder.withSelectPart(SELECT_QUERY)
                .withIndexedAssayGroupsOrContrasts(indexedAssayGroups)
                .withAssayGroupOrContrast(ASSAYGROUPID)
                .build();

        return jdbcTemplate.query(query.getQuery(), rowMapper, query.getValues());

    }

}
