package uk.ac.ebi.atlas.search.diffanalytics;


import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsTSVWriterTest {

    private DiffAnalyticsTSVWriter subject;

    @Mock
    private PrintWriter responseWriterMock;

    @Mock
    private Contrast contrastMock;

    private static final String CONTRAST_NAME = "MY_CONSTRAST";
    public static final String BIOENTITY_ID = "1";
    public static final String BIOENTITY_NAME = "jane";
    public static final String EXPERIMENT_ACCESSION = "E-1";
    public static final String SPECIES = "tiger";

    @Before
    public void init() {
        subject = new DiffAnalyticsTSVWriter();
        subject.setResponseWriter(responseWriterMock);
        when(contrastMock.getDisplayName()).thenReturn(CONTRAST_NAME);
    }

    @Test
    public void testWriteDifferentialExpression() throws IOException {
        double pvalue = 0.001;
        double foldChange = 0.004;
        DifferentialExpression diffExpression = new DifferentialExpression(pvalue, foldChange, contrastMock);

        DiffAnalytics dbExpression = new DiffAnalytics(BIOENTITY_ID, BIOENTITY_NAME, EXPERIMENT_ACCESSION, diffExpression, SPECIES);

        List<DiffAnalytics> dbExpressionList = Lists.newArrayList(dbExpression);
        DiffAnalyticsList dbExpressions = new DiffAnalyticsList(dbExpressionList, dbExpressionList.size());

        subject.write(dbExpressions);

        String expectedRow = BIOENTITY_NAME + "\t" + SPECIES + "\t" + EXPERIMENT_ACCESSION + "\t" + CONTRAST_NAME + "\t" + pvalue + "\t" + foldChange + "\tNA\n" ;
        verify(responseWriterMock).write(eq(expectedRow), any(Integer.class), any(Integer.class));
    }

    @Test
    public void testWriteMicroarrayExpression() throws IOException {
        double pvalue = 0.001;
        double foldChange = 0.004;
        double tstatistic = 0.007;
        DifferentialExpression diffExpression = new MicroarrayExpression(pvalue, foldChange, tstatistic, contrastMock);

        DiffAnalytics dbExpression = new DiffAnalytics(BIOENTITY_ID, BIOENTITY_NAME, EXPERIMENT_ACCESSION, diffExpression, SPECIES);

        List<DiffAnalytics> dbExpressionList = Lists.newArrayList(dbExpression);
        DiffAnalyticsList dbExpressions = new DiffAnalyticsList(dbExpressionList, dbExpressionList.size());

        subject.write(dbExpressions);

        String expectedRow = BIOENTITY_NAME + "\t" + SPECIES + "\t" + EXPERIMENT_ACCESSION + "\t" + CONTRAST_NAME + "\t" + pvalue + "\t" + foldChange + "\t" + tstatistic + "\n" ;
        verify(responseWriterMock).write(eq(expectedRow), any(Integer.class), any(Integer.class));
    }

    @Test
    public void testWritePositiveInfinity() throws IOException {
        double pvalue = 0.001;
        double foldChange = Double.POSITIVE_INFINITY;
        DifferentialExpression diffExpression = new DifferentialExpression(pvalue, foldChange, contrastMock);

        DiffAnalytics dbExpression = new DiffAnalytics(BIOENTITY_ID, BIOENTITY_NAME, EXPERIMENT_ACCESSION, diffExpression, SPECIES);

        List<DiffAnalytics> dbExpressionList = Lists.newArrayList(dbExpression);
        DiffAnalyticsList dbExpressions = new DiffAnalyticsList(dbExpressionList, dbExpressionList.size());

        subject.write(dbExpressions);

        String expectedRow = BIOENTITY_NAME + "\t" + SPECIES + "\t" + EXPERIMENT_ACCESSION + "\t" + CONTRAST_NAME + "\t" + pvalue + "\tInf\tNA\n" ;
        verify(responseWriterMock).write(eq(expectedRow), any(Integer.class), any(Integer.class));
    }

    @Test
    public void testWriteNegativeInfinity() throws IOException {
        double pvalue = 0.001;
        double foldChange = Double.NEGATIVE_INFINITY;
        DifferentialExpression diffExpression = new DifferentialExpression(pvalue, foldChange, contrastMock);

        DiffAnalytics dbExpression = new DiffAnalytics(BIOENTITY_ID, BIOENTITY_NAME, EXPERIMENT_ACCESSION, diffExpression, SPECIES);

        List<DiffAnalytics> dbExpressionList = Lists.newArrayList(dbExpression);
        DiffAnalyticsList dbExpressions = new DiffAnalyticsList(dbExpressionList, dbExpressionList.size());

        subject.write(dbExpressions);

        String expectedRow = BIOENTITY_NAME + "\t" + SPECIES + "\t" + EXPERIMENT_ACCESSION + "\t" + CONTRAST_NAME + "\t" + pvalue + "\t-Inf\tNA\n" ;
        verify(responseWriterMock).write(eq(expectedRow), any(Integer.class), any(Integer.class));
    }

}
