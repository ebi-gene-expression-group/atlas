
package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentBuilderTest {

    private static final String FACTOR_TYPE = "type";
    private static final String SPECIES = "homo sapiens";
    private static final String SPECIES_NAME = "Homo sapiens";
    private static final String FACTOR_NAME = "name";
    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String DESCRIPTION = "description";
    private static final String DISPLAY_NAME = "displayName";
    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String PUBMEDID = "PUBMEDID";
    private static final List<String> PROVIDER_URL = Arrays.asList("http://www.provider.com","http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION = Arrays.asList("Baseline experiment data provider","Another baseline experiment data provider");

    private BaselineExperimentBuilder subject;

    @Mock
    private ExperimentRun runMock1;

    @Mock
    private ExperimentRun runMock2;

    @Mock
    private FactorGroup factorGroupMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentalFactors experimentalFactors;

    private Map<String, String> nameMap = Maps.newHashMap();

    private Map<String, String> speciesMap = Maps.newHashMap();

    @Mock
    private AssayGroups assayGroupsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentBuilder();

        nameMap.put(FACTOR_TYPE, FACTOR_NAME);

        speciesMap.put(SPECIES, SPECIES_NAME);

        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet("g1", "g2"));
    }

    @Test
    public void testCreate() throws Exception {

        BaselineExperiment experiment = subject.forSpecies(SPECIES)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withExtraInfo(false)
                .withSpeciesMapping(speciesMap)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withDataProviderURL(PROVIDER_URL)
                .withDataProviderDescription(PROVIDER_DESCRIPTION)
                .create();

        BaselineExperiment experiment1 = subject.forSpecies(SPECIES)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withExtraInfo(false)
                .withSpeciesMapping(speciesMap)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withDataProviderURL(null)
                .withDataProviderDescription(null)
                .create();

        assertThat(experiment.getAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(experiment.getExperimentRunAccessions(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
        assertThat(experiment.getDescription(), is(DESCRIPTION));
        assertThat(experiment.getDisplayName(), is(DISPLAY_NAME));
        assertThat(experiment.hasExtraInfoFile(), is(false));
        assertThat(experiment.getSpecies(), is(SPECIES));
        assertThat(experiment.getSpeciesToEnsemblMapping(), is(speciesMap));
        assertThat(experiment.getType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
        assertThat(experiment.getPubMedIds(), contains(PUBMEDID));
        assertThat(experiment.getExperimentDesign(), is(experimentDesignMock));
        assertThat(experiment.getExperimentalFactors(), is(experimentalFactors));
        assertThat(experiment.getDataProviderURL(), is(PROVIDER_URL));
        assertThat(experiment.getDataProviderDescription(), is(PROVIDER_DESCRIPTION));
    }
}