package uk.ac.ebi.atlas.search.diffanalytics;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.trader.ContrastTrader;

import java.sql.ResultSet;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsRowMapperTest {

    private DiffAnalyticsRowMapper subject;

    @Mock
    private ContrastTrader contrastTraderMock;

    @Mock
    private ResultSet resultSetMock;

    private Contrast contrast = new Contrast("c1", "ad", null, null, "name");

    @Before
    public void setUp() throws Exception {
        Mockito.when(contrastTraderMock.getContrast("e1", "c1")).thenReturn(contrast);

        Mockito.when(resultSetMock.getString(DiffAnalyticsQueryBuilder.EXPERIMENT)).thenReturn("e1");
        Mockito.when(resultSetMock.getString(DiffAnalyticsQueryBuilder.CONTRASTID)).thenReturn("c1");
        Mockito.when(resultSetMock.getString(DiffAnalyticsQueryBuilder.IDENTIFIER)).thenReturn("id1");
        Mockito.when(resultSetMock.getString(DiffAnalyticsQueryBuilder.ORGANISM)).thenReturn("org1");
        Mockito.when(resultSetMock.getDouble(DiffAnalyticsQueryBuilder.PVALUE)).thenReturn(0.0001);
        Mockito.when(resultSetMock.getDouble(DiffAnalyticsQueryBuilder.LOG_2_FOLD)).thenReturn(-1.1);
        Mockito.when(resultSetMock.getString(DiffAnalyticsQueryBuilder.TSTAT)).thenReturn("1.1");

        subject = new DiffAnalyticsRowMapper(contrastTraderMock);
    }

    @Test
    public void testMapRow() throws Exception {
        DiffAnalytics expression = subject.mapRow(resultSetMock, 1);

        MatcherAssert.assertThat(expression.getBioentityId(), Is.is("id1"));
        MatcherAssert.assertThat(expression.getExperimentAccession(), Is.is("e1"));
        MatcherAssert.assertThat(expression.getSpecies(), Is.is("org1"));

        DifferentialExpression value = new MicroarrayExpression(0.0001, -1.1, 1.1, contrast);
        MatcherAssert.assertThat(expression.getExpression(), Is.is(value));
    }

    @Test
    public void testBuildDifferentialExpression() throws Exception {
        DifferentialExpression expression = subject.buildDifferentialExpression(0.001, 1, null, contrast);
        MatcherAssert.assertThat(expression.getClass().getName(), Is.is(DifferentialExpression.class.getName()));
    }

    @Test
    public void testBuildMicroarrayExpression() throws Exception {
        DifferentialExpression expression = subject.buildDifferentialExpression(0.001, 1, "1.1", contrast);
        MatcherAssert.assertThat(expression.getClass().getName(), Is.is(MicroarrayExpression.class.getName()));
    }
}
