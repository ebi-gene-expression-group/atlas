package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.species.services.PopularSpeciesDao.SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY;

@RunWith(MockitoJUnitRunner.class)
public class PopularSpeciesDaoTest {

    static final SpeciesProperties ANIMAL_PROPERTIES = SpeciesProperties.create("name", "factor", "animals", ImmutableList.of(ImmutableMap.<String, String>of()));
    static final Species ANIMAL_SPECIES = new Species("Some animal", ANIMAL_PROPERTIES);

    static final SpeciesProperties PLANT_PROPERTIES = SpeciesProperties.create("name", "factor", "plants", ImmutableList.of(ImmutableMap.<String, String>of()));
    static final Species PLANT_SPECIES = new Species("Some plant", PLANT_PROPERTIES);

    static final SpeciesProperties FUNGI_PROPERTIES = SpeciesProperties.create("name", "factor", "fungi", ImmutableList.of(ImmutableMap.<String, String>of()));
    static final Species FUNGI_SPECIES = new Species("Some fungi", FUNGI_PROPERTIES);

    static final ImmutableList<String> ANIMAL_SPECIES_NAMES = ImmutableList.of("Homo sapiens", "Caenorhabditis elegans", "Bos taurus", "Mus musculus", "Drosophila melanogaster", "Xenopus tropicalis");
    static final ImmutableList<String> PLANT_SPECIES_NAMES = ImmutableList.of("Zea mays", "Vitis vinifera", "Arabidopsis thaliana", "Glycine max", "Hordeum vulgare", "Oryza sativa");
    static final ImmutableList<String> FUNGI_SPECIES_NAMES = ImmutableList.of("Aspergillus fumigatus", "Saccharomyces cerevisiae", "Schizosaccharomyces pombe");

    static ImmutableMap<String, Pair<Integer, Integer>> expectedValues;

    @Mock
    JdbcTemplate jdbcTemplateMock;

    @Mock
    SpeciesFactory speciesFactoryMock;

    PopularSpeciesDao subject;

    @Before
    public void setUp() throws Exception {
        for (String animalSpeciesName : ANIMAL_SPECIES_NAMES) {
            when(speciesFactoryMock.create(animalSpeciesName)).thenReturn(ANIMAL_SPECIES);
        }
        for (String plantSpeciesName : PLANT_SPECIES_NAMES) {
            when(speciesFactoryMock.create(plantSpeciesName)).thenReturn(PLANT_SPECIES);
        }
        for (String fungiSpeciesName : FUNGI_SPECIES_NAMES) {
            when(speciesFactoryMock.create(fungiSpeciesName)).thenReturn(FUNGI_SPECIES);
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
                        ImmutableMap.<String, Object>of("organism", speciesName, "type", experimentType.toString(), "c", experimentsCount)
                );
            }

            speciesToExperimentCountBuilder.put(speciesName, Pair.of(baselineExperimentsCount, differentialExperimentsCount));
        }

        expectedValues = speciesToExperimentCountBuilder.build();

        when(jdbcTemplateMock.queryForList(SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY)).thenReturn(resultsBuilder.build().asList());
        subject = new PopularSpeciesDao(jdbcTemplateMock, speciesFactoryMock);
    }

    @Test
    public void popularSpecies() throws Exception {
        assertThat(subject.popularSpecies(), hasSize(ANIMAL_SPECIES_NAMES.size() + PLANT_SPECIES_NAMES.size() + FUNGI_SPECIES_NAMES.size()));

        for (PopularSpeciesInfo popularSpeciesInfo : subject.popularSpecies()) {
            assertThat(popularSpeciesInfo.baselineExperiments(), is((long) expectedValues.get(popularSpeciesInfo.species()).getLeft()));
            assertThat(popularSpeciesInfo.differentialExperiments(), is((long) expectedValues.get(popularSpeciesInfo.species()).getRight()));
            assertThat(popularSpeciesInfo.kingdom(), is(speciesFactoryMock.create(popularSpeciesInfo.species()).getKingdom()));
        }
    }
}