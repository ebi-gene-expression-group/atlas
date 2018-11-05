package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.species.services.PopularSpeciesDao.SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY;

@RunWith(MockitoJUnitRunner.class)
public class PopularSpeciesDaoTest {
    private static final Function<String, String> TO_ENSEMBL =
            speciesName -> speciesName.toLowerCase().replaceAll("\\s+", "_");
    private static final Function<String, String> TO_REFERENCE =
            speciesName -> speciesName.toLowerCase().replaceAll("\\s+", " ");

    private static final ImmutableList<String> ANIMAL_SPECIES_NAMES =
            ImmutableList.of(
                    "Homo sapiens",
                    "Caenorhabditis elegans",
                    "Bos taurus",
                    "Mus musculus",
                    "Drosophila melanogaster",
                    "Xenopus tropicalis");
    private static final ImmutableList<String> PLANT_SPECIES_NAMES =
            ImmutableList.of(
                    "Zea mays",
                    "Vitis vinifera",
                    "Arabidopsis thaliana",
                    "Glycine max",
                    "Hordeum vulgare",
                    "Oryza sativa");
    private static final ImmutableList<String> FUNGI_SPECIES_NAMES =
            ImmutableList.of(
                    "Aspergillus fumigatus",
                    "Saccharomyces cerevisiae",
                    "Schizosaccharomyces pombe");

    private static ImmutableMap<String, Pair<Integer, Integer>> expectedValues;

    @Mock
    private JdbcTemplate jdbcTemplateMock;

    @Mock
    private SpeciesFactory speciesFactoryMock;

    private PopularSpeciesDao subject;

    @Before
    public void setUp() throws Exception {
        for (String animalSpeciesName : ANIMAL_SPECIES_NAMES) {
            SpeciesProperties speciesProperties =
                    SpeciesProperties.create(
                            TO_ENSEMBL.apply(animalSpeciesName),
                            "ORGANISM_PART",
                            "animals",
                            ImmutableList.of(ImmutableMap.of()));
            when(speciesFactoryMock.create(animalSpeciesName))
                    .thenReturn(new Species(animalSpeciesName, speciesProperties));
            when(speciesFactoryMock.create(TO_REFERENCE.apply(animalSpeciesName)))
                    .thenReturn(new Species(animalSpeciesName, speciesProperties));
        }

        for (String plantSpeciesName : PLANT_SPECIES_NAMES) {
            SpeciesProperties speciesProperties =
                    SpeciesProperties.create(
                            TO_ENSEMBL.apply(plantSpeciesName),
                            "DEVELOPMENTAL_STAGE",
                            "animals",
                            ImmutableList.of(ImmutableMap.of()));
            when(speciesFactoryMock.create(plantSpeciesName))
                    .thenReturn(new Species(plantSpeciesName, speciesProperties));
            when(speciesFactoryMock.create(TO_REFERENCE.apply(plantSpeciesName)))
                    .thenReturn(new Species(plantSpeciesName, speciesProperties));
        }

        for (String fungiSpeciesName : FUNGI_SPECIES_NAMES) {
            SpeciesProperties speciesProperties =
                    SpeciesProperties.create(
                            TO_ENSEMBL.apply(fungiSpeciesName),
                            "GROWTH_CONDITION",
                            "animals",
                            ImmutableList.of(ImmutableMap.of()));
            when(speciesFactoryMock.create(fungiSpeciesName))
                    .thenReturn(new Species(fungiSpeciesName, speciesProperties));
            when(speciesFactoryMock.create(TO_REFERENCE.apply(fungiSpeciesName)))
                    .thenReturn(new Species(fungiSpeciesName, speciesProperties));
        }

        ImmutableList.Builder<Map<String, Object>> resultsBuilder = ImmutableList.builder();
        ImmutableMap.Builder<String, Pair<Integer, Integer>> speciesToExperimentCountBuilder = ImmutableMap.builder();
        for (String speciesName : Iterables.concat(ANIMAL_SPECIES_NAMES, PLANT_SPECIES_NAMES, FUNGI_SPECIES_NAMES)) {
            int baselineExperimentsCount = 0;
            int differentialExperimentsCount = 0;
            for (ExperimentType experimentType : ExperimentType.values()) {

                long experimentsCount = ThreadLocalRandom.current().nextLong(0, 100 + 1);
                if (experimentType.isBaseline()) {
                    baselineExperimentsCount += experimentsCount;
                } else { //if (experimentType.isDifferential()) {
                    differentialExperimentsCount += experimentsCount;
                }

                resultsBuilder.add(
                        ImmutableMap.of(
                                "species", speciesName, "type", experimentType.toString(), "c", experimentsCount));
            }

            speciesToExperimentCountBuilder.put(
                    TO_REFERENCE.apply(speciesName),
                    Pair.of(baselineExperimentsCount, differentialExperimentsCount));
        }

        expectedValues = speciesToExperimentCountBuilder.build();

        when(jdbcTemplateMock.queryForList(SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY))
                .thenReturn(resultsBuilder.build().asList());
        subject = new PopularSpeciesDao(jdbcTemplateMock, speciesFactoryMock);
    }

    @Test
    public void popularSpecies() {
        assertThat(
                subject.popularSpecies(),
                hasSize(ANIMAL_SPECIES_NAMES.size() + PLANT_SPECIES_NAMES.size() + FUNGI_SPECIES_NAMES.size()));

        for (PopularSpeciesInfo popularSpeciesInfo : subject.popularSpecies()) {
            assertThat(
                    popularSpeciesInfo.baselineExperiments(),
                    is((long) expectedValues.get(popularSpeciesInfo.species()).getLeft()));
            assertThat(
                    popularSpeciesInfo.differentialExperiments(),
                    is((long) expectedValues.get(popularSpeciesInfo.species()).getRight()));
            assertThat(
                    popularSpeciesInfo.kingdom(),
                    is(speciesFactoryMock.create(popularSpeciesInfo.species()).getKingdom()));
        }
    }
}
