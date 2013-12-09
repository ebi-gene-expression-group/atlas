package uk.ac.ebi.atlas.integration.ebisearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExperimentDescriptionDAOIT {

    @Inject
    private ExperimentDescriptionDAO subject;

    private ExperimentDescription E_MTAB_513 = new ExperimentDescription("E-MTAB-513", "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)");

    @Test
    public void selectAllPublicExperimentDescriptions() {
        List<ExperimentDescription> experimentDescriptions = subject.selectAllPublicExperimentDescriptions();
        assertThat(experimentDescriptions.size(), is(14));
        assertThat(experimentDescriptions, hasItem(E_MTAB_513));
    }

}
