package uk.ac.ebi.atlas.expdesign;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaselineExperimentDesignMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    private static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    @Inject
    private MageTabLimpopoUtils mageTabLimpopoUtils;

    @Inject
    private PropertyMergeService propertyMergeService;

    private RnaSeqExperimentDesignMageTabParser subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqExperimentDesignMageTabParser();
        subject.setMageTabLimpopoUtils(mageTabLimpopoUtils);
        subject.setPropertyMergeService(propertyMergeService);
    }


    @Test
    public void testExtractCharacteristics513() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_MTAB_513);
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("sex", "age", "organism part", "Organism", "ethnic group"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION_E_GEOD_26284);
        assertThat(experimentDesign.getSampleHeaders(), containsInAnyOrder("sex", "biosource provider", "cell line", "cellular component", "organism part", "karyotype", "disease state", "cell type", "Organism"));
    }

}