package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Named
@Scope("prototype")
public class BaselineExpressionDao {

    static final String COUNT_IDENTIFIER = "COUNT(IDENTIFIER)";

    static final String ASSAYGROUPID = "ASSAYGROUPID";

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ")
            .append(AssayGroupQueryBuilder.EXPERIMENT).append(", ")
            .append(ASSAYGROUPID).append(", ")
            .append(COUNT_IDENTIFIER)
            .append(" FROM RNASEQ_BSLN_EXPRESSIONS  subpartition( ABOVE_CUTOFF ) ")
            .toString();
    static final String GROUP_BY_EXPERIMENT_ASSAYGROUPID = "group by EXPERIMENT, ASSAYGROUPID";

    private JdbcTemplate jdbcTemplate;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private BaselineBioentitiesCountRowMapper rowMapper;

    @Inject
    public BaselineExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource,
                                 AssayGroupQueryBuilder assayGroupQueryBuilder, BaselineBioentitiesCountRowMapper rowMapper) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.rowMapper = rowMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<BaselineBioentitiesCount> getBioentitiesCounts(Collection<IndexedAssayGroup> indexedAssayGroups) {
        AssayGroupQuery query = assayGroupQueryBuilder.withSelectPart(SELECT_QUERY)
                .withIndexedAssayGroupsOrContrasts(indexedAssayGroups)
                .withAssayGroupOrContrast(ASSAYGROUPID)
                .withExtraCondition(GROUP_BY_EXPERIMENT_ASSAYGROUPID)
                .build();

        return jdbcTemplate.query(query.getQuery(), rowMapper, query.getValues());

    }

}
