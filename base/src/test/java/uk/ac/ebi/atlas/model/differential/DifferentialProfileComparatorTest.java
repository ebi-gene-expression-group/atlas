package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileDeserializer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileComparatorTest {

    private DifferentialProfileComparator subject;

    @Mock
    private Contrast contrastMock1;
    @Mock
    private Contrast contrastMock2;
    @Mock
    private Contrast contrastMock3;

    private Set<Contrast> allContrasts;
    private Set<Contrast> selectedContrasts;
    private Set<Contrast> nonSelectedContrasts;

    @Mock
    private DifferentialProfile profileMock1;

    @Mock
    private DifferentialProfile profileMock2;

    @Mock
    private Predicate<DifferentialExpression> anyExpression;


    @Before
    public void init() {
        when(anyExpression.apply(any(DifferentialExpression.class))).thenReturn(true);

        allContrasts = Sets.newHashSet(contrastMock1, contrastMock2, contrastMock3);
        selectedContrasts = Sets.newHashSet(contrastMock1);
        nonSelectedContrasts = Sets.newHashSet(contrastMock2, contrastMock3);

        subject = new DifferentialProfileComparator(true, selectedContrasts, allContrasts, Regulation.UP);
    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        subject = new DifferentialProfileComparator(true, null, allContrasts, Regulation.UP);

        when(profileMock1.getSpecificity(Regulation.UP)).thenReturn(1);
        when(profileMock2.getSpecificity(Regulation.UP)).thenReturn(2);

        //then
        int comparison = subject.compare(profileMock1, profileMock2);

        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOn() throws Exception {
        //when
        when(profileMock1.getStrongestExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.04);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(1.5999999999999999));

        //when
        when(profileMock1.getStrongestExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.05D);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));

        //when
        when(profileMock1.getStrongestExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.02);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.01);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOnWhenAllContrastsAreSelected() throws Exception {
        subject = new DifferentialProfileComparator(true, selectedContrasts, allContrasts, Regulation.UP);

        //when
        when(profileMock1.getStrongestExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.05);
        //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);
        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }



    @Test
    public void mocksequence_NonSpecific_AllContrasts() {

        DifferentialProfile differentialProfileMock1 = Mockito.mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock2 = Mockito.mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock3 = Mockito.mock(DifferentialProfile.class);

        //given
        when(differentialProfileMock1.getName()).thenReturn("1");
        when(differentialProfileMock1.getAverageExpressionLevelOn(new HashSet<Contrast>())).thenReturn(3D);
        when(differentialProfileMock1.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);

        when(differentialProfileMock1.getName()).thenReturn("2");
        when(differentialProfileMock2.getAverageExpressionLevelOn(new HashSet<Contrast>())).thenReturn(5D);
        when(differentialProfileMock2.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);

        when(differentialProfileMock1.getName()).thenReturn("3");
        when(differentialProfileMock3.getAverageExpressionLevelOn(new HashSet<Contrast>())).thenReturn(2D);
        when(differentialProfileMock3.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);

        subject = new DifferentialProfileComparator(false, Collections.emptySet(), Collections.emptySet(), Regulation.UP_DOWN);

        //when
        SortedSet<DifferentialProfile> profiles = new TreeSet<DifferentialProfile>(subject);
        profiles.add(differentialProfileMock1);
        profiles.add(differentialProfileMock2);
        profiles.add(differentialProfileMock3);

        //then
        Iterator<DifferentialProfile> profilesIterator = profiles.iterator();

        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock2));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock1));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock3));

    }


    private static final String T_STAT_IGNORED = "0";
    private static final String P_VALUE_IGNORED = "999";
    private static final String FOLD_CHANGE_IGNORED = "0";

    public static final String FOLD_CHANGE_20 = "20";
    public static final String FOLD_CHANGE_10 = "10";
    public static final String FOLD_CHANGE_7 = "7";
    public static final String FOLD_CHANGE_5 = "5";
    public static final String FOLD_CHANGE_3 = "3";
    public static final String FOLD_CHANGE_1 = "0.1";

    private static final String P_VALUE_0_DOT_1 = "0.1";
    private static final String P_VALUE_0_DOT_2 = "0.2";
    private static final String GENE_1 = "1";
    private static final String GENE_2 = "2";
    private static final String GENE_3 = "3";
    private static final String GENE_4 = "4";
    private static final String GENE_5 = "5";
    private static final String GENE_6 = "6";

    private static final String DESIGN_ELEMENT = "design_element";

    String specificOneContrast                    = Joiner.on("\t").join(new String[] {GENE_1, GENE_1, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_1,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    String twoContrastHighFoldChange              = Joiner.on("\t").join(new String[] {GENE_2, GENE_2, DESIGN_ELEMENT, P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_20,   P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_10});
    String twoContrastHighFoldChangeOtherContrast = Joiner.on("\t").join(new String[] {GENE_6, GENE_6, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,   P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_20});
    String twoContrastLowFoldChange               = Joiner.on("\t").join(new String[] {GENE_3, GENE_3, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,   P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10});
    String twoContrastSameFoldChangeLowPValue     = Joiner.on("\t").join(new String[] {GENE_4, GENE_4, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5});
    String twoContrastSameFoldChangeHighPValue    = Joiner.on("\t").join(new String[] {GENE_5, GENE_5, DESIGN_ELEMENT, P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5});

    String sequenceLines = Joiner.on("\n").join(new String[] {specificOneContrast, twoContrastHighFoldChangeOtherContrast, twoContrastSameFoldChangeHighPValue, twoContrastSameFoldChangeLowPValue, twoContrastLowFoldChange, twoContrastHighFoldChange});
    ImmutableList<MicroarrayProfile> sequenceProfiles;

    IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff().setPValueCutoff(1).setRegulation(Regulation.UP_DOWN).setFoldChangeCutOff(0);

    @Before
    public void loadProfiles() {
        MicroarrayProfileDeserializer deserializer = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1, contrastMock2), expressionFilter);
        sequenceProfiles = deserializer.create(sequenceLines.split("\n"));
        sequenceProfiles2 = deserializer.create(sequenceLines2.split("\n"));
    }

    @Test
    public void sequence_Specific_AllContrasts() {
        subject = new DifferentialProfileComparator(true, Collections.emptySet(), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_6, GENE_2, GENE_3, GENE_4, GENE_5));
    }

    @Test
    public void sequence_Specific_SelectedContrast() {
        subject = new DifferentialProfileComparator(true, Sets.newHashSet(contrastMock1), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3, GENE_4, GENE_5, GENE_6));
    }

    @Test
    public void sequence_NonSpecific_AllContrasts() {
        subject = new DifferentialProfileComparator(false, Collections.emptySet(), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_6, GENE_2, GENE_3, GENE_4, GENE_5, GENE_1));
    }

    @Test
    public void sequence_NonSpecific_SelectedContrast() {
        subject = new DifferentialProfileComparator(false, Sets.newHashSet(contrastMock1), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_2, GENE_3, GENE_6, GENE_4, GENE_5, GENE_1));
    }

    String oneContrastHigh = Joiner.on("\t").join(new String[] {GENE_1, GENE_1, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_7,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    String twoContrastMid = Joiner.on("\t").join(new String[] {GENE_2, GENE_2, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3,    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3});
    String oneContrastLow = Joiner.on("\t").join(new String[] {GENE_3, GENE_3, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});

    String sequenceLines2 = Joiner.on("\n").join(new String[] {oneContrastHigh, twoContrastMid, oneContrastLow});
    ImmutableList<MicroarrayProfile> sequenceProfiles2;

    //mimics AT3G48131, DML1, F14M2.2 on http://localhost:8080/gxa/experiments/E-GEOD-38400?_specific=on
    @Test
    public void sequence2_NonSpecific_Averaging_Of_Multiple_Contrasts_Vs_Single_Contrast() {
        subject = new DifferentialProfileComparator(false, Collections.emptySet(), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles2, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3));
    }

    private DifferentialProfile[] sortProfiles(ImmutableList<MicroarrayProfile> sequenceProfiles, DifferentialProfileComparator comparator) {
        SortedSet<MicroarrayProfile> sortedProfiles = new TreeSet<MicroarrayProfile>(comparator);
        sortedProfiles.addAll(sequenceProfiles);
        return sortedProfiles.toArray(new MicroarrayProfile[sequenceProfiles.size()]);
    }


    private String[] extractGeneNames(DifferentialProfile[] profiles) {
        String[] geneNames = new String[profiles.length];

        for (int i = 0; i < profiles.length; i++) {
            geneNames[i] = profiles[i].getName();
        }

        return geneNames;
    }

}
