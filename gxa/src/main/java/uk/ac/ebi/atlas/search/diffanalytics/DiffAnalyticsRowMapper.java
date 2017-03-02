package uk.ac.ebi.atlas.search.diffanalytics;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.trader.ContrastTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@Scope("prototype")
class DiffAnalyticsRowMapper implements RowMapper<DiffAnalytics> {

    //Used to handle positive/negative infinite values in the DB
    private static final int INFINITY_VALUE = 1000000;

    private ContrastTrader contrastTrader;

    @Inject
    DiffAnalyticsRowMapper(ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
    }

    @Override
    public DiffAnalytics mapRow(ResultSet rs, int rowNum) throws SQLException {
        String experimentAccession = rs.getString(DiffAnalyticsQueryBuilder.EXPERIMENT);
        String contrastId = rs.getString(DiffAnalyticsQueryBuilder.CONTRASTID);

        //TODO: getting contrast is slow because we go back to the database to get the experiment for each contrast
        Contrast contrast = contrastTrader.getContrast(experimentAccession, contrastId);
        DifferentialExpression expression = buildDifferentialExpression(rs.getDouble(DiffAnalyticsQueryBuilder.PVALUE), rs.getDouble(DiffAnalyticsQueryBuilder.LOG_2_FOLD), rs.getString(DiffAnalyticsQueryBuilder.TSTAT), contrast);

        return new DiffAnalytics(
                rs.getString(DiffAnalyticsQueryBuilder.IDENTIFIER),
                rs.getString(DiffAnalyticsQueryBuilder.NAME),
                experimentAccession,
                expression,
                rs.getString(DiffAnalyticsQueryBuilder.ORGANISM), contrast);
    }

    DifferentialExpression buildDifferentialExpression(double pValue, double foldChange, String tstatistic, Contrast contrast) {

        if (foldChange == INFINITY_VALUE) {
            foldChange = Double.POSITIVE_INFINITY;
        }
        if (foldChange == -INFINITY_VALUE) {
            foldChange = Double.NEGATIVE_INFINITY;
        }

        if (tstatistic == null) {
            return new DifferentialExpression(pValue, foldChange, contrast);
        }
        return new MicroarrayExpression(pValue, foldChange, Double.parseDouble(tstatistic), contrast);


    }
}
