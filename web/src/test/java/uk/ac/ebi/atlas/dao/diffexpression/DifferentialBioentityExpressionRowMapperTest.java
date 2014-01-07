package uk.ac.ebi.atlas.dao.diffexpression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.diffexpression.DiffExpressionDao;
import uk.ac.ebi.atlas.dao.diffexpression.DifferentialBioentityExpressionRowMapper;
import uk.ac.ebi.atlas.dao.diffexpression.DifferentialGeneQueryBuilder;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;

import java.sql.ResultSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialBioentityExpressionRowMapperTest {

    private DifferentialBioentityExpressionRowMapper subject;

    @Mock
    private ContrastTrader contrastTraderMock;

    @Mock
    private ResultSet resultSetMock;

    private Contrast contrast = new Contrast("c1", "ad", null, null, "name");

    @Before
    public void setUp() throws Exception {
        when(contrastTraderMock.getContrast("e1", "c1")).thenReturn(contrast);

        when(resultSetMock.getString(DifferentialGeneQueryBuilder.EXPERIMENT)).thenReturn("e1");
        when(resultSetMock.getString(DifferentialGeneQueryBuilder.CONTRASTID)).thenReturn("c1");
        when(resultSetMock.getString(DifferentialGeneQueryBuilder.IDENTIFIER)).thenReturn("id1");
        when(resultSetMock.getString(DifferentialGeneQueryBuilder.ORGANISM)).thenReturn("org1");
        when(resultSetMock.getString(DifferentialGeneQueryBuilder.DESIGNELEMENT)).thenReturn("de1");
        when(resultSetMock.getDouble(DifferentialGeneQueryBuilder.PVALUE)).thenReturn(0.0001);
        when(resultSetMock.getDouble(DifferentialGeneQueryBuilder.LOG_2_FOLD)).thenReturn(-1.1);
        when(resultSetMock.getString(DifferentialGeneQueryBuilder.TSTAT)).thenReturn("1.1");

        subject = new DifferentialBioentityExpressionRowMapper(contrastTraderMock);
    }

    @Test
    public void testMapRow() throws Exception {
        DifferentialBioentityExpression expression = subject.mapRow(resultSetMock, 1);

        assertThat(expression.getBioentityId(), is("id1"));
        assertThat(expression.getExperimentAccession(), is("e1"));
        assertThat(expression.getSpecies(), is("org1"));
        assertThat(expression.getDesignElement(), is("de1"));

        DifferentialExpression value = new MicroarrayExpression(0.0001, -1.1, 1.1, contrast);
        assertThat(expression.getExpression(), is(value));
    }

    @Test
    public void testBuildDifferentialExpression() throws Exception {
        DifferentialExpression expression = subject.buildDifferentialExpression(0.001, 1, null, contrast);
        assertThat(expression.getClass().getName(), is(DifferentialExpression.class.getName()));
    }

    @Test
    public void testBuildMicroarrayExpression() throws Exception {
        DifferentialExpression expression = subject.buildDifferentialExpression(0.001, 1, "1.1", contrast);
        assertThat(expression.getClass().getName(), is(MicroarrayExpression.class.getName()));
    }
}
