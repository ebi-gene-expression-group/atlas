package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class ExperimentDAOIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_GEOD_5614 = "E-GEOD-5614";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.RNASEQ_MRNA_BASELINE;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
    private static final String SECRET_111 = "Secret_111";

    @Inject
    private ExperimentDAO subject;

    public void createSecret111() throws Exception {
        ExperimentDTO mtab = ExperimentDTO.createNew(SECRET_111, TYPE_MICROARRAY, "cow", Sets.newHashSet("1"), "diff", false);
        subject.addExperiment(mtab, Optional.<String>absent());
    }

    public void deleteSecret111() throws Exception {
        subject.deleteExperiment(SECRET_111);
    }

    @Test
    public void testFindExperiments() throws Exception {
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        assertThat(experimentDTOs.size(), greaterThan(50));
        assertThat(experimentDTOs, hasItem(ExperimentDTO.createNew(E_MTAB_513, TYPE_BASELINE, "", Sets.newHashSet(""), "", false)));
    }

    @Test
    public void testFindExperimentByType() throws Exception {
        Set<String> experimentAccessions = subject.findPublicExperimentAccessions(TYPE_BASELINE);
        assertThat(experimentAccessions, hasItem(E_MTAB_513));
        experimentAccessions = subject.findPublicExperimentAccessions(TYPE_MICROARRAY);
        assertThat(experimentAccessions, hasItem(E_GEOD_5614));

    }

    @Test
    public void findExperimentShouldSucceed() throws Exception {
        ExperimentDTO experimentDTO = subject.findPublicExperiment(E_MTAB_513);
        assertThat(experimentDTO.getExperimentAccession(), is(E_MTAB_513));
        assertThat(experimentDTO.getExperimentType(), is(TYPE_BASELINE));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findExperimentShouldFailForUnknownExperiment() throws Exception {
        subject.findPublicExperiment("UNKNOWN");
    }

    @Test
    public void testAddExperiment() throws Exception {
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        int size = experimentDTOs.size();
        ExperimentDTO mtabNew = ExperimentDTO.createNew("new", TYPE_MICROARRAY, "cow", Sets.newHashSet("1"), "diff", true);
        subject.addExperiment(mtabNew, Optional.<String>absent());
        experimentDTOs = subject.findAllExperiments();
        assertThat(experimentDTOs.size(), is(size + 1));
        assertThat(experimentDTOs, hasItem(mtabNew));
        subject.deleteExperiment("new");
    }

    @Test
    public void testDeleteExperiment() throws Exception {
        createSecret111();
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        int size = experimentDTOs.size();
        deleteSecret111();
        assertThat(subject.findAllExperiments().size(), is(size - 1));
    }

    @Test
    public void updateExperimentShouldChangePrivateState() throws Exception {
        createSecret111();
        assertThat(subject.findPublicExperiment(SECRET_111), is(notNullValue()));
        subject.updateExperiment(SECRET_111, true);
        assertThat(subject.findExperiment(SECRET_111, true).isPrivate(), is(true));
        subject.updateExperiment(SECRET_111, false);
        assertThat(subject.findPublicExperiment(SECRET_111), is(notNullValue()));
        deleteSecret111();
    }

    @Test
    public void isImportedShouldReturnImportState() throws Exception {
        assertThat(subject.isImported(E_MTAB_513), is(true));
        assertThat(subject.isImported("NEW"), is(false));
    }

}