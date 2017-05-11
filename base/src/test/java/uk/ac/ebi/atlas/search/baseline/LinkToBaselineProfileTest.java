package uk.ac.ebi.atlas.search.baseline;

import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LinkToBaselineProfileTest {

    FactorGroup factorGroup = new FactorSet().add(new Factor("header", "value"));
    BaselineExperiment experiment = BaselineExperimentTest.mockExperiment("E-MOCK-1");
    BaselineExperimentProfile baselineExperimentProfile = new BaselineExperimentProfile(experiment, factorGroup);

    @Test
    public void useRelativeUrls() throws Exception {

        URI result = new LinkToBaselineProfile(SemanticQuery.create()).apply(baselineExperimentProfile);

        assertThat(result.isAbsolute(), is(false));
    }

    @Test
    public void includeGeneQueryAndExperimentAccession() throws Exception {

        URI result = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(baselineExperimentProfile);

        assertThat(result.toString(), containsString("ASPM"));
        assertThat(result.toString(), containsString(experiment.getAccession()));
    }

}