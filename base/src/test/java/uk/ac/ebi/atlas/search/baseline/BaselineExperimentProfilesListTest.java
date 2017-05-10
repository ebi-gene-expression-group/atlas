package uk.ac.ebi.atlas.search.baseline;

import org.junit.Test;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineExperimentProfilesListTest {

    String factorHeader = "type1";

    @Test
    public void testGetFactorsAcrossExperiments() throws Exception {
        BaselineExperimentProfilesList result = new BaselineExperimentProfilesList();

        BaselineExperimentProfile p1 = new BaselineExperimentProfile(BaselineExperimentTest.mockExperiment(), new FactorSet());
        p1.add(new FactorAcrossExperiments(new Factor(factorHeader, "v1")), new BaselineExpression(1.0));
        p1.add(new FactorAcrossExperiments(new Factor(factorHeader, "v2")), new BaselineExpression(2.0));
        result.add(p1);
        assertThat(result.getFactorsAcrossExperiments().size(), is(2));

        BaselineExperimentProfile p2 = new BaselineExperimentProfile(BaselineExperimentTest.mockExperiment(), new FactorSet());
        p2.add(new FactorAcrossExperiments(new Factor(factorHeader, "v1")), new BaselineExpression(1.0));
        p2.add(new FactorAcrossExperiments(new Factor(factorHeader, "v3")), new BaselineExpression(3.0));
        result.add(p2);
        assertThat(result.getFactorsAcrossExperiments().size(), is(3));
    }

    @Test
    public void zerosDoNotMakeItToTheList() throws Exception {
        BaselineExperimentProfilesList result = new BaselineExperimentProfilesList();

        BaselineExperimentProfile p1 = new BaselineExperimentProfile(BaselineExperimentTest.mockExperiment(), new FactorSet());
        p1.add(new FactorAcrossExperiments(new Factor(factorHeader, "v1")), new BaselineExpression(1.0));
        p1.add(new FactorAcrossExperiments(new Factor(factorHeader, "v2")), new BaselineExpression(2.0));
        p1.add(new FactorAcrossExperiments(new Factor(factorHeader, "v3")), new BaselineExpression(0.0));
        result.add(p1);
        assertThat(result.getFactorsAcrossExperiments().size(), is(2));

        BaselineExperimentProfile p2 = new BaselineExperimentProfile(BaselineExperimentTest.mockExperiment(), new FactorSet());
        p2.add(new FactorAcrossExperiments(new Factor(factorHeader, "v1")), new BaselineExpression(1.0));
        p2.add(new FactorAcrossExperiments(new Factor(factorHeader, "v3")), new BaselineExpression(3.0));
        result.add(p2);
        assertThat(result.getFactorsAcrossExperiments().size(), is(3));
    }

}