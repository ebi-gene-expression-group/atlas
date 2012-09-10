package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionLevelConstructorsTest {

    private TranscriptExpressionLevel subject;

    @Mock
    private FactorValue factorValue1;
    @Mock
    private FactorValue factorValue2;

    private Set<FactorValue> factorValues = new HashSet<>();

    @Before
    public void initializeFactorValues() {
        factorValues.add(factorValue1);
        factorValues.add(factorValue2);
    }

    @Test
    public void constructorShouldHandleNonEmptyFactorValuesCollection() throws Exception {

        //given
        subject = new TranscriptExpressionLevel("id1", 1, new ExperimentRun("RUN_ACCESSION", factorValues));

        //then
        assertThat(subject.getFactorValues().size(), is(2));

    }

    @Test
    public void constructorShouldHandleEmptyFactorValuesCollection() throws Exception {

        //given
        subject = new TranscriptExpressionLevel("id1", 1, new ExperimentRun("RUN_ACCESSION"));

        //then
        assertThat(subject.getFactorValues().size(), is(0));

    }

    @Test
    public void constructorsShouldHandleNullFactorValuesCollection() throws Exception {

        //given
        subject = new TranscriptExpressionLevel("id1", 1, new ExperimentRun("RUN_ACCESSION"));

        //then
        assertThat(subject.getFactorValues().size(), is(0));


    }

}
