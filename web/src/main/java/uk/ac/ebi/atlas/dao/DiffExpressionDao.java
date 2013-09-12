package uk.ac.ebi.atlas.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedContrast;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class DiffExpressionDao {

    static final String EXPERIMENT = "EXPERIMENT";
    static final String CONTRASTID = "CONTRASTID";
    static final String PVALUE = "PVAL";
    static final String LOG_2_FOLD = "LOG2FOLD";
    static final String TSTAT = "TSTAT";
    static final String IDENTIFIER = "IDENTIFIER";
    static final String ORGANISM = "ORGANISM";
    static final String DESIGNELEMENT = "DESIGNELEMENT";

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ").append(IDENTIFIER).append(", ")
            .append(DESIGNELEMENT).append(", ")
            .append(ORGANISM).append(", ")
            .append(EXPERIMENT).append(", ")
            .append(CONTRASTID).append(", ")
            .append(PVALUE).append(", ")
            .append(LOG_2_FOLD).append(", ")
            .append(TSTAT)
            .append(" FROM VW_DIFFANALYTICS where ")
            .toString();

    static final String ROWNUM_ORDER_BY_PVAL = "rownum <= ? order by PVAL";

    private JdbcTemplate jdbcTemplate;

    private ContrastTrader contrastTrader;

    static final int RESULT_SIZE = 50;


    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DifferentialBioentityExpression> getExpressions() {
        String query = SELECT_QUERY + ROWNUM_ORDER_BY_PVAL;

        List<DifferentialBioentityExpression> result = jdbcTemplate.query(query, new DifferentialBioentityExpressionRowMapper(contrastTrader), RESULT_SIZE);

        return result;
    }

    public List<DifferentialBioentityExpression> getExpressions(Collection<IndexedContrast> indexedContrasts) {

        IndexedContrastQuery indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts);
        List<DifferentialBioentityExpression> result = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                new DifferentialBioentityExpressionRowMapper(contrastTrader),
                indexedContrastQuery.getValues());

        return result;
    }

    IndexedContrastQuery buildIndexedContrastQuery(Collection<IndexedContrast> indexedContrasts) {
        IndexedContrastQuery query = new IndexedContrastQuery();

        List<String> queryParts = Lists.newArrayList();

        String queryPart = "(" + EXPERIMENT + "=? AND " + CONTRASTID + "=? )";

        for (IndexedContrast indexedContrast : indexedContrasts) {
            queryParts.add(queryPart);
            query.addValue(indexedContrast.getExperimentAccession());
            query.addValue(indexedContrast.getContrastId());
        }

        Joiner joiner = Joiner.on(" OR ");

        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append(SELECT_QUERY);

        if (!indexedContrasts.isEmpty()) {

            queryStringBuilder .append("(");
            joiner.appendTo(queryStringBuilder, queryParts)
                    .append(") AND ");
        }

        queryStringBuilder.append(ROWNUM_ORDER_BY_PVAL);

        query.setQueryString(queryStringBuilder.toString());
        return query;
    }

    static class IndexedContrastQuery {
        private List<String> values = Lists.newArrayList();
        private String query;

        void addValue(String value) {
            values.add(value);
        }

        String[] getValues() {
            values.add(String.valueOf(RESULT_SIZE));
            return values.toArray(new String[values.size()]);
        }

        String getQuery() {
            return query;
        }

        void setQueryString(String query) {
            this.query = query;
        }
    }
}
