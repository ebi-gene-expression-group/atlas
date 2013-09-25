package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class DiffExpressionDao {

    static final String CONTRASTID = "CONTRASTID";
    static final String PVALUE = "PVAL";
    static final String LOG_2_FOLD = "LOG2FOLD";
    static final String TSTAT = "TSTAT";
    static final String IDENTIFIER = "IDENTIFIER";
    static final String NAME = "NAME";
    static final String ORGANISM = "ORGANISM";
    static final String DESIGNELEMENT = "DESIGNELEMENT";

    static final int RESULT_SIZE = 50;

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ").append(IDENTIFIER).append(", ")
            .append(NAME).append(", ")
            .append(DESIGNELEMENT).append(", ")
            .append(ORGANISM).append(", ")
            .append(AssayGroupQueryBuilder.EXPERIMENT).append(", ")
            .append(CONTRASTID).append(", ")
            .append(PVALUE).append(", ")
            .append(LOG_2_FOLD).append(", ")
            .append(TSTAT)
            .append(" FROM VW_DIFFANALYTICS ")
            .toString();
    static final String COUNT_QUERY = "SELECT count(1) FROM VW_DIFFANALYTICS ";

    static final String ORDER_BY_PVAL = "order by PVAL";

    private JdbcTemplate jdbcTemplate;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private DifferentialBioentityExpressionRowMapper rowMapper;

    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, AssayGroupQueryBuilder assayGroupQueryBuilder, DifferentialBioentityExpressionRowMapper rowMapper) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DifferentialBioentityExpression> getExpressions(Collection<IndexedAssayGroup> indexedContrasts) {

        AssayGroupQuery indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts, SELECT_QUERY);
        jdbcTemplate.setMaxRows(RESULT_SIZE);
        List<DifferentialBioentityExpression> result = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                rowMapper,
                indexedContrastQuery.getValues());

        return result;
    }

    public int getResultCount(Collection<IndexedAssayGroup> indexedContrasts) {

        AssayGroupQuery query = buildIndexedContrastQuery(indexedContrasts, COUNT_QUERY);

        return jdbcTemplate.queryForObject(query.getQuery(), Integer.class, query.getValues());
    }

    AssayGroupQuery buildIndexedContrastQuery(Collection<IndexedAssayGroup> indexedContrasts, String queryBeginning) {

        return assayGroupQueryBuilder.withSelectPart(queryBeginning)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withAssayGroupOrContrast(CONTRASTID)
                .withExtraCondition(ORDER_BY_PVAL).build();

    }

}
