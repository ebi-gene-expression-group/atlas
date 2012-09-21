package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranscriptExpressionsListTest {

    private static final String TRANSCRIPT_ID_1 = "T1";
    private static final String TRANSCRIPT_ID_3 = "T3";
    private static final String TRANSCRIPT_ID_4 = "T4";

    @Mock
    TranscriptExpression expression1;
    @Mock
    TranscriptExpression expression2;
    @Mock
    TranscriptExpression expression3;
    @Mock
    TranscriptExpression expression4;
    @Mock
    TranscriptExpression expression5;

    private TranscriptExpressionsList subject;

    @Before
    public void setUp() throws Exception {

        subject = new TranscriptExpressionsList(Lists.newArrayList(expression5, expression3, expression4, expression1, expression2));

    }

    @Test
    public void testGetTop() throws Exception {
        //when
        TranscriptExpressionsList topExpressions = subject.getTop(3);
        //then
        assertThat(topExpressions, hasSize(3));
        //and
        assertThat(topExpressions, contains(expression5, expression3, expression4));
    }

    @Test
    public void testGetTopWhenListIsSmallerThanRequestedAmountOfLevels() throws Exception {
        //when
        TranscriptExpressionsList topExpressions = subject.getTop(6);
        //then
        assertThat(topExpressions, hasSize(5));
        //and
        assertThat(topExpressions, contains(expression5, expression3, expression4, expression1, expression2));
    }

    @Test
    public void testGetTopWhenListIsEqualToRequestedAmountOfLevels() throws Exception {
        //when
        TranscriptExpressionsList topExpressions = subject.getTop(5);
        //then
        assertThat(topExpressions, hasSize(5));
        //and
        assertThat(topExpressions, contains(expression5, expression3, expression4, expression1, expression2));
    }

    @Test
    public void testGetRpkmValue() throws Exception {

    }

    @Test
    public void testGetDistinctTranscriptIds() throws Exception {
        when(expression1.getTranscriptId()).thenReturn(TRANSCRIPT_ID_1);
        when(expression2.getTranscriptId()).thenReturn(TRANSCRIPT_ID_1);
        when(expression3.getTranscriptId()).thenReturn(TRANSCRIPT_ID_4);
        when(expression4.getTranscriptId()).thenReturn(TRANSCRIPT_ID_3);
        when(expression5.getTranscriptId()).thenReturn(TRANSCRIPT_ID_4);

        //when
        Set<String> transcriptIds = subject.getDistinctTranscriptIds();
        //then
        assertThat(transcriptIds, contains("T4", "T3", "T1"));
    }

    @Test
    public void testGetDistinctOrganismParts() throws Exception {
        //given
        when(expression1.getTranscriptId()).thenReturn(TRANSCRIPT_ID_1);
        when(expression2.getTranscriptId()).thenReturn(TRANSCRIPT_ID_1);
        when(expression3.getTranscriptId()).thenReturn(TRANSCRIPT_ID_4);
        when(expression4.getTranscriptId()).thenReturn(TRANSCRIPT_ID_3);
        when(expression5.getTranscriptId()).thenReturn(TRANSCRIPT_ID_4);
        //and
        when(expression1.getOrganismPart()).thenReturn("brain");
        when(expression2.getOrganismPart()).thenReturn("blood");
        when(expression3.getOrganismPart()).thenReturn("bomb");
        when(expression4.getOrganismPart()).thenReturn("banana");
        when(expression5.getOrganismPart()).thenReturn("berrywhite");

        //when
        SortedSet<String> organismParts = subject.getDistinctOrganismParts(Sets.newHashSet("T3", "T4"));

        //then
        assertThat(organismParts, contains("banana", "berrywhite", "bomb"));

    }

    @Test
    public void getMaxRpkmShouldReturnNullForEmptyList() {
        //when
        subject = new TranscriptExpressionsList();
        //then
        assertThat(subject.getMaxFpkm(), is(nullValue()));
    }

    @Test
    public void getMaxRpkmTest() {
        //given
        when(expression1.getRpkm()).thenReturn(55d);
        when(expression2.getRpkm()).thenReturn(15d);
        when(expression3.getRpkm()).thenReturn(25d);
        when(expression4.getRpkm()).thenReturn(115d);
        when(expression5.getRpkm()).thenReturn(35d);
        //then
        assertThat(subject.getMaxFpkm(), is(115d));
    }

    @Test
    public void getMinRpkmShouldReturnNullForEmptyList() {
        //when
        subject = new TranscriptExpressionsList();
        //then
        assertThat(subject.getMinFpkm(), is(nullValue()));
    }

    @Test
    public void getMinRpkmTest() {
        //given
        when(expression1.getRpkm()).thenReturn(55d);
        when(expression2.getRpkm()).thenReturn(15d);
        when(expression3.getRpkm()).thenReturn(25d);
        when(expression4.getRpkm()).thenReturn(115d);
        when(expression5.getRpkm()).thenReturn(35d);
        //then
        assertThat(subject.getMinFpkm(), is(15d));
    }


}
