//package uk.ac.ebi.atlas.model;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.Set;
//import java.util.SortedSet;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GeneProfilesListTest {
//
//    private static final String GENE_ID_1 = "T1";
//    private static final String GENE_ID_3 = "T3";
//    private static final String GENE_ID_4 = "T4";
//
//    @Mock
//    GeneExpression expression1;
//    @Mock
//    GeneExpression expression2;
//    @Mock
//    GeneExpression expression3;
//    @Mock
//    GeneExpression expression4;
//    @Mock
//    GeneExpression expression5;
//
//    private GeneProfilesList subject;
//
//    @Before
//    public void setUp() throws Exception {
//
//        subject = new GeneProfilesList(Lists.newArrayList(expression5, expression3, expression4, expression1, expression2));
//
//    }
//
//    @Test
//    public void testGetTop() throws Exception {
//        //when
//        GeneProfilesList topProfiles = subject.getTop(3);
//        //then
//        assertThat(topProfiles, hasSize(3));
//        //and
//        assertThat(topProfiles, contains(expression5, expression3, expression4));
//    }
//
//    @Test
//    public void testGetTopWhenListIsSmallerThanRequestedAmountOfExpressions() throws Exception {
//        //when
//        GeneProfilesList topProfiles = subject.getTop(6);
//        //then
//        assertThat(topProfiles, hasSize(5));
//        //and
//        assertThat(topProfiles, contains(expression5, expression3, expression4, expression1, expression2));
//    }
//
//    @Test
//    public void testGetTopWhenListIsEqualToRequestedAmountOfExpressions() throws Exception {
//        //when
//        GeneProfilesList topProfiles = subject.getTop(5);
//        //then
//        assertThat(topProfiles, hasSize(5));
//        //and
//        assertThat(topProfiles, contains(expression5, expression3, expression4, expression1, expression2));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void sublistShouldThrowIllegalArguemntExceptionWhenUpperIndexIsLessThanZero() throws Exception {
//        //when
//        GeneProfilesList e = subject.subList(0, -1);
//        assertThat(e, is(nullValue()));
//    }
//
//    public void sublistTest() throws Exception {
//        //when
//        GeneProfilesList geneProfiles = subject.subList(0, 3);
//        //then
//        assertThat(geneProfiles, contains(expression5, expression3, expression4));
//    }
//
//    public void sublistShouldReturnEntireListWhenTopIndexLargerThanListSize() throws Exception {
//        //when
//        GeneProfilesList geneProfiles = subject.subList(0, 7);
//        //then
//        assertThat(geneProfiles, hasSize(5));
//    }
//
//    @Test
//    public void testGetDistinctGeneIds() throws Exception {
//        when(expression1.getGeneId()).thenReturn(GENE_ID_1);
//        when(expression2.getGeneId()).thenReturn(GENE_ID_1);
//        when(expression3.getGeneId()).thenReturn(GENE_ID_4);
//        when(expression4.getGeneId()).thenReturn(GENE_ID_3);
//        when(expression5.getGeneId()).thenReturn(GENE_ID_4);
//
//        //when
//        Set<String> geneIds = subject.getDistinctGeneIds();
//        //then
//        assertThat(geneIds, contains("T4", "T3", "T1"));
//    }
//
//    @Test
//    public void testGetDistinctOrganismParts() throws Exception {
//        //given
//        when(expression1.getGeneId()).thenReturn(GENE_ID_1);
//        when(expression2.getGeneId()).thenReturn(GENE_ID_1);
//        when(expression3.getGeneId()).thenReturn(GENE_ID_4);
//        when(expression4.getGeneId()).thenReturn(GENE_ID_3);
//        when(expression5.getGeneId()).thenReturn(GENE_ID_4);
//        //and
//        when(expression1.getOrganismPart()).thenReturn("brain");
//        when(expression2.getOrganismPart()).thenReturn("blood");
//        when(expression3.getOrganismPart()).thenReturn("bomb");
//        when(expression4.getOrganismPart()).thenReturn("banana");
//        when(expression5.getOrganismPart()).thenReturn("berrywhite");
//
//        //when
//        SortedSet<String> organismParts = subject.getDistinctOrganismParts(Sets.newHashSet("T3", "T4"));
//
//        //then
//        assertThat(organismParts, contains("banana", "berrywhite", "bomb"));
//
//    }
//
//    @Test
//    public void getMaxExpressionLevelShouldReturnNullForEmptyList() {
//        //when
//        subject = new GeneProfilesList();
//        //then
//        assertThat(subject.getMaxExpressionLevel(), is(nullValue()));
//    }
//
//    @Test
//    public void getMaxExpressionLevelTest() {
//        //given
//        when(expression1.getLevel()).thenReturn(55d);
//        when(expression2.getLevel()).thenReturn(15d);
//        when(expression3.getLevel()).thenReturn(25d);
//        when(expression4.getLevel()).thenReturn(115d);
//        when(expression5.getLevel()).thenReturn(35d);
//        //then
//        assertThat(subject.getMaxExpressionLevel(), is(115d));
//    }
//
//    @Test
//    public void getMinExpressionLevelShouldReturnNullForEmptyList() {
//        //when
//        subject = new GeneProfilesList();
//        //then
//        assertThat(subject.getMinExpressionLevel(), is(nullValue()));
//    }
//
//    @Test
//    public void getMinExpressionLevelTest() {
//        //given
//        when(expression1.getLevel()).thenReturn(55d);
//        when(expression2.getLevel()).thenReturn(15d);
//        when(expression3.getLevel()).thenReturn(25d);
//        when(expression4.getLevel()).thenReturn(115d);
//        when(expression5.getLevel()).thenReturn(35d);
//        //then
//        assertThat(subject.getMinExpressionLevel(), is(15d));
//    }
//
//
//}
