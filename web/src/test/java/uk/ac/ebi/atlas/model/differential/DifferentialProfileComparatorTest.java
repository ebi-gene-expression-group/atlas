/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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
import uk.ac.ebi.atlas.streams.differential.IsDifferentialExpressionAboveCutOff;
import uk.ac.ebi.atlas.streams.differential.microarray.MicroarrayProfileDeserializer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
    public void lowerAverageAcrossSelectedContrasts() {

         //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.01);
        when(profileMock1.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3))).thenReturn(0.02);
        //and
         //when
        when(profileMock2.getAverageExpressionLevelOn(selectedContrasts)).thenReturn(0.01);
        when(profileMock2.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3))).thenReturn(0.04);


        int comparison = subject.compare(profileMock1, profileMock2);
        // then
        assertThat(comparison, is(greaterThanOrEqualTo(0)));

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
    public void sequence1() {

        DifferentialProfile differentialProfileMock1 = Mockito.mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock2 = Mockito.mock(DifferentialProfile.class);
        DifferentialProfile differentialProfileMock3 = Mockito.mock(DifferentialProfile.class);

        //given
        when(differentialProfileMock1.getAverageExpressionLevelOn(new HashSet<Contrast>())).thenReturn(3D);
        when(differentialProfileMock1.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);
        when(differentialProfileMock2.getAverageExpressionLevelOn(new HashSet<Contrast>())).thenReturn(5D);
        when(differentialProfileMock2.getSpecificity(Regulation.UP_DOWN)).thenReturn(1);
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

        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock3));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock1));
        assertThat(profilesIterator.next(), Matchers.is(differentialProfileMock2));

    }

    String sequenceLines =  "FBgn0019828\tdj\t1627959_a_at\t0.0004000391565989\t-9.36995510274818\t-0.788061466666666\t6.4460598240872e-06\t-20.5528971215852\t-1.68509426666667\n" +
                    "FBgn0035109\tCG13876\t1638975_at\t0.77649464534638\t0.443665367338669\t0.0357508666666666\t8.35994190521307e-06\t-19.1558475545564\t-1.55682156666667\n" +
                    "FBgn0031690\tCG7742\t1623604_at\t0.596675085988318\t-0.796460861127057\t-0.120377\t1.25718990358721e-05\t-17.7045026278553\t-1.9175005\n" +
                    "FBgn0032636\tCG5043\t1637522_at\t0.00844182480286779\t-4.82652565702941\t-0.6649263\t1.78059980397776e-05\t-16.493967343194\t-2.1014968\n" +
                    "FBgn0051803\tCG31803\t1631644_at\t0.120354422845427\t-2.36172902724942\t-0.382887633333332\t1.93385121266592e-05\t-16.2525901824726\t-1.45677683333333\n";

    @Test
    public void sequence_foldChangeCutOff_0() {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(0.05);
        expressionFilter.setRegulation(Regulation.UP_DOWN);
        expressionFilter.setFoldChangeCutOff(0);

        MicroarrayProfileDeserializer builder = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1, contrastMock2), expressionFilter);
        subject = new DifferentialProfileComparator(true, Collections.emptySet(), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        //when
        SortedSet<MicroarrayProfile> sortedProfiles = new TreeSet<MicroarrayProfile>(subject);
        ImmutableList<MicroarrayProfile> profiles = builder.create(sequenceLines.split("\n"));
        sortedProfiles.addAll(profiles);

        //then
        DifferentialProfile[] sortedProfilesArray = sortedProfiles.toArray(new MicroarrayProfile[profiles.size()]);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains("CG13876", "CG7742", "CG31803", "dj", "CG5043"));
    }


    @Test
    public void sequence_foldChangeCutOff_1() {
        IsDifferentialExpressionAboveCutOff expressionFilter = new IsDifferentialExpressionAboveCutOff();
        expressionFilter.setPValueCutoff(0.05);
        expressionFilter.setRegulation(Regulation.UP_DOWN);
        expressionFilter.setFoldChangeCutOff(1);

        MicroarrayProfileDeserializer builder = new MicroarrayProfileDeserializer(ImmutableList.of(contrastMock1, contrastMock2), expressionFilter);
        subject = new DifferentialProfileComparator(true, Collections.emptySet(), Sets.newHashSet(contrastMock1, contrastMock2), Regulation.UP_DOWN);

        //when
        SortedSet<MicroarrayProfile> sortedProfiles = new TreeSet<MicroarrayProfile>(subject);
        ImmutableList<MicroarrayProfile> profiles = builder.create(sequenceLines.split("\n"));
        sortedProfiles.addAll(profiles);

        //then
        DifferentialProfile[] sortedProfilesArray = sortedProfiles.toArray(new MicroarrayProfile[profiles.size()]);
        System.out.println(Joiner.on("\n").join(sortedProfilesArray));

        String[] sortedGeneNames = extractGeneNames(sortedProfilesArray);
        assertThat(Arrays.asList(sortedGeneNames), contains("dj", "CG13876", "CG7742", "CG5043", "CG31803"));
    }

    private String[] extractGeneNames(DifferentialProfile[] profiles) {
        String[] geneNames = new String[profiles.length];

        for (int i = 0; i < profiles.length; i++) {
            geneNames[i] = profiles[i].getName();
        }

        return geneNames;
    }

}
