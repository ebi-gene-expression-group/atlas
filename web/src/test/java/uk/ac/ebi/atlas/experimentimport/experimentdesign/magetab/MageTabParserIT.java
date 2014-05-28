package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import javax.annotation.Resource;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MageTabParserIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-TABM-713";

    @Resource(name = "microarrayExperimentDesignMageTabParser")
    private MicroarrayExperimentDesignMageTabParser subject;

    @Test
    public void factorIsDescribedByFactorTypeAndNotName() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION).getExperimentDesign();

        assertThat(experimentDesign.getSampleHeaders(), contains("DiseaseState", "Organism", "organism part"));
        assertThat(experimentDesign.getFactorHeaders(), contains("disease state"));


        FactorSet factors = experimentDesign.getFactors("Hybridization 1");
        Factor factor1 = factors.iterator().next();
        assertThat(factor1.getHeader(), is("disease state"));
        assertThat(factor1.getType(), is("DISEASE_STATE"));
    }

}
