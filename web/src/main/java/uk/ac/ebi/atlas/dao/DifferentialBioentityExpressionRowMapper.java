package uk.ac.ebi.atlas.dao;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;

import java.sql.ResultSet;
import java.sql.SQLException;

class DifferentialBioentityExpressionRowMapper implements RowMapper<DifferentialBioentityExpression> {

    //Used to handle positive/negative infinite values in the DB
    private static final int INFINITY_VALUE = 1000000;

    private ContrastTrader contrastTrader;

    DifferentialBioentityExpressionRowMapper(ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
    }

    @Override
    public DifferentialBioentityExpression mapRow(ResultSet rs, int rowNum) throws SQLException {
        String experimentAccession = rs.getString(DiffExpressionDao.EXPERIMENT);
        String contrastId = rs.getString(DiffExpressionDao.CONTRASTID);
        Contrast contrast = contrastTrader.getContrast(experimentAccession, contrastId);
        DifferentialExpression expression = buildDifferentialExpression(rs.getDouble(DiffExpressionDao.PVALUE), rs.getDouble(DiffExpressionDao.LOG_2_FOLD), rs.getString(DiffExpressionDao.TSTAT), contrast);


        return new DifferentialBioentityExpression(
                rs.getString(DiffExpressionDao.IDENTIFIER),
                rs.getString(DiffExpressionDao.NAME),
                experimentAccession,
                expression,
                rs.getString(DiffExpressionDao.ORGANISM),
                rs.getString(DiffExpressionDao.DESIGNELEMENT));
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
