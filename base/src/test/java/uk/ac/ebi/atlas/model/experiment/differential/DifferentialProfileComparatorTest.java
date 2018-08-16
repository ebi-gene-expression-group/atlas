package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactoryTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileComparatorTest {
    private List<Contrast> mockContrasts = ContrastTestUtils.get(3);

    private Set<Contrast> allContrasts = ImmutableSet.copyOf(mockContrasts);
    private Set<Contrast> selectedContrasts = ImmutableSet.copyOf(mockContrasts.subList(0, 1));
    private Set<Contrast> nonSelectedContrasts = ImmutableSet.copyOf(mockContrasts.subList(1, 3));

    private DifferentialProfileComparator<DifferentialProfile> subject =
            new DifferentialProfileComparator<>(true, selectedContrasts, allContrasts, Regulation.UP);

    @Mock
    private DifferentialProfile<DifferentialExpression, ?> profileMock1;

    @Mock
    private DifferentialProfile<DifferentialExpression, ?> profileMock2;

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        subject = new DifferentialProfileComparator<>(true, allContrasts, allContrasts, Regulation.UP);

        when(profileMock1.getSpecificity(Regulation.UP)).thenReturn(1L);
        when(profileMock2.getSpecificity(Regulation.UP)).thenReturn(2L);

        //then
        int comparison = subject.compare(profileMock1, profileMock2);

        assertThat(comparison, lessThan(0));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOn() {
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
    public void testGetExpressionLevelFoldChangeOnWhenAllContrastsAreSelected() {
        subject = new DifferentialProfileComparator<>(true, selectedContrasts, allContrasts, Regulation.UP);

        //when
        when(profileMock1.getMaxExpressionLevelOn(nonSelectedContrasts)).thenReturn(0.05);
        //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.025);
        //then
        assertThat(subject.getExpressionLevelFoldChange(profileMock1), is(2D));
    }


    @Test
    public void mocksequenceNonSpecificAllContrasts() {
        RnaSeqProfile differentialProfileMock1 = mock(RnaSeqProfile.class);
        RnaSeqProfile differentialProfileMock2 = mock(RnaSeqProfile.class);
        RnaSeqProfile differentialProfileMock3 = mock(RnaSeqProfile.class);

        //given
        when(differentialProfileMock1.getName()).thenReturn("1");
        when(differentialProfileMock1.getAverageExpressionLevelOn(new HashSet<>())).thenReturn(3D);

        when(differentialProfileMock1.getName()).thenReturn("2");
        when(differentialProfileMock2.getAverageExpressionLevelOn(new HashSet<>())).thenReturn(5D);

        when(differentialProfileMock1.getName()).thenReturn("3");
        when(differentialProfileMock3.getAverageExpressionLevelOn(new HashSet<>())).thenReturn(2D);

        subject =
                new DifferentialProfileComparator<>(
                        false, Collections.emptySet(), Collections.emptySet(), Regulation.UP_DOWN);

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

    private static final String T_STAT_IGNORED = "0";
    private static final String P_VALUE_IGNORED = "999";
    private static final String FOLD_CHANGE_IGNORED = "0";

    private static final String FOLD_CHANGE_20 = "20";
    private static final String FOLD_CHANGE_10 = "10";
    private static final String FOLD_CHANGE_7 = "7";
    private static final String FOLD_CHANGE_5 = "5";
    private static final String FOLD_CHANGE_3 = "3";
    private static final String FOLD_CHANGE_1 = "0.1";

    private static final String P_VALUE_0_DOT_1 = "0.1";
    private static final String P_VALUE_0_DOT_2 = "0.2";
    private static final String GENE_1 = "1";
    private static final String GENE_2 = "2";
    private static final String GENE_3 = "3";
    private static final String GENE_4 = "4";
    private static final String GENE_5 = "5";
    private static final String GENE_6 = "6";

    private static final String DESIGN_ELEMENT = "design_element";

    private static final String HEADER =
            Joiner.on("\t").join(new String[] {
                    "gene name", "gene id", "design element",
                    "contrast_1.p-value", "contrast_1.t-stat", "contrast_1.fold-change",
                    "contrast_2.p-value", "contrast_2.t-stat", "contrast_2.fold-change"});
    private static final String SPECIFIC_ONE_CONTRAST =
            Joiner.on("\t").join(new String[] {
                    GENE_1, GENE_1, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_1,
                    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    private static final String TWO_CONTRAST_HIGH_FOLD_CHANGE =
            Joiner.on("\t").join(new String[] {
                    GENE_2, GENE_2, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_20,
                    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_10});
    private static final String TWO_CONTRAST_HIGH_FOLD_CHANGE_OTHER_CONTRAST =
            Joiner.on("\t").join(new String[] {
                    GENE_6, GENE_6, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_20});
    private static final String TWO_CONTRAST_LOW_FOLD_CHANGE =
            Joiner.on("\t").join(new String[] {
                    GENE_3, GENE_3, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_10});
    private static final String TWO_CONTRAST_SAME_FOLD_CHANGE_LOW_P_VALUE =
            Joiner.on("\t").join(new String[] {
                    GENE_4, GENE_4, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5});
    private static final String TWO_CONTRAST_SAME_FOLD_CHANGE_HIGH_P_VALUE =
            Joiner.on("\t").join(new String[] {
                    GENE_5, GENE_5, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5,
                    P_VALUE_0_DOT_2, T_STAT_IGNORED, FOLD_CHANGE_5});

    private static final List<String> SEQUENCE_LINES =
            ImmutableList.of(
                    HEADER, SPECIFIC_ONE_CONTRAST, TWO_CONTRAST_HIGH_FOLD_CHANGE_OTHER_CONTRAST,
                    TWO_CONTRAST_SAME_FOLD_CHANGE_HIGH_P_VALUE, TWO_CONTRAST_SAME_FOLD_CHANGE_LOW_P_VALUE,
                    TWO_CONTRAST_LOW_FOLD_CHANGE, TWO_CONTRAST_HIGH_FOLD_CHANGE);

    private static final List<Contrast> CONTRASTS = ContrastTestUtils.get(6);


    private static final List<MicroarrayProfile> SEQUENCE_PROFILES =
            MicroarrayProfileStreamFactoryTest.loadProfiles(CONTRASTS, SEQUENCE_LINES);

    @Test
    public void sequenceSpecificAllContrasts() {
        subject = new DifferentialProfileComparator<>(true, CONTRASTS, CONTRASTS, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(SEQUENCE_PROFILES, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_6, GENE_2, GENE_3, GENE_4, GENE_5));
    }

    @Test
    public void sequenceSpecificSelectedContrast() {
        subject = new DifferentialProfileComparator<>(true, CONTRASTS.subList(0, 1), CONTRASTS,
                Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(SEQUENCE_PROFILES, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3, GENE_4, GENE_5, GENE_6));
    }

    @Test
    public void sequenceNonSpecificAllContrasts() {
        subject = new DifferentialProfileComparator<>(false, CONTRASTS, CONTRASTS, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(SEQUENCE_PROFILES, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_6, GENE_2, GENE_3, GENE_4, GENE_5, GENE_1));
    }

    @Test
    public void sequenceNonSpecificSelectedContrast() {
        subject = new DifferentialProfileComparator<>(false, CONTRASTS.subList(0, 1), CONTRASTS, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(SEQUENCE_PROFILES, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_2, GENE_3, GENE_6, GENE_4, GENE_5, GENE_1));
    }

    private static final String ONE_CONTRAST_HIGH =
            Joiner.on("\t").join(new String[] {
                    GENE_1, GENE_1, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_7,
                    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});
    private static final String TWO_CONTRAST_MID =
            Joiner.on("\t").join(new String[] {
                    GENE_2, GENE_2, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_3});
    private static final String ONE_CONTRAST_LOW =
            Joiner.on("\t").join(new String[] {
                    GENE_3, GENE_3, DESIGN_ELEMENT,
                    P_VALUE_0_DOT_1, T_STAT_IGNORED, FOLD_CHANGE_5,
                    P_VALUE_IGNORED, T_STAT_IGNORED, FOLD_CHANGE_IGNORED});

    private static final GeneProfilesList<MicroarrayProfile> SEQUENCE_PROFILES_2 =
            MicroarrayProfileStreamFactoryTest.loadProfiles(
                    CONTRASTS,  ImmutableList.of(HEADER, ONE_CONTRAST_HIGH, TWO_CONTRAST_MID, ONE_CONTRAST_LOW));

    //mimics AT3G48131, DML1, F14M2.2 on http://localhost:8080/gxa/experiments/E-GEOD-38400?_specific=on
    @Test
    public void sequence2NonSpecificAveragingOfMultipleContrastsVsSingleContrast() {
        subject = new DifferentialProfileComparator<>(false, CONTRASTS, CONTRASTS, Regulation.UP_DOWN);

        DifferentialProfile[] sortedProfilesArray = sortProfiles(SEQUENCE_PROFILES_2, subject);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains(GENE_1, GENE_2, GENE_3));
    }

    private DifferentialProfile[] sortProfiles(List<MicroarrayProfile> sequenceProfiles,
                                               DifferentialProfileComparator<DifferentialProfile> comparator) {
        SortedSet<MicroarrayProfile> sortedProfiles = new TreeSet<>(comparator);
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
