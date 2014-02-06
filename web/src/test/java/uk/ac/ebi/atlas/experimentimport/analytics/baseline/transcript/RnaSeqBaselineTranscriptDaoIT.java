package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDao;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
@Transactional  // enable transaction manager, so that changes to the database are rolled back after each test method
public class RnaSeqBaselineTranscriptDaoIT {

    public static final String EXPERIMENT_ACCESSION = "delme";
    public static final String GENE_1 = "gene1";
    public static final String TRANSCRIPT_ID_1 = "t1";
    public static final String TRANSCRIPT_ID_2 = "t2";

    @Inject
    private RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao;

    @Inject
    private TranscriptProfileDao transcriptProfileDao;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RnaSeqBaselineTranscriptReader rnaSeqBaselineTranscriptReader;

    private static final RnaSeqBaselineTranscript RNA_SEQ_BASELINE_TRANSCRIPT_1 = RnaSeqBaselineTranscript.create(GENE_1, TRANSCRIPT_ID_1, new Double[]{0.0,1.1});
    private static final RnaSeqBaselineTranscript RNA_SEQ_BASELINE_TRANSCRIPT_2 = RnaSeqBaselineTranscript.create(GENE_1, TRANSCRIPT_ID_2, new Double[]{2.2,3.3});
    private static final TranscriptProfile TRANSCRIPT_PROFILE_1 = new TranscriptProfile(GENE_1, TRANSCRIPT_ID_1, ImmutableList.of("0.0", "1.1"));
    private static final TranscriptProfile TRANSCRIPT_PROFILE_2 = new TranscriptProfile(GENE_1, TRANSCRIPT_ID_2, ImmutableList.of("2.2", "3.3"));

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(rnaSeqBaselineTranscriptReader.next()).thenReturn(RNA_SEQ_BASELINE_TRANSCRIPT_1, RNA_SEQ_BASELINE_TRANSCRIPT_2);
        when(rnaSeqBaselineTranscriptReader.hasNext()).thenReturn(true, true, false);
    }

    @Test
    public void insertAndDeleteBaselineExpressions() throws IOException {
        assertThat(getCount(), is(0));

        rnaSeqBaselineTranscriptDao.insert(EXPERIMENT_ACCESSION, rnaSeqBaselineTranscriptReader);

        assertThat(getCount(), is(2));

        Collection<TranscriptProfile> transcriptProfiles = transcriptProfileDao.findTranscriptProfiles(EXPERIMENT_ACCESSION, GENE_1);

        assertThat(transcriptProfiles, containsInAnyOrder(TRANSCRIPT_PROFILE_1, TRANSCRIPT_PROFILE_2));

        rnaSeqBaselineTranscriptDao.delete(EXPERIMENT_ACCESSION);

        assertThat(getCount(), is(0));

    }

    private int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ? AND ISACTIVE ='T'", Integer.class, EXPERIMENT_ACCESSION);
    }


}
