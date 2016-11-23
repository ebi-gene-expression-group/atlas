package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqProfileBuilderTest {

    private static final String CONTRAST_NAME1 = "a";
    private static final String CONTRAST_NAME2 = "b";
    private static final String GENE_ID = "geneId";
    private static final String GENE_NAME = "aGeneName";

    @Mock
    Contrast contrastMock1;

    @Mock
    Contrast contrastMock2;

    @Mock
    DifferentialExpression expressionMock;

    private RnaSeqProfileReusableBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        when(expressionMock.isUnderExpressed()).thenReturn(true);

        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(0.05);
        expressionFilter.setRegulation(Regulation.UP_DOWN);

        subject = new RnaSeqProfileReusableBuilder(expressionFilter);
    }

    @Test
    public void testCreate() throws Exception {
        RnaSeqProfileReusableBuilder builder = subject.beginNewInstance(GENE_ID, GENE_NAME);
        builder.addExpression(expressionMock);
        DifferentialProfile profile = builder.create();
        assertThat(profile.getId(), is(GENE_ID));
    }
}