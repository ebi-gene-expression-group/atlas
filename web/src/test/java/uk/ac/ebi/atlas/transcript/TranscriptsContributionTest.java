package uk.ac.ebi.atlas.transcript;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TranscriptsContributionTest {

    private TranscriptsContribution subject = new TranscriptsContribution();

    @Test
    public void testGetTranscriptPercentageRates() throws Exception {
        //given
        subject.put("t1", 3d);
        subject.put("t2", 2d);
        subject.put("t3", 4d);

        //when
        Map<String,Double> rates = subject.getTranscriptPercentageRates();

        //then
        assertThat(rates.get("t1"), is(33.3));
        assertThat(rates.get("t2"), is(22.2));
        assertThat(rates.get("t3"), is(44.5));

    }
}
