
package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterFactorMenuBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final String RNA = "RNA";
    private static final String CELLULAR_COMPONENT = "CELLULAR_COMPONENT";
    private static final String ORGANISM_PART_NAME = "organism part";
    private static final String RNA_NAME = "RNA";
    private static final String CELLULAR_COMPONENT_NAME = "cellular component";

    Factor firstFactor = new Factor(ORGANISM_PART, "liver");
    Factor secondFactor = new Factor(ORGANISM_PART, "heart");
    Factor thirdFactor = new Factor(ORGANISM_PART, "brain");
    Factor forthFactor = new Factor(RNA, "long poly a rna");
    Factor fifthFactor = new Factor(RNA, "total rna");
    Factor sixthFactor = new Factor(CELLULAR_COMPONENT, "plasma");
    Factor sevenFactor = new Factor(CELLULAR_COMPONENT, "whole cell");

    Set<Factor> allFactors = Sets.newHashSet(firstFactor, secondFactor, thirdFactor, forthFactor, fifthFactor, sixthFactor, sevenFactor);

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    FilterFactorMenuBuilder subject;

    private SortedSet<Factor> putIntoSortedSet(Factor... factors) {
        SortedSet<Factor> result = Sets.newTreeSet();
        for (Factor factor : factors) {
            result.add(factor);
        }
        return result;
    }

    @Before
    public void setup() {

        // liver, long poly a rna, plasma
        when(experimentalFactorsMock.getCoOccurringFactors(firstFactor)).thenReturn(putIntoSortedSet(forthFactor, sixthFactor));

        // heart, long poly a rna, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(secondFactor)).thenReturn(putIntoSortedSet(forthFactor, sevenFactor));

        // brain, total rna, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(thirdFactor)).thenReturn(putIntoSortedSet(fifthFactor, sevenFactor));

        // long poly a rna with liver, heart, plasma, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(forthFactor)).thenReturn(putIntoSortedSet(firstFactor, secondFactor, sixthFactor, sevenFactor));

        // total rna with brain, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(fifthFactor)).thenReturn(putIntoSortedSet(thirdFactor, sevenFactor));

        // plasma with liver, long poly a rna
        when(experimentalFactorsMock.getCoOccurringFactors(sixthFactor)).thenReturn(putIntoSortedSet(firstFactor, forthFactor));

        // whole cell with, heart, brain, long poly a rna, total rna
        when(experimentalFactorsMock.getCoOccurringFactors(sevenFactor)).thenReturn(putIntoSortedSet(secondFactor, thirdFactor, forthFactor, fifthFactor));

        when(experimentalFactorsMock.getFactorDisplayName(ORGANISM_PART)).thenReturn(ORGANISM_PART_NAME);
        when(experimentalFactorsMock.getFactorDisplayName(RNA)).thenReturn(RNA_NAME);
        when(experimentalFactorsMock.getFactorDisplayName(CELLULAR_COMPONENT)).thenReturn(CELLULAR_COMPONENT_NAME);

        when(experimentalFactorsMock.getFactorType(ORGANISM_PART_NAME)).thenReturn(ORGANISM_PART);
        when(experimentalFactorsMock.getFactorType(RNA_NAME)).thenReturn(RNA);
        when(experimentalFactorsMock.getFactorType(CELLULAR_COMPONENT_NAME)).thenReturn(CELLULAR_COMPONENT);

        subject = new FilterFactorMenuBuilder();
        subject.withExperimentalFactors(experimentalFactorsMock).forFilterFactors(allFactors);
    }

    @Test
    public void testExtractFactorNamesAll() {

        // given
        Map<String, Set<Factor>> extractedFactorNames = subject.extractFactorNames(allFactors);

        // then
        assertThat(extractedFactorNames.keySet(), containsInAnyOrder(ORGANISM_PART_NAME, RNA, CELLULAR_COMPONENT_NAME));
        assertThat(extractedFactorNames.get(ORGANISM_PART_NAME), containsInAnyOrder(firstFactor, secondFactor, thirdFactor));
        assertThat(extractedFactorNames.get(RNA_NAME), containsInAnyOrder(forthFactor, fifthFactor));
        assertThat(extractedFactorNames.get(CELLULAR_COMPONENT_NAME), containsInAnyOrder(sixthFactor, sevenFactor));
    }

    @Test
    public void testExtractFactorNamesSubset() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor);

        // given
        Map<String, Set<Factor>> extractedFactorNames = subject.extractFactorNames(subset);

        // then
        assertThat(extractedFactorNames.keySet(), contains(ORGANISM_PART_NAME));
        assertThat(extractedFactorNames.get(ORGANISM_PART_NAME), containsInAnyOrder(firstFactor, secondFactor, thirdFactor));
    }

    @Test
    public void testFilterRemainingFactorsAll() {

        // given
        Set<Factor> factors = subject.filterRemainingFactors(firstFactor, allFactors);
        Set<Factor> factors1 = subject.filterRemainingFactors(forthFactor, allFactors);

        // then
        assertThat(factors, containsInAnyOrder(forthFactor, sixthFactor));
        assertThat(factors1, containsInAnyOrder(firstFactor, secondFactor, sixthFactor, sevenFactor));
    }

    @Test
    public void testFilterRemainingFactorsSubset() {

        Set<Factor> subset = Sets.newHashSet(sixthFactor, sevenFactor);

        // given
        Set<Factor> factors = subject.filterRemainingFactors(firstFactor, subset);
        Set<Factor> factors1 = subject.filterRemainingFactors(forthFactor, subset);

        // then
        assertThat(factors, containsInAnyOrder(sixthFactor));
        assertThat(factors1, containsInAnyOrder(sixthFactor, sevenFactor));
    }

    @Test
    public void testBuildVoicesSubsetNoRecursion() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor);

        // given
        SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices = subject.buildVoices(subset, subset);

        // then
        assertThat(filterFactorMenuVoices.size(), is(3));

        // and
        checkForOrganismParts(filterFactorMenuVoices, true);
    }

    @Test
    public void testBuildVoicesSubsetOneRecursion() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor, forthFactor, fifthFactor);

        // given
        SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices = subject.buildVoices(Sets.newHashSet(firstFactor, secondFactor, thirdFactor), subset);

        // then
        assertThat(filterFactorMenuVoices.size(), is(3));

        // and
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[3]);

        assertThat(array[0].getDisplayName(), is("brain"));
        checkChildrenOfVoice(array[0].getChildren().first(), RNA_NAME, "total rna");
        assertThat(array[0].getChildren().first().getChildren().first().isLeaf(), is(true));

        assertThat(array[1].getDisplayName(), is("heart"));
        checkChildrenOfVoice(array[1].getChildren().first(), RNA_NAME, "long poly a rna");
        assertThat(array[1].getChildren().first().getChildren().first().isLeaf(), is(true));

        assertThat(array[2].getDisplayName(), is("liver"));
        checkChildrenOfVoice(array[2].getChildren().first(), RNA_NAME, "long poly a rna");
        assertThat(array[2].getChildren().first().getChildren().first().isLeaf(), is(true));
    }

    @Test
    public void testBuildVoicesOtherSubsetOneRecursion() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor, forthFactor, fifthFactor);

        // given
        SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices = subject.buildVoices(Sets.newHashSet(forthFactor, fifthFactor), subset);

        // then
        assertThat(filterFactorMenuVoices.size(), is(2));

        // and
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[2]);

        assertThat(array[0].getDisplayName(), is("long poly a rna"));
        checkChildrenOfVoice(array[0].getChildren().first(), ORGANISM_PART_NAME, "heart");
        assertThat(array[0].getChildren().first().getChildren().first().isLeaf(), is(true));

        assertThat(array[1].getDisplayName(), is("total rna"));
        checkChildrenOfVoice(array[1].getChildren().first(), ORGANISM_PART_NAME, "brain");
        assertThat(array[1].getChildren().first().getChildren().first().isLeaf(), is(true));
    }

    @Test
    public void testExtractVoicesSubsetNoRecursion() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor);

        // given
        SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices = subject.extractVoicesForFactors(subset);

        // then
        assertThat(filterFactorMenuVoices.size(), is(1));

        // and
        FilterFactorMenuVoice filterFactorMenuVoice = filterFactorMenuVoices.iterator().next();
        assertThat(filterFactorMenuVoice.getDisplayName(), is(ORGANISM_PART_NAME));
        assertThat(filterFactorMenuVoice.isLeaf(), is(false));
        assertThat(filterFactorMenuVoice.getChildren().size(), is(3));

        // and
        checkForOrganismParts(filterFactorMenuVoice.getChildren(), true);
    }

    @Test
    public void testExtractVoicesSubsetOneRecursion() {

        Set<Factor> subset = Sets.newHashSet(firstFactor, secondFactor, thirdFactor, forthFactor, fifthFactor);

        // given
        SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices = subject.extractVoicesForFactors(subset);

        // then
        assertThat(filterFactorMenuVoices.size(), is(2));

        // and
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[2]);

        assertThat(array[0].getDisplayName(), is(RNA_NAME));
        assertThat(array[0].getChildren().size(), is(2));
        assertThat(array[1].getDisplayName(), is(ORGANISM_PART_NAME));
        assertThat(array[1].getChildren().size(), is(3));

        checkForRNA(array[0].getChildren(), false);
        checkForOrganismParts(array[1].getChildren(), false);
    }

    @Test
    public void testBuild() {

        // given
        SortedSet<FilterFactorMenuVoice> menu = subject.build();

        // then
        assertThat(menu, is(notNullValue()));
        assertThat(menu.size(), is(3));

        // and
        FilterFactorMenuVoice[] array = menu.toArray(new FilterFactorMenuVoice[3]);
        assertThat(array[0].getDisplayName(), is(RNA_NAME));
        assertThat(array[0].getType(), is(RNA));
        assertThat(array[0].getChildren().size(), is(2));
        assertThat(array[1].getDisplayName(), is(CELLULAR_COMPONENT_NAME));
        assertThat(array[1].getType(), is(CELLULAR_COMPONENT));
        assertThat(array[1].getChildren().size(), is(2));
        assertThat(array[2].getDisplayName(), is(ORGANISM_PART_NAME));
        assertThat(array[2].getType(), is(ORGANISM_PART));
        assertThat(array[2].getChildren().size(), is(3));

        // and
        checkForRNA(array[0].getChildren(), false);
        checkForCellularComponents(array[1].getChildren(), false);
        checkForOrganismParts(array[2].getChildren(), false);
    }

    @Test
    public void testBuildCheckFirstFirstFirst() {

        // given
        SortedSet<FilterFactorMenuVoice> menu = subject.build();
        FilterFactorMenuVoice[] array = menu.toArray(new FilterFactorMenuVoice[3]);

        // then
        FilterFactorMenuVoice firstLevel = array[0];
        checkChildrenOfVoice(firstLevel, RNA_NAME, "long poly a rna");

        FilterFactorMenuVoice secondLevel = firstLevel.getChildren().first().getChildren().first();
        checkChildrenOfVoice(secondLevel, CELLULAR_COMPONENT_NAME, "plasma");

        FilterFactorMenuVoice thirdLevel = secondLevel.getChildren().first().getChildren().first();
        checkChildrenOfVoice(thirdLevel, ORGANISM_PART_NAME, "liver");
        assertThat(thirdLevel.getChildren().first().isLeaf(), is(true));
        assertThat(thirdLevel.getChildren().first().getFactor(), is(firstFactor));
    }

    @Test
    public void testBuildCheckLastFirstFirst() {

        // given
        SortedSet<FilterFactorMenuVoice> menu = subject.build();
        FilterFactorMenuVoice[] array = menu.toArray(new FilterFactorMenuVoice[3]);

        // then
        FilterFactorMenuVoice firstLevel = array[2];
        checkChildrenOfVoice(firstLevel, ORGANISM_PART_NAME, "brain");

        FilterFactorMenuVoice secondLevel = firstLevel.getChildren().first().getChildren().first();
        checkChildrenOfVoice(secondLevel, RNA_NAME, "total rna");

        FilterFactorMenuVoice thirdLevel = secondLevel.getChildren().first().getChildren().first();
        checkChildrenOfVoice(thirdLevel, CELLULAR_COMPONENT_NAME, "whole cell");
        assertThat(thirdLevel.getChildren().first().isLeaf(), is(true));
        assertThat(thirdLevel.getChildren().first().getFactor(), is(sevenFactor));
    }

    @Test
    public void testBuildCheckFirstLastLast() {

        // given
        SortedSet<FilterFactorMenuVoice> menu = subject.build();
        FilterFactorMenuVoice[] array = menu.toArray(new FilterFactorMenuVoice[3]);

        // then
        FilterFactorMenuVoice firstLevel = array[0];
        checkChildrenOfVoice(firstLevel, RNA_NAME, "long poly a rna");

        FilterFactorMenuVoice secondLevel = firstLevel.getChildren().last().getChildren().last();
        checkChildrenOfVoice(secondLevel, ORGANISM_PART_NAME, "brain");

        FilterFactorMenuVoice thirdLevel = secondLevel.getChildren().last().getChildren().last();
        checkChildrenOfVoice(thirdLevel, CELLULAR_COMPONENT_NAME, "whole cell");
        assertThat(thirdLevel.getChildren().first().isLeaf(), is(true));
        assertThat(thirdLevel.getChildren().first().getFactor(), is(sevenFactor));
    }

    @Test
    public void testBuildCheckLastLastLast() {

        // given
        SortedSet<FilterFactorMenuVoice> menu = subject.build();
        FilterFactorMenuVoice[] array = menu.toArray(new FilterFactorMenuVoice[3]);

        // then
        FilterFactorMenuVoice firstLevel = array[2];
        checkChildrenOfVoice(firstLevel, ORGANISM_PART_NAME, "brain");

        FilterFactorMenuVoice secondLevel = firstLevel.getChildren().last().getChildren().last();
        checkChildrenOfVoice(secondLevel, CELLULAR_COMPONENT_NAME, "plasma");

        FilterFactorMenuVoice thirdLevel = secondLevel.getChildren().last().getChildren().last();
        checkChildrenOfVoice(thirdLevel, RNA_NAME, "long poly a rna");
        assertThat(thirdLevel.getChildren().last().isLeaf(), is(true));
        assertThat(thirdLevel.getChildren().last().getFactor(), is(forthFactor));
    }

    private void checkForRNA(SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices, boolean onLeaf) {
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[3]);
        assertThat(array[0].getDisplayName(), is("long poly a rna"));
        assertThat(array[0].isLeaf(), is(onLeaf));
        assertThat(array[0].getFactor(), is(forthFactor));
        assertThat(array[1].getDisplayName(), is("total rna"));
        assertThat(array[1].isLeaf(), is(onLeaf));
        assertThat(array[1].getFactor(), is(fifthFactor));
    }

    private void checkForCellularComponents(SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices, boolean onLeaf) {
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[3]);
        assertThat(array[0].getDisplayName(), is("plasma"));
        assertThat(array[0].isLeaf(), is(onLeaf));
        assertThat(array[0].getFactor(), is(sixthFactor));
        assertThat(array[1].getDisplayName(), is("whole cell"));
        assertThat(array[1].isLeaf(), is(onLeaf));
        assertThat(array[1].getFactor(), is(sevenFactor));
    }

    private void checkForOrganismParts(SortedSet<FilterFactorMenuVoice> filterFactorMenuVoices, boolean onLeaf) {
        FilterFactorMenuVoice[] array = filterFactorMenuVoices.toArray(new FilterFactorMenuVoice[3]);
        assertThat(array[0].getDisplayName(), is("brain"));
        assertThat(array[0].isLeaf(), is(onLeaf));
        assertThat(array[0].getFactor(), is(thirdFactor));
        assertThat(array[1].getDisplayName(), is("heart"));
        assertThat(array[1].isLeaf(), is(onLeaf));
        assertThat(array[1].getFactor(), is(secondFactor));
        assertThat(array[2].getDisplayName(), is("liver"));
        assertThat(array[2].isLeaf(), is(onLeaf));
        assertThat(array[2].getFactor(), is(firstFactor));
    }

    private void checkChildrenOfVoice(FilterFactorMenuVoice voice, String childFactorName, String childFirstFactorValue) {

        assertThat(voice.getDisplayName(), is(childFactorName));

        FilterFactorMenuVoice factorValueLevel = voice.getChildren().first();
        assertThat(factorValueLevel.getDisplayName(), is(childFirstFactorValue));
    }

}