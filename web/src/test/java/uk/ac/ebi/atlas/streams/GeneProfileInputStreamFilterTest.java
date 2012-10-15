package uk.ac.ebi.atlas.streams;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileInputStreamFilterTest {

    public static final String GENE1 = "Gene1";
    public static final String GENE2 = "Gene2";

    @Mock
    private ObjectInputStream<GeneProfile> inputStreamMock;

    @Mock
    private GeneProfile gene1ProfileMock;

    @Mock
    private GeneProfile gene3ProfileMock;

    private Set<String> geneIDs = Sets.newHashSet(GENE1, GENE2);

    private GeneProfileInputStreamFilter subject;

    @Before
    public void initMocks(){
        when(gene1ProfileMock.getGeneId()).thenReturn(GENE2);
        when(gene3ProfileMock.getGeneId()).thenReturn("UNACCEPTABLE_GENE");
    }

    @Before
    public void initSubject(){
        subject = new GeneProfileInputStreamFilter(inputStreamMock, geneIDs);
    }

    @Test
    public void acceptanceCriteriaTestShouldBeBasedOnGeneIDsSet(){
        //given
        Predicate acceptancePredicate = subject.getAcceptanceCriteria();

        //then
        assertThat(acceptancePredicate.apply(gene1ProfileMock), is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(false));
    }

    @Test
    public void acceptanceCriteriaTestAlwaysSucceedsWhenTheGeneIDsSetIsEmpty(){
        //given
        subject = new GeneProfileInputStreamFilter(inputStreamMock, new HashSet<String>());
        //and
        Predicate acceptancePredicate = subject.getAcceptanceCriteria();

        //then
        assertThat(acceptancePredicate.apply(gene1ProfileMock), is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(true));
    }


}
