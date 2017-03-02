
package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqDifferentialProfilesWriterFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    
    @Mock
    HttpServletRequest requestMock;

    @Mock
    DifferentialRequestPreferences preferencesMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    DifferentialExperiment experimentMock;

    @Mock
    RnaSeqRequestContext requestContextMock;

    @Mock
    PrintWriter printWriterMock;

    @Mock
    ExpressionsWriter expressionsWriterMock;

    @Mock
    ExperimentTrader experimentTrader;

    @Mock
    RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;
    
    @Mock
    RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory;
    
    @Mock
    SolrQueryService solrQueryService;
    
    @Mock
    DataWriterFactory dataWriterFactory;

    RnaSeqExperimentDownloadController subject;

    @Before
    public void setUp() throws Exception {
                
        subject = new RnaSeqExperimentDownloadController(rnaSeqProfileStreamFactory, rnaSeqDifferentialProfilesWriterFactory,
                solrQueryService,dataWriterFactory, experimentTrader);
        when(experimentTrader.getExperiment(EXPERIMENT_ACCESSION,"")).thenReturn(experimentMock);
        when(experimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(responseMock.getWriter()).thenReturn(printWriterMock);
    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        subject.downloadGeneProfiles(EXPERIMENT_ACCESSION,"", preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

    }

    @Test
    public void testDownloadRawCounts() throws Exception {
        when(dataWriterFactory.getRnaSeqRawDataWriter(experimentMock, printWriterMock)).thenReturn(expressionsWriterMock);
        when(expressionsWriterMock.write()).thenReturn(0L);

        subject.downloadRawCounts(EXPERIMENT_ACCESSION,"",requestMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-raw-counts.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }

    @Test
    public void testDownloadAllAnalytics() throws Exception {

        when(dataWriterFactory.getRnaSeqAnalyticsDataWriter(experimentMock, printWriterMock)).thenReturn(expressionsWriterMock);
        when(expressionsWriterMock.write()).thenReturn(0L);

        subject.downloadAllAnalytics(EXPERIMENT_ACCESSION,"",requestMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-analytics.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }
}