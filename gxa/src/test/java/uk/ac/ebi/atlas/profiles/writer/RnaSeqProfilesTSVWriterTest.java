
package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqProfilesTSVWriterTest {

    @Mock
    private RnaSeqProfile geneProfileMock;

    @Mock
    private DifferentialExpression expressionMock;

    @Mock
    private RnaSeqRequestContext rnaSeqRequestContextMock;

    private RnaSeqProfilesTSVWriter subject;


    @Before
    public void initMocks() {
        when(geneProfileMock.getExpression(any(Contrast.class))).thenReturn(expressionMock);

        subject = new RnaSeqProfilesTSVWriter(new CsvWriterFactory());
    }

    @Test
    public void buildConditionExpressionsHeaders() throws Exception {
        Contrast contrast1 = mock(Contrast.class);
        when(contrast1.getDisplayName()).thenReturn("cond1");
        Contrast contrast2 = mock(Contrast.class);
        when(contrast2.getDisplayName()).thenReturn("cond2");

        TreeSet<Contrast> conditions = Sets.newTreeSet();
        conditions.add(contrast1);
        conditions.add(contrast2);
        String[] columnNames = subject.getConditionColumnHeaders(conditions);
        assertThat(columnNames.length, is(4));
        assertThat(columnNames, arrayContaining("cond1.p-value", "cond1.log2foldchange", "cond2.p-value", "cond2.log2foldchange"));
    }

    @Test
    public void testBuildExpressionsRow() throws Exception {

        //given
        when(expressionMock.getFoldChange()).thenReturn(-0.978932452151424);
        when(expressionMock.getPValue()).thenReturn(0.134707651014487);

        SortedSet<Contrast> contrasts = new TreeSet<>();
        contrasts.add(new Contrast("id1", null, null, null, "name"));

        //when
        String[] expressions = subject.extractConditionLevels(geneProfileMock, contrasts);

        //then
        assertThat(expressions.length, Matchers.is(2));
        assertThat(expressions, is(new String[]{"0.134707651014487", "-0.978932452151424"}));

    }

    @Test
    public void testGetValueToString() throws Exception {
        assertThat(subject.getValueToString(0.134707651014487), is("0.134707651014487"));
        assertThat(subject.getValueToString(Double.POSITIVE_INFINITY), is("NA"));
        assertThat(subject.getValueToString(Double.NEGATIVE_INFINITY), is("NA"));

    }

}
