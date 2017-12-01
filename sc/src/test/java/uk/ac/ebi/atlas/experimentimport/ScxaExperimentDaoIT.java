package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ScxaExperimentDaoIT {
    private static final String SC_ACCESSION = "TEST-SC";
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    @Inject
    ScxaExperimentDao subject;

    @Before
    public void setUp() throws Exception {
        UUID uuid = UUID.randomUUID();

        ExperimentDTO experimentDto =
                new ExperimentDTO(
                        SC_ACCESSION,
                        ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE,
                        "Homo sapiens",
                        Sets.newHashSet("PubMed ID 1", "PubMed ID 2"),
                        "Single-cell RNA-seq analysis of human pancreas from healthy individuals and type 2 diabetes patients",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-31"),
                        true,
                        RANDOM_UUID.toString());
        subject.addExperiment(experimentDto, RANDOM_UUID);
    }

    @After
    public void tearDown() throws Exception {
        try {
            subject.deleteExperiment(SC_ACCESSION);
        } catch (ResourceNotFoundException e) {
            // Some tests remove the test experiment
            return;
        }
    }

    @Test
    public void publicExperimentsDontIncludePrivateExperiments() {
        assumeThat(subject.findExperiment(SC_ACCESSION, RANDOM_UUID.toString()), hasProperty("experimentAccession", is(SC_ACCESSION)));
        assertThat(subject.findPublicExperimentAccessions(), not(hasItem("experimentAccession")));
    }

    @Test
    public void asAdminIncludesPrivateExperiments() {
        assertThat(subject.getAllExperimentsAsAdmin(), hasItem(hasProperty("experimentAccession", is(SC_ACCESSION))));
    }

    @Test
    public void setPrivacyStatus() {
        assumeThat(subject.findPublicExperimentAccessions(), not(hasItem(SC_ACCESSION)));
        subject.setExperimentPrivacyStatus(SC_ACCESSION, false);
        assertThat(subject.findPublicExperimentAccessions(), hasItem(SC_ACCESSION));
    }

    @Test
    public void countExperiments() {
        int count = subject.countExperiments();
        subject.deleteExperiment(SC_ACCESSION);
        assertThat(subject.countExperiments(), is(count - 1));
    }
}