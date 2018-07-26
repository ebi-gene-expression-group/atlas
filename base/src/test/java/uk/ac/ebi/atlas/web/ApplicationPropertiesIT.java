package uk.ac.ebi.atlas.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ApplicationPropertiesIT {

    @Inject
    private SpeciesFactory speciesFactory;

    @Test
    public void referenceExperiment() {
        assertThat(
                ApplicationProperties.getBaselineReferenceExperimentAccession(speciesFactory.create("Homo sapiens")),
                is("E-MTAB-2836"));
    }

    @Test
    public void missingReferenceExperiment() {
        assertThat(
                ApplicationProperties.getBaselineReferenceExperimentAccession(speciesFactory.create("Canis lupus")),
                is(nullValue()));
    }
}
