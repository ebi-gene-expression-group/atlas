package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class GxaExperimentDaoIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_GEOD_5614 = "E-GEOD-5614";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.RNASEQ_MRNA_BASELINE;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
    private static final String SECRET_111 = "Secret_111";

    @Inject
    private GxaExperimentDao subject;

    public UUID createSecret111(boolean isPrivate) {
        UUID randomUUID = UUID.randomUUID();
        ExperimentDTO mtab = ExperimentDTO.create(SECRET_111, TYPE_MICROARRAY, "cow", Sets.newHashSet("1"), "diff", isPrivate);
        subject.addExperiment(mtab, randomUUID);
        return randomUUID;
    }

    @Before
    public void deleteSecret111() {
        try {
            subject.deleteExperiment(SECRET_111);
        } catch (Exception e) {
            //yum!
        }
    }

    @Test
    public void testFindExperiments() {
        List<ExperimentDTO> experimentDTOs = subject.getAllExperimentsAsAdmin();
        assertThat(experimentDTOs.size(), greaterThan(50));
        assertThat(experimentDTOs, hasItem(ExperimentDTO.create(E_MTAB_513, TYPE_BASELINE, "", Sets.newHashSet(""), "", false)));
    }

    @Test
    public void testFindExperimentByType() {
        Set<String> experimentAccessions = subject.findPublicExperimentAccessions(TYPE_BASELINE);
        assertThat(experimentAccessions, hasItem(E_MTAB_513));
        experimentAccessions = subject.findPublicExperimentAccessions(TYPE_MICROARRAY);
        assertThat(experimentAccessions, hasItem(E_GEOD_5614));

    }

    @Test
    public void findExperimentShouldSucceed() {
        ExperimentDTO experimentDTO = subject.findExperiment(E_MTAB_513, "");
        assertThat(experimentDTO.getExperimentAccession(), is(E_MTAB_513));
        assertThat(experimentDTO.getExperimentType(), is(TYPE_BASELINE));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findExperimentShouldFailForUnknownExperiment() {
        subject.findExperiment("UNKNOWN", "");
    }

    @Test
    public void testAddExperiment() {
        int size = subject.getAllExperimentsAsAdmin().size();
        createSecret111(false);
        assertThat(subject.getAllExperimentsAsAdmin().size(), is(size + 1));
        subject.deleteExperiment(SECRET_111);
        assertThat(subject.getAllExperimentsAsAdmin().size(), is(size));
    }

    @Test
    public void testDeleteExperiment() {
        createSecret111(false);
        int size = subject.getAllExperimentsAsAdmin().size();
        deleteSecret111();
        assertThat(subject.getAllExperimentsAsAdmin().size(), is(size - 1));
    }

    @Test
    public void updateExperimentShouldChangePrivateState() {
        createSecret111(false);
        subject.setExperimentPrivacyStatus(SECRET_111, true);
        assertThat(subject.getExperimentAsAdmin(SECRET_111).isPrivate(), is(true));
        subject.setExperimentPrivacyStatus(SECRET_111, false);
        assertThat(subject.getExperimentAsAdmin(SECRET_111).isPrivate(), is(false));
    }

    @Test(expected = RuntimeException.class)
    public void cannotCreateExperimentTwice() {
        createSecret111(false);
        createSecret111(false);
    }

    @Test(expected = RuntimeException.class)
    public void cannotCreateExperimentTwice2() {
        createSecret111(false);
        createSecret111(true);
    }

    @Test
    public void forPublicExperimentsAccessKeyIsIgnored() {
        UUID id = createSecret111(false);
        assertThat(subject.findExperiment(SECRET_111, id.toString()), is(subject.findExperiment(SECRET_111, "different id")));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void forPrivateExperimentsAccessKeyIsRequired() {
        UUID id = createSecret111(true);

        assertThat(subject.findExperiment(SECRET_111, id.toString()), hasProperty("experimentAccession", is(SECRET_111)));

        exception.expect(ResourceNotFoundException.class);
        subject.findExperiment(SECRET_111, "foobar");
    }

    @Test
    public void youCanGetPrivateExperimentIfYouAreAdmin() {
        createSecret111(true);
        try {
            subject.getExperimentAsAdmin(SECRET_111);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void countExperiments() {
        createSecret111(true);
        int count = subject.countExperiments();
        deleteSecret111();
        assertThat(subject.countExperiments(), is(count - 1));
    }
}