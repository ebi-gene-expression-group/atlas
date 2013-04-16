package uk.ac.ebi.atlas.transcript;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptContributionCalculatorTest {

    @Mock
    private GeneProfileDaoMock geneProfileDaoMock;

    @Mock
    private BaselineExperimentsCache experimentsCacheMock;

    private TranscriptContributionCalculator subject;

    private List<TranscriptProfile> transcriptProfiles;

    private TranscriptProfile profile1 = new TranscriptProfile("T1", Lists.newArrayList(1d, 1d, 3d));
    private TranscriptProfile profile2 = new TranscriptProfile("T2", Lists.newArrayList(1d, 2d, 3d));
    private TranscriptProfile profile3 = new TranscriptProfile("T3", Lists.newArrayList(1d, 3d, 3d));
    private TranscriptProfile profile4 = new TranscriptProfile("T4", Lists.newArrayList(1d, 4d, 3d));

    @Before
    public void initSubject() throws Exception {

        subject = new TranscriptContributionCalculator(geneProfileDaoMock, experimentsCacheMock);
    }

    @Test
    public void testCreateTopThreeTranscriptsMapWithMoreThan3Transcripts() throws Exception {
        //given
        transcriptProfiles = Lists.newArrayList(profile4, profile3, profile2, profile1);

        //when
        Map<String, Double> topThreeTranscriptsMap = subject.createTopThreeTranscriptsMap(transcriptProfiles, 1);

        //then
        assertThat(topThreeTranscriptsMap.keySet(), containsInAnyOrder("T4", "T3", "T2", TranscriptContributionCalculator.OTHERS));
        assertThat(topThreeTranscriptsMap.values(), containsInAnyOrder(4d, 3d, 2d, 1d));

    }

    @Test
    public void testCreateTopThreeTranscriptsMapWith2Transcripts() throws Exception {
        //given
        transcriptProfiles = Lists.newArrayList(profile4, profile2);

        //when
        Map<String, Double> topThreeTranscriptsMap = subject.createTopThreeTranscriptsMap(transcriptProfiles, 1);

        //then
        assertThat(topThreeTranscriptsMap.keySet(), containsInAnyOrder("T4", "T2"));
        assertThat(topThreeTranscriptsMap.values(), containsInAnyOrder(4d, 2d));
    }

    @Test
    public void testGetReverseTranscriptProfileComparator() throws Exception {
        //when
        Comparator<TranscriptProfile> comparator = subject.getReverseTranscriptProfileComparator(1);

        //then
        assertThat(comparator.compare(profile2, profile3), greaterThan(0));
        assertThat(comparator.compare(profile2, profile2), is(0));
    }
}
