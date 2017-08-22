package uk.ac.ebi.atlas.experimentimport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class ExperimentCheckerIT {

    private static final String RNA_SEQ_BASELINE_EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String PROTEOMICS_BASELINE_EXPERIMENT_ACCESSION = "E-PROT-1";
    private static final String RNA_SEQ_DIFFERENTIAL_EXPERIMENT_ACCESION = "E-MTAB-4289";
    private static final String MICROARRAY_1_COLOUR_DIFFERENTIAL_EXPERIMENT_ACCESION = "E-GEOD-34130";
    private static final String MICROARRAY_2_COLOUR_DIFFERENTIAL_EXPERIMENT_ACCESION = "E-GEOD-43049";

    @Inject
    private ExperimentChecker subject;

    @Test
    public void testCheckBaseline() throws Exception {
        subject.checkAllFiles(RNA_SEQ_BASELINE_EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE);
        subject.checkAllFiles(PROTEOMICS_BASELINE_EXPERIMENT_ACCESSION, ExperimentType.PROTEOMICS_BASELINE);
    }

    @Test
    public void testCheckDifferential() throws Exception {
        subject.checkAllFiles(RNA_SEQ_DIFFERENTIAL_EXPERIMENT_ACCESION, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }

    @Test
    public void testCheckMicroarray() throws Exception {
        subject.checkAllFiles(MICROARRAY_1_COLOUR_DIFFERENTIAL_EXPERIMENT_ACCESION, ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
        subject.checkAllFiles(MICROARRAY_2_COLOUR_DIFFERENTIAL_EXPERIMENT_ACCESION, ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
    }

}