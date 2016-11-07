package uk.ac.ebi.atlas.profiles.writer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.io.*;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesTSVWriterTest {

    @Mock
    private ObjectInputStream<BaselineProfile> inputStreamMock;
    @Mock
    private BaselineRequestContext requestContextMock;
    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private BaselineExperiment experimentMock;
    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private Resource tsvFileMastheadTemplateResourceMock;

    private BaselineProfilesTSVWriter subject;

    private SortedSet<Factor> organismParts;

    @Before
    public void initMocks() {
        organismParts = Sets.newTreeSet(Sets.newHashSet(
                createFactorValue("adipose"),
                createFactorValue("brain"),
                createFactorValue("breast"),
                createFactorValue("liver"),
                createFactorValue("lung")));

        when(requestContextMock.getExperiment()).thenReturn(experimentMock);
        when(requestContextMock.getAllQueryFactors()).thenReturn(organismParts);
        when(requestContextMock.getGeneQuery()).thenReturn(SemanticQuery.create("geneQuery"));
        when(requestContextMock.getSelectedQueryFactors()).thenReturn(
                Sets.newHashSet(new Factor("type1", "value1"), new Factor("type2", "value2")));
        when(requestContextMock.getCutoff()).thenReturn(0.5D);

        FactorGroup fgAdipose = FactorSet.create(ImmutableMap.of("ORG", "adipose"));
        FactorGroup fgBrain = FactorSet.create(ImmutableMap.of("ORG", "brain"));
        FactorGroup fgBreast = FactorSet.create(ImmutableMap.of("ORG", "breast"));
        FactorGroup fgLiver = FactorSet.create(ImmutableMap.of("ORG", "liver"));
        FactorGroup fgLung = FactorSet.create(ImmutableMap.of("ORG", "lung"));

        BaselineExpression expressionAdipose0 = new BaselineExpression(0d, fgAdipose);
        BaselineExpression expressionBrain0 = new BaselineExpression(0d, fgBrain);
        BaselineExpression expressionBreast0 = new BaselineExpression(0d, fgBreast);
        BaselineExpression expressionLiver0 = new BaselineExpression(0d, fgLiver);
        BaselineExpression expressionLung0 = new BaselineExpression(0d, fgLung);

        BaselineExpression expressionBrain = new BaselineExpression(0.11d, fgBrain);
        BaselineExpression expressionLung = new BaselineExpression(9d, fgLung);
        BaselineExpression expressionLiver = new BaselineExpression(21.12d, fgLiver);

        BaselineProfile baselineProfile1 = new BaselineProfile("GI1", "GN1");
        baselineProfile1.add("ORG", expressionAdipose0);
        baselineProfile1.add("ORG", expressionBrain);
        baselineProfile1.add("ORG", expressionBreast0);
        baselineProfile1.add("ORG", expressionLiver0);
        baselineProfile1.add("ORG", expressionLung);

        BaselineProfile baselineProfile2 = new BaselineProfile("GI2", "GN2");
        baselineProfile2.add("ORG", expressionAdipose0);
        baselineProfile2.add("ORG", expressionBrain0);
        baselineProfile2.add("ORG", expressionBreast0);
        baselineProfile2.add("ORG", expressionLiver);
        baselineProfile2.add("ORG", expressionLung0);

        when(inputStreamMock.readNext()).thenReturn(baselineProfile1)
                .thenReturn(baselineProfile2)
                .thenReturn(null);

        when(experimentalFactorsMock.getFactors(anyString())).thenReturn(ImmutableSortedSet.copyOf(organismParts));
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
    }

    @Before
    public void initSubject() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("{0} {1} {2} {3} {4} {5}".getBytes());
        when(tsvFileMastheadTemplateResourceMock.getInputStream()).thenReturn(inputStream);

        subject = new BaselineProfilesTSVWriter(new CsvWriterFactory(), tsvFileMastheadTemplateResourceMock);
        subject.setResponseWriter(printWriterMock);
    }

    @Test
    public void expressionLevels() throws Exception {
        long count = subject.write(inputStreamMock, organismParts, requestContextMock, false);

        verify(printWriterMock).write("Gene ID\tGene Name\tadipose\tbrain\tbreast\tliver\tlung\n", 0, 50);
        verify(printWriterMock).write("GI1\tGN1\t0\t0.11\t0\t0\t9\n", 0, 21);
        verify(printWriterMock).write("GI2\tGN2\t0\t0\t0\t21.12\t0\n", 0, 22);

        assertThat(count, is(2L));
    }

    @Test
    public void buildCsvHeadersTest() {
        String[] headers = subject.buildCsvColumnHeaders(organismParts, requestContextMock, false);
        assertThat(headers, is(new String[]{"Gene ID", "Gene Name", "adipose", "brain", "breast", "liver", "lung"}));
    }

    @Test
    public void buildCsvRowTest() {
        String[] headers = subject.buildCsvRow(new String[]{"A", "B"}, new String[]{"C", "D"});
        assertThat(headers, is(new String[]{"A", "B", "C", "D"}));
    }

    private Factor createFactorValue(String value) {
        return new Factor("ORG", value);
    }
}
