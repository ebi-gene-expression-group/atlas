package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.trader.loader.BaselineExperimentExpressionLevelFile;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
@Transactional  // enable transaction manager, so that changes to the database are rolled back after each test method
public class RnaSeqBaselineTranscriptImporterIT {

    private static final String TEST_ACCESSION = "TEST-BASELINE";

    RnaSeqBaselineTranscriptImporter subject;

    @Inject
    JdbcTemplate jdbcTemplate;

    @Inject
    RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao;
    @Inject
    RnaSeqBaselineTranscriptReaderFactory rnaSeqBaselineTranscriptReaderFactory;
    @Inject
    BaselineExperimentExpressionLevelFile baselineExperimentExpressionLevelFile;

    @Mock
    RnaSeqBaselineTranscriptReaderFactory rnaSeqBaselineTranscriptReaderFactoryMock;

    RnaSeqBaselineTranscriptReader rnaSeqBaselineTranscriptReaderSpy;

    String[] orderedAssayGroupIds = "g1\tg2\tg3\tg4\tg5\tg6\tg7\tg8\tg9\tg10\tg11\tg12\tg13\tg14\tg15\tg16".split("\t");

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        rnaSeqBaselineTranscriptReaderSpy = spy(rnaSeqBaselineTranscriptReaderFactory.create(TEST_ACCESSION, orderedAssayGroupIds));

        when(rnaSeqBaselineTranscriptReaderFactoryMock.create(TEST_ACCESSION, orderedAssayGroupIds)).thenReturn(rnaSeqBaselineTranscriptReaderSpy);

        subject = new RnaSeqBaselineTranscriptImporter(rnaSeqBaselineTranscriptDao,
                rnaSeqBaselineTranscriptReaderFactoryMock,
                baselineExperimentExpressionLevelFile);

    }

    @Test
    public void importTranscripts() throws IOException {

        subject.importTranscripts(TEST_ACCESSION);

        assertThat(getCount(TEST_ACCESSION), is(5));
        verify(rnaSeqBaselineTranscriptReaderSpy).close();
    }

    @Test
    public void readerClosedOnException() throws IOException {
        rnaSeqBaselineTranscriptReaderSpy = Mockito.mock(RnaSeqBaselineTranscriptReader.class);
        when(rnaSeqBaselineTranscriptReaderSpy.hasNext()).thenThrow(UnsupportedOperationException.class);

        when(rnaSeqBaselineTranscriptReaderFactoryMock.create(TEST_ACCESSION, orderedAssayGroupIds)).thenReturn(rnaSeqBaselineTranscriptReaderSpy);

        subject = new RnaSeqBaselineTranscriptImporter(rnaSeqBaselineTranscriptDao,
                rnaSeqBaselineTranscriptReaderFactoryMock,
                baselineExperimentExpressionLevelFile);

        try {
            subject.importTranscripts(TEST_ACCESSION);
        } catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException ignored");
        } finally {
            verify(rnaSeqBaselineTranscriptReaderSpy).close();
        }
    }

    private int getCount(String accession) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ? AND ISACTIVE ='T'", Integer.class, accession);
    }


}
