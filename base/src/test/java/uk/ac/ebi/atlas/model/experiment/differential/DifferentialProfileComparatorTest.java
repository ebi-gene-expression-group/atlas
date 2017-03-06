package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactoryTest;
import uk.ac.ebi.atlas.profiles.tsv.MicroarrayExpressionsRowDeserializer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileComparatorTest {

    
    
    List<Contrast> mockContrasts = ContrastTest.get(3);

    Set<Contrast> allContrasts = ImmutableSet.copyOf(mockContrasts);
    Set<Contrast> selectedContrasts = ImmutableSet.copyOf(mockContrasts.subList(0,1));
    Set<Contrast> nonSelectedContrasts = ImmutableSet.copyOf(mockContrasts.subList(1,3));

    DifferentialProfileComparator<DifferentialProfile> subject = new DifferentialProfileComparator<>(true, selectedContrasts, allContrasts, Regulation.UP);

    @Mock
    DifferentialProfile<DifferentialExpression> profileMock1;

    @Mock
    DifferentialProfile<DifferentialExpression> profileMock2;

    Predicate<DifferentialExpression> anyExpression = Predicates.alwaysTrue();

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        subject = new DifferentialProfileComparator<>(true, allContrasts, allContrasts, Regulation.UP);

        when(profileMock1.getSpecificity(Regulation.UP)).thenReturn(1);
        when(profileMock2.getSpecificity(Regulation.UP)).thenReturn(2);

        //then
        int comparison = subject.compare(profileMock1, profileMock2);

        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOn() throws Exception {
        //when
        when(profileMock1.getMaxExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.04);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(1.5999999999999999));

        //when
        when(profileMock1.getMaxExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.05D);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));

        //when
        when(profileMock1.getMaxExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.02);
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.01);

        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOnWhenAllContrastsAreSelected() throws Exception {
        subject = new DifferentialProfileComparator<>(true, selectedContrasts, allContrasts, Regulation.UP);

        //when
        when(profileMock1.getMaxExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.05);
        //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);
        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }



    @Test
    public void mocksequence_NonSpecific_AllContrasts() {

        DifferentialProfile differentialProfileMock1 = mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock2 = mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock3 = mock(DifferentialProfile.class);

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

        subject = new DifferentialProfileComparator<>(false, Collections.<Contrast>emptySet(), Collections.<Contrast>emptySet(), Regulation.UP_DOWN);

        //when
        SortedSet<DifferentialProfile> profiles = new TreeSet<>(subject);
        profiles.add(differentialProfileMock1);
        profiles.add(differentialProfileMock2);
        profiles.add(differentialProfileMock3);

        //then
        Iterator<DifferentialProfile> profilesIterator = profiles.iterator();

        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock2));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock1));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock3));

    }


    static final String T_STAT_IGNORED = "0";
    static final String P_VALUE_IGNORED = "999";
    static final String FOLD_CHANGE_IGNORED = "0";

    static final String FOLD_CHANGE_20 = "20";
    static final String FOLD_CHANGE_10 = "10";
    static final String FOLD_CHANGE_7 = "7";
    static final String FOLD_CHANGE_5 = "5";
    static final String FOLD_CHANGE_3 = "3";
    static final String FOLD_CHANGE_1 = "0.1";

    static final String P_VALUE_0_DOT_1 = "0.1";
    static final String P_VALUE_0_DOT_2 = "0.2";
    static final String GENE_1 = "1";
    static final String GENE_2 = "2";
    static final String GENE_3 = "3";
    static final String GENE_4 = "4";
    static final String GENE_5 = "5";
    static final String GENE_6 = "6";

    static final String DESIGN_ELEMENT = "design_element";
    String header                                 = Joiner.on("\t").join(new String[] {"gene name", "gene id", "design element", "contrast_1.p-value", "contrast_1.t-stat", "contrast_1.fold-change",    "contrast_2.p-value", "contrast_2.t-stat", "contrast_2.fold-change"});
    String specificOneContrast                    = Joiner.on("\t").join(new String[] {GENE_1, GENE_1, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_1,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    String twoContrastHighFoldChange              = Joiner.on("\t").join(new String[] {GENE_2, GENE_2, DESIGN_ELEMENT, P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_20,   P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_10});
    String twoContrastHighFoldChangeOtherContrast = Joiner.on("\t").join(new String[] {GENE_6, GENE_6, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,   P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_20});
    String twoContrastLowFoldChange               = Joiner.on("\t").join(new String[] {GENE_3, GENE_3, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,   P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10});
    String twoContrastSameFoldChangeLowPValue     = Joiner.on("\t").join(new String[] {GENE_4, GENE_4, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5});
    String twoContrastSameFoldChangeHighPValue    = Joiner.on("\t").join(new String[] {GENE_5, GENE_5, DESIGN_ELEMENT, P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5});

    List<String> sequenceLines = ImmutableList.of(header, specificOneContrast, twoContrastHighFoldChangeOtherContrast,
            twoContrastSameFoldChangeHighPValue, twoContrastSameFoldChangeLowPValue, twoContrastLowFoldChange,
            twoContrastHighFoldChange);

    List<Contrast> contrasts = ContrastTest.get(6);


    List<MicroarrayProfile> sequenceProfiles = MicroarrayProfileStreamFactoryTest.loadProfiles(contrasts, sequenceLines);

    @Test
    public void sequence_Specific_AllContrasts() {
        subject = new DifferentialProfileComparator<>(true, contrasts, contrasts, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_6, GENE_2, GENE_3, GENE_4, GENE_5));
    }

    @Test
    public void sequence_Specific_SelectedContrast() {
        subject = new DifferentialProfileComparator<>(true, contrasts.subList(0,1), contrasts,
                Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3, GENE_4, GENE_5, GENE_6));
    }

    @Test
    public void sequence_NonSpecific_AllContrasts() {
        subject = new DifferentialProfileComparator<>(false, contrasts ,contrasts, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_6, GENE_2, GENE_3, GENE_4, GENE_5, GENE_1));
    }

    @Test
    public void sequence_NonSpecific_SelectedContrast() {
        subject = new DifferentialProfileComparator<>(false, contrasts.subList(0,1), contrasts, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_2, GENE_3, GENE_6, GENE_4, GENE_5, GENE_1));
    }

    String oneContrastHigh = Joiner.on("\t").join(new String[] {GENE_1, GENE_1, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_7,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    String twoContrastMid = Joiner.on("\t").join(new String[] {GENE_2, GENE_2, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3,    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3});
    String oneContrastLow = Joiner.on("\t").join(new String[] {GENE_3, GENE_3, DESIGN_ELEMENT, P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});

    GeneProfilesList<MicroarrayProfile> sequenceProfiles2 =
            MicroarrayProfileStreamFactoryTest.loadProfiles(contrasts,  ImmutableList.of(header, oneContrastHigh, twoContrastMid, oneContrastLow));

    //mimics AT3G48131, DML1, F14M2.2 on http://localhost:8080/gxa/experiments/E-GEOD-38400?_specific=on
    @Test
    public void sequence2_NonSpecific_Averaging_Of_Multiple_Contrasts_Vs_Single_Contrast() {
        subject = new DifferentialProfileComparator<>(false, contrasts ,contrasts, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(sequenceProfiles2, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3));
    }

    DifferentialProfile[] sortProfiles(List<MicroarrayProfile> sequenceProfiles, DifferentialProfileComparator<DifferentialProfile> comparator) {
        SortedSet<MicroarrayProfile> sortedProfiles = new TreeSet<>(comparator);
        sortedProfiles.addAll(sequenceProfiles);
        return sortedProfiles.toArray(new MicroarrayProfile[sequenceProfiles.size()]);
    }


    String[] extractGeneNames(DifferentialProfile[] profiles) {
        String[] geneNames = new String[profiles.length];

        for (int i = 0; i < profiles.length; i++) {
            geneNames[i] = profiles[i].getName();
        }

        return geneNames;
    }

}
