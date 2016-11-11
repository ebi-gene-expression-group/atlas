package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.thirdpartyintegration.ebeye.ExperimentDescription;
import uk.ac.ebi.atlas.thirdpartyintegration.ebeye.ExperimentDescriptionDAO;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class ExperimentDescriptionDAOIT {

    @Inject
    private ExperimentDescriptionDAO subject;

    private static final ExperimentDescription E_MTAB_513 = new ExperimentDescription("E-MTAB-513", "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)");

    @Test
    public void selectAllPublicExperimentDescriptions() {
        List<ExperimentDescription> experimentDescriptions = subject.selectAllPublicExperimentDescriptions();
        assertThat(experimentDescriptions.size(), greaterThan(50));
        assertThat(experimentDescriptions, hasItem(E_MTAB_513));
    }

}
