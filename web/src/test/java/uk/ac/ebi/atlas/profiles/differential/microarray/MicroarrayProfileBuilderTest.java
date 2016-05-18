
package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayProfileBuilderTest {

    private static final String DESIGN_ELEMENT_NAME = "designElementName";
    private static final String CONTRAST_NAME1 = "a";
    private static final String CONTRAST_NAME2 = "b";
    private static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "aGeneName";

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    @Mock
    MicroarrayRequestContext contextMock;

    @Mock
    MicroarrayExpression expressionMock;

    MicroarrayProfileReusableBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        SortedSet<Contrast> sortedSet = new TreeSet();
        sortedSet.add(contrastMock1);
        sortedSet.add(contrastMock2);

        when(expressionMock.isUnderExpressed()).thenReturn(true);

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(0.05);
        expressionFilter.setRegulation(Regulation.UP_DOWN);

        subject = new MicroarrayProfileReusableBuilder(expressionFilter);

    }

    @Test
    public void testCreate() throws Exception {
        MicroarrayProfileReusableBuilder builder = subject.beginNewInstance(GENE_ID, GENE_NAME, DESIGN_ELEMENT_NAME);
        builder.addExpression(expressionMock);
        MicroarrayProfile profile = builder.create();
        assertThat(profile.getDesignElementName(), is(DESIGN_ELEMENT_NAME));
        assertThat(profile.getId(), is(GENE_ID));
        assertThat(profile.getName(), is(GENE_NAME));
    }
}