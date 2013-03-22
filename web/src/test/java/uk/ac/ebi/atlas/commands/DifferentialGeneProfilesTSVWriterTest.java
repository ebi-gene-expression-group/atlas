package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialGeneProfilesTSVWriterTest {

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private DifferentialProfile geneProfileMock;

    @Mock
    private DifferentialExpression expressionMock;

    private DifferentialGeneProfilesTSVWriter subject;


    @Before
    public void initMocks() {
        when(geneProfileMock.getExpression(any(Contrast.class))).thenReturn(expressionMock);

        subject = new DifferentialGeneProfilesTSVWriter(new NumberUtils(), geneNamesProviderMock);
    }

    @Test
    public void testBuildColumnNames() throws Exception {
        List<String> columnNames = subject.buildColumnNames(Sets.newTreeSet(Sets.newHashSet("cond1", "cond2")));
        assertThat(columnNames.size(), is(4));
        assertThat(columnNames, contains("cond1.p-value", "cond1.log2foldchange", "cond2.p-value", "cond2.log2foldchange"));
    }

    @Test
    public void testBuildExpressionsRow() throws Exception {

        //given
        when(expressionMock.getFoldChange()).thenReturn(-0.978932452151424);
        when(expressionMock.getLevel()).thenReturn(0.134707651014487);

        SortedSet<Contrast> contrasts = new TreeSet<>(Contrast.orderByDisplayName());
        contrasts.add(new Contrast("id1", null, null, "name"));

        //when
        String[] expressions = subject.buildExpressionsRow(geneProfileMock, contrasts);

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
