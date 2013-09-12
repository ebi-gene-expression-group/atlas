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
                experimentAccession,
                expression,
                rs.getString(DiffExpressionDao.ORGANISM),
                rs.getString(DiffExpressionDao.DESIGNELEMENT));
    }

    DifferentialExpression buildDifferentialExpression(double pValue, double foldChange, String tstatistic, Contrast contrast) {

        if (tstatistic == null) {
            return new DifferentialExpression(pValue, foldChange, contrast);
        }
        return new MicroarrayExpression(pValue, foldChange, Double.parseDouble(tstatistic), contrast);


    }
}
