package uk.ac.ebi.atlas.dao.diffexpression;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@Scope("prototype")
class DifferentialBioentityExpressionRowMapper implements RowMapper<DifferentialBioentityExpression> {

    //Used to handle positive/negative infinite values in the DB
    private static final int INFINITY_VALUE = 1000000;

    private ContrastTrader contrastTrader;

    @Inject
    DifferentialBioentityExpressionRowMapper(ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
    }

    @Override
    public DifferentialBioentityExpression mapRow(ResultSet rs, int rowNum) throws SQLException {
        String experimentAccession = rs.getString(DifferentialGeneQueryBuilder.EXPERIMENT);
        String contrastId = rs.getString(DiffExpressionDao.CONTRASTID);

        //TODO: getting contrast is slow because we go back to the database to get the experiment for each contrast
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
