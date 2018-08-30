package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.thirdpartyintegration.ebeye.ExperimentDescription;
import uk.ac.ebi.atlas.thirdpartyintegration.ebeye.ExperimentDescriptionDao;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ExperimentDescriptionDaoIT {
    @Inject
    private ExperimentDescriptionDao subject;

    private static final ExperimentDescription E_MTAB_513 =
            new ExperimentDescription(
                    "E-MTAB-513", "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)");

    @Test
    public void selectAllPublicExperimentDescriptions() {
        // TODO Get random experiment accession from DB, get description and test
        List<ExperimentDescription> experimentDescriptions = subject.selectAllPublicExperimentDescriptions();
        assertThat(experimentDescriptions, hasItem(E_MTAB_513));
    }
}
