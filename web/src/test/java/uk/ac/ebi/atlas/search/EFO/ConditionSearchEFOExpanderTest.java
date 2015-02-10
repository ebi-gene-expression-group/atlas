package uk.ac.ebi.atlas.search.EFO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ConditionSearchEFOExpanderTest {

    @Mock
    private EFOIdToTermMapper efoIdToTermMapperMock;

    private ConditionSearchEFOExpander subject = new ConditionSearchEFOExpander(efoIdToTermMapperMock);

    @Test
    public void nullTerm() {
        assertThat(subject.getIds(null), is(nullValue()));
    }

}