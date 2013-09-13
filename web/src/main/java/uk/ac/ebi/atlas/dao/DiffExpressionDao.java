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
            .append(" FROM VW_DIFFANALYTICS ")
            .toString();

    static final String COUNT_QUERY = "SELECT count(1) FROM VW_DIFFANALYTICS ";
    static final String ORDER_BY_PVAL = "order by PVAL";

    private JdbcTemplate jdbcTemplate;

    private ContrastTrader contrastTrader;

    static final int RESULT_SIZE = 50;


    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DifferentialBioentityExpression> getExpressions(Collection<IndexedContrast> indexedContrasts) {

        IndexedContrastQuery indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts, SELECT_QUERY);
        jdbcTemplate.setMaxRows(RESULT_SIZE);
        List<DifferentialBioentityExpression> result = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                new DifferentialBioentityExpressionRowMapper(contrastTrader),
                indexedContrastQuery.getValues());

        return result;
    }

    public int getResultCount(Collection<IndexedContrast> indexedContrasts) {

        IndexedContrastQuery query = buildIndexedContrastQuery(indexedContrasts, COUNT_QUERY);

        return jdbcTemplate.queryForObject(query.getQuery(), Integer.class, query.getValues());
    }

    IndexedContrastQuery buildIndexedContrastQuery(Collection<IndexedContrast> indexedContrasts, String queryBeginning) {
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
        queryStringBuilder.append(queryBeginning);

        if (!indexedContrasts.isEmpty()) {

            queryStringBuilder .append("WHERE (");
            joiner.appendTo(queryStringBuilder, queryParts)
                    .append(") ");
        }

        queryStringBuilder.append(ORDER_BY_PVAL);

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
//            values.add(String.valueOf(RESULT_SIZE));
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
