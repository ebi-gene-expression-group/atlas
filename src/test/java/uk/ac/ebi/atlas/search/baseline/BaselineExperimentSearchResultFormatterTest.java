package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentSearchResultFormatterTest {

    private static final String HEART = "heart";
    private static final String LIVER = "liver";
    private static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String EXP1 = "EXP1";
    public static final String EXP2 = "EXP2";
    private Factor heartFactor = new Factor("organism part", HEART);
    private Factor liverFactor = new Factor("organism part", LIVER);
    private SortedSet<Factor> factors = ImmutableSortedSet.of(heartFactor, liverFactor);

    @Mock
    private BaselineExperiment exp1;

    @Mock
    private BaselineExperiment exp2;

    @Mock
    private ExperimentalFactors experimentalFactors;

    BaselineExperimentProfile profile1;
    BaselineExperimentProfile profile2;

    private BaselineExperimentSearchResultFormatter subject;

    @Before
    public void before() {
        FactorGroup noFilterFactors = new FactorSet();

        when(exp1.getAccession()).thenReturn(EXP1);
        when(exp2.getAccession()).thenReturn(EXP2);

        when(exp1.getExperimentalFactors()).thenReturn(experimentalFactors);
        when(exp2.getExperimentalFactors()).thenReturn(experimentalFactors);
        when(experimentalFactors.getComplementFactors(Mockito.any(FactorGroup.class))).thenReturn(new TreeSet<Factor>());

        BaselineExperimentSlice slice1 = BaselineExperimentSlice.create(exp1, noFilterFactors);
        BaselineExperimentSlice slice2 = BaselineExperimentSlice.create(exp2, noFilterFactors);

        profile1 = new BaselineExperimentProfile(slice1);
        profile2 = new BaselineExperimentProfile(slice2);
        BaselineExperimentProfilesList experimentProfiles = new BaselineExperimentProfilesList(ImmutableList.of(profile1, profile2));

        subject = new BaselineExperimentSearchResultFormatter(experimentProfiles, factors);
    }

    @Test
    public void headers() {
        profile1.add(ORGANISM_PART, new BaselineExpression(1D, new FactorSet(heartFactor)));
        profile2.add(ORGANISM_PART, new BaselineExpression(2D, new FactorSet(liverFactor)));

        assertThat(subject.getHeaders(), is(new String[]{"Experiment", HEART, LIVER}));
    }

    @Test
    public void rows() {
        profile1.add(ORGANISM_PART, new BaselineExpression(1D, new FactorSet(heartFactor)));
        profile2.add(ORGANISM_PART, new BaselineExpression(2D, new FactorSet(liverFactor)));

        Iterator<String[]> iterator = subject.iterator();
        assertThat(iterator.next(), is(new String[]{EXP1, "1", ""}));
        assertThat(iterator.next(), is(new String[]{EXP2, "", "2"}));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void rowWithNoTissue() {
        profile1.add(ORGANISM_PART, new BaselineExpression("NT", new FactorSet(heartFactor)));
        profile1.add(ORGANISM_PART, new BaselineExpression(2D, new FactorSet(liverFactor)));

        Iterator<String[]> iterator = subject.iterator();
        assertThat(iterator.next(), is(new String[]{EXP1, "NA", "2"}));
    }

}
