package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableSetMultimap.flatteningToImmutableSetMultimap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.getRandomEfoAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.getRandomExperimentAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.randomRnaSeqRunId;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConditionsCollectorTest {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private EfoLookupService efoLookupServiceMock;

    private ImmutableSetMultimap<String, String> expandedOntologyTerms;

    private Map<String, Map<String, String>> factorValues;

    private Map<String, Map<String, String>> sampleCharacteristics;

    private AssayGroup testAssayGroup;

    private AssayGroup referenceAssayGroup;

    private ConditionsCollector subject;

    @BeforeEach
    void setUp() {
        subject = new ConditionsCollector(efoLookupServiceMock);
        when(baselineExperimentMock.getExperimentDesign()).thenReturn(experimentDesignMock);
        when(differentialExperimentMock.getExperimentDesign()).thenReturn(experimentDesignMock);

        String experimentAccession = getRandomExperimentAccession();
        when(differentialExperimentMock.getAccession()).thenReturn(experimentAccession);
        when(baselineExperimentMock.getAccession()).thenReturn(experimentAccession);

        // Create a pool of 50 random assays...
        List<String> assayIds =
                IntStream.range(0, 50)
                        .boxed()
                        .map(__ -> randomRnaSeqRunId())
                        .distinct()
                        .collect(toList());

        // Assign at most five random EFO terms to each assay
        ImmutableSetMultimap<String, String> assayIdToOntologyTerms = assayIds.stream()
                .collect(flatteningToImmutableSetMultimap(
                        assayId -> assayId,
                        __ -> IntStream.range(0, RNG.nextInt(1, 5))
                                .boxed()
                                .map(___ -> getRandomEfoAccession())
                                .distinct()));
        when(experimentDesignMock.getAllOntologyTermIdsByAssayAccession()).thenReturn(assayIdToOntologyTerms);

        // Expand, in turn, each EFO term to at most another five
        expandedOntologyTerms =
                assayIdToOntologyTerms.entries().stream()
                        .collect(flatteningToImmutableSetMultimap(
                                Map.Entry::getKey,
                                entry ->
                                        Stream.concat(
                                                Stream.of(entry.getValue()),
                                                IntStream.range(0, RNG.nextInt(1, 5))
                                                        .boxed()
                                                        .map(__ -> getRandomEfoAccession()))
                                        .distinct()));
        when(efoLookupServiceMock.expandOntologyTerms(assayIdToOntologyTerms)).thenReturn(expandedOntologyTerms);

        // Assign names to all EFO terms
        Set<String> labels = expandedOntologyTerms.values().stream().map(__ -> randomAlphabetic(15)).collect(toSet());
        when(efoLookupServiceMock.getLabels(expandedOntologyTerms.keySet())).thenReturn(labels);

        // Factors and sample characteristics from SDRF file. assay accession -> map of header, value (max of 4)
        Set<String> factorHeaders =
                IntStream.range(0, RNG.nextInt(1, 4))
                        .boxed()
                        .map(__ -> randomAlphabetic(10))
                        .collect(toSet());
        factorValues =
                assayIds.stream()
                        .collect(toMap(
                                assayId -> assayId,
                                __ -> factorHeaders.stream()
                                        .collect(toMap(
                                                factorHeader -> factorHeader,
                                                ___ -> randomAlphanumeric(20)))));
        factorValues.forEach(
                (key, value) -> when(experimentDesignMock.getFactorValues(key)).thenReturn(value));

        Set<String> sampleHeaders =
                IntStream.range(0, RNG.nextInt(1, 4))
                        .boxed()
                        .map(__ -> randomAlphabetic(10))
                        .collect(toSet());
        sampleCharacteristics =
                assayIds.stream()
                        .collect(toMap(
                                assayId -> assayId,
                                __ -> sampleHeaders.stream()
                                        .collect(toMap(
                                                sampleHeader -> sampleHeader,
                                                ___ -> randomAlphanumeric(20)))));
        sampleCharacteristics.forEach(
                (key, value) -> when(experimentDesignMock.getSampleCharacteristicsValues(key)).thenReturn(value));

        // Create list of 1..10 assays, named g1, g2, g3...
        List<String> assayGroupIds =
                IntStream.range(1, RNG.nextInt(2, 11))
                        .boxed()
                        .map(i -> "g" + Integer.toString(i))
                        .collect(toList());

        // Distribute assays between assay groups
        Multimap<String, String> assayGroupToAssayIds = randomizedMultimapOf(assayGroupIds, assayIds);

        List<AssayGroup> assayGroups = assayGroupToAssayIds.keySet().stream()
                .map(assayGroupId ->
                        AssayGroupFactory.create(
                                assayGroupId,
                                assayGroupToAssayIds.get(assayGroupId).toArray(new String[0])))
                .collect(toList());

        testAssayGroup = assayGroups.get(RNG.nextInt(0, assayGroups.size()));
        referenceAssayGroup = assayGroups.get(RNG.nextInt(0, assayGroups.size()));
        Contrast contrast = new Contrast(
                testAssayGroup.getId() + "_" + referenceAssayGroup.getId(),
                randomAlphanumeric(10),
                referenceAssayGroup,
                testAssayGroup,
                "‘" + testAssayGroup.getId() + "’ vs ‘" + referenceAssayGroup.getId() + "’");

        when(baselineExperimentMock.getDataColumnDescriptors()).thenReturn(assayGroups);
        when(differentialExperimentMock.getDataColumnDescriptors()).thenReturn(ImmutableList.of(contrast));
    }

    @Test
    void baselineConditions() {
        assertThat(subject.getConditions(baselineExperimentMock))
                .flatExtracting("values")
                .containsExactlyInAnyOrderElementsOf(
                        // We compare against a list since EFO terms or factor/sample values might occur more than once
                        ImmutableList.<String>builder()
                            .addAll(expandedOntologyTerms.values())
                            .addAll(
                                    factorValues.values().stream()
                                            .flatMap(headerValueMap -> headerValueMap.values().stream())
                                            .collect(toSet()))
                            .addAll(
                                    sampleCharacteristics.values().stream()
                                            .flatMap(headerValueMap -> headerValueMap.values().stream())
                                            .collect(toSet()))
                            .build());
    }

    @Test
    void differentialConditions() {
        assertThat(subject.getConditions(differentialExperimentMock))
//                .isEqualTo(legacyConditionsLookupService.buildPropertiesForDifferentialExperiment(differentialExperimentMock.getAccession(), experimentDesignMock, ImmutableList.of(contrast)))
                .flatExtracting("values")
                .containsExactlyInAnyOrderElementsOf(
                        // We compare against a list since EFO terms or factor/sample values might occur more than once
                        ImmutableList.<String>builder()
                                .addAll(
                                        testAssayGroup.assaysAnalyzedForThisDataColumn().stream()
                                                .flatMap(assayId ->
                                                        ImmutableSet.<String>builder()
                                                                .addAll(expandedOntologyTerms.get(assayId))
                                                                .addAll(factorValues.get(assayId).values())
                                                                .addAll(sampleCharacteristics.get(assayId).values())
                                                                .build()
                                                                .stream())
                                                .collect(toList()))
                                .addAll(
                                        referenceAssayGroup.assaysAnalyzedForThisDataColumn().stream()
                                                .flatMap(assayId ->
                                                        ImmutableSet.<String>builder()
                                                                .addAll(expandedOntologyTerms.get(assayId))
                                                                .addAll(factorValues.get(assayId).values())
                                                                .addAll(sampleCharacteristics.get(assayId).values())
                                                                .build()
                                                                .stream())
                                                .collect(toList()))
                                .build());
    }

    private static <K, V> Multimap<K, V> randomizedMultimapOf(Collection<K> buckets, Collection<V> values) {
        checkArgument(values.size() >= buckets.size());

        List<K> _buckets = Lists.newArrayList(buckets);
        Collections.shuffle(_buckets);
        List<V> _values = Lists.newArrayList(values);
        Collections.shuffle(_values);

        ImmutableMultimap.Builder<K, V> builder = ImmutableMultimap.builder();

        // First, all buckets should have at least one value
        for (K bucket : _buckets) {
            builder.put(bucket, _values.remove(0));
        }

        // Distribute the remaining values randomly among buckets
        while (!_values.isEmpty()) {
            builder.put(
                    _buckets.get(RNG.nextInt(0, _buckets.size())),
                    _values.remove(0));
        }

        return builder.build();
    }
}
