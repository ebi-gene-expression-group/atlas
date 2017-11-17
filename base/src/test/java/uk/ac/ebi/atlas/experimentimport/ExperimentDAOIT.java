package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExperimentDAOIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_GEOD_5614 = "E-GEOD-5614";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.RNASEQ_MRNA_BASELINE;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
    private static final String SECRET_111 = "Secret_111";

    @Inject
    private ExperimentDAO subject;

    public UUID createSecret111(boolean isPrivate) {
        ExperimentDTO mtab = ExperimentDTO.createNew(SECRET_111, TYPE_MICROARRAY, "cow", Sets.newHashSet("1"), "diff", isPrivate);
        return subject.addExperiment(mtab, Optional.absent());
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
        assertThat(experimentDTOs, hasItem(ExperimentDTO.createNew(E_MTAB_513, TYPE_BASELINE, "", Sets.newHashSet(""), "", false)));
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

    @Test
    public void forPrivateExperimentsAccessKeyIsRequired() {
        UUID id = createSecret111(true);
        try {
            subject.findExperiment(SECRET_111, id.toString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void forPrivateExperimentsAccessKeyIsRequired2() {
        createSecret111(true);
        try {
            subject.findExperiment(SECRET_111, "different id");
            fail();
        } catch (Exception e) {
            //yum
        }
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
}