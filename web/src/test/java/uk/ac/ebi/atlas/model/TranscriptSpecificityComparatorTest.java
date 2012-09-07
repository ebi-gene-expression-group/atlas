package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptSpecificityComparatorTest {

    private TranscriptSpecificityComparator subject;

    @Mock
    private ExpressionLevel nullTranscriptSpecificity;

    @Mock
    private ExpressionLevel highestTranscriptSpecificity;

    @Mock
    private ExpressionLevel lowTranscriptSpecificity;

    @Mock
    private ExpressionLevel lowTranscriptSpecificityWithSmallerRPKM;


    @Before
    public void initExpressionLevels(){
        when(nullTranscriptSpecificity.getTranscriptSpecificity())
                .thenReturn(null);
        when(highestTranscriptSpecificity.getTranscriptSpecificity())
                .thenReturn(1);
        when(lowTranscriptSpecificity.getTranscriptSpecificity())
                .thenReturn(16);
        when(lowTranscriptSpecificity.getRpkm())
            .thenReturn(10D);
        when(lowTranscriptSpecificityWithSmallerRPKM.getTranscriptSpecificity())
            .thenReturn(16);
        when(lowTranscriptSpecificityWithSmallerRPKM.getRpkm())
            .thenReturn(0D);
        subject = new TranscriptSpecificityComparator();
    }

    @Before
    public void initSubject(){
        subject = new TranscriptSpecificityComparator();
    }

    @Test
    public void nullSpecificityShouldFollowLowerSpecificity(){
        //when
        int comparison = subject.compare(nullTranscriptSpecificity, lowTranscriptSpecificity);

        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity(){
        //when
        int comparison = subject.compare(lowTranscriptSpecificity, highestTranscriptSpecificity);

        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void highSpecificityShouldPreceedLowSpecificity(){
        //when
        int comparison = subject.compare(highestTranscriptSpecificity, lowTranscriptSpecificity);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void differentSpecificityShouldNotTriggerExpressionLevelComparator(){
        //when
        int comparison = subject.compare(highestTranscriptSpecificity, lowTranscriptSpecificity);

        //then
        verify(lowTranscriptSpecificityWithSmallerRPKM, never()).compareTo(lowTranscriptSpecificity);

    }

    @Test
    public void sameSpecificityWithSmallerRPKMShouldFollow(){
        //when
        int comparison = subject.compare(lowTranscriptSpecificityWithSmallerRPKM, lowTranscriptSpecificity);

        //then
        verify(lowTranscriptSpecificityWithSmallerRPKM).compareTo(lowTranscriptSpecificity);

    }


}
