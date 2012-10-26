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
public class GeneExpressionConstructorsTest {

    public static final String RUN_ACCESSION = "RUN_ACCESSION";

    private GeneExpression subject;

    @Mock
    private FactorValue factorValue1;
    @Mock
    private FactorValue factorValue2;

    @Test
    public void constructorShouldHandleNonEmptyFactorValuesCollection() throws Exception {

        //given
        subject = new GeneExpression("id1", 1, new ExperimentRun(RUN_ACCESSION)
                                                    .addFactorValue("NAME_1", "VALUE_1")
                                                    .addFactorValue("NAME_2", "VALUE_2"));

        //then
        assertThat(subject.getFactorValues().size(), is(2));

    }

    @Test
    public void constructorShouldHandleEmptyFactorValuesCollection() throws Exception {

        //given
        subject = new GeneExpression("id1", 1, new ExperimentRun(RUN_ACCESSION));

        //then
        assertThat(subject.getFactorValues().size(), is(0));

    }


}
