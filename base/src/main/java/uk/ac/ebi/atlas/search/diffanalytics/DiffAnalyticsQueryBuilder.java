package uk.ac.ebi.atlas.search.diffanalytics;

import uk.ac.ebi.atlas.search.DatabaseQuery;

import java.text.MessageFormat;

public class DiffAnalyticsQueryBuilder {
    static final String CONTRASTID = "CONTRASTID";
    static final String PVALUE = "PVAL";
    static final String LOG_2_FOLD = "LOG2FOLD";
    static final String TSTAT = "TSTAT";
    static final String IDENTIFIER = "IDENTIFIER";
    static final String NAME = "NAME";
    static final String ORGANISM = "ORGANISM";
    static final String EXPERIMENT = "EXPERIMENT";

    private static final String SELECT_QUERY = "SELECT " +
            IDENTIFIER + ", " +
            NAME  + ", " +
            ORGANISM + ", " +
            EXPERIMENT + ", " +
            CONTRASTID + ", " +
            PVALUE + ", " +
            LOG_2_FOLD + ", " +
            TSTAT +
            " FROM VW_DIFFANALYTICS ";

    private static final String JOIN_PUBLIC_EXPERIMENTS_ONLY =
            "JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' ";

    private static final String ORDER_BY_LOG2FOLD = "order by abs(LOG2FOLD) desc";

    private final String geneId;

    public DiffAnalyticsQueryBuilder(String geneId) {
        this.geneId = geneId;
    }

    public DatabaseQuery<Object> buildSelect() {
        DatabaseQuery<Object> databaseQuery = new DatabaseQuery<>();
        databaseQuery.appendToQueryString(SELECT_QUERY + JOIN_PUBLIC_EXPERIMENTS_ONLY);
        databaseQuery.appendToQueryString(MessageFormat.format("WHERE VW_DIFFANALYTICS.IDENTIFIER=''{0}'' ", geneId));
        databaseQuery.appendToQueryString(ORDER_BY_LOG2FOLD);

        return databaseQuery;
    }
}
