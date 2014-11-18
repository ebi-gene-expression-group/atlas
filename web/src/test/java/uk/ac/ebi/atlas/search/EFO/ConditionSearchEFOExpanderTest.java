package uk.ac.ebi.atlas.search.EFO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.dao.EFOTreeDAO;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ConditionSearchEFOExpanderTest {

    @Mock
    private EFOTreeDAO efoTreeDAOMock;

    private ConditionSearchEFOExpander subject = new ConditionSearchEFOExpander(efoTreeDAOMock);

    @Test
    public void nullTerm() {
        assertThat(subject.fetchExpandedTermWithEFOChildren(null), is(nullValue()));
    }

}