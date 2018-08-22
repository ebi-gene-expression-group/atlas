package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class LinkToBaselineProfileTest {
    private String factorHeader = "header";
    private String queryFactorType = Factor.normalize(factorHeader);
    private FactorGroup factorGroup = new FactorSet().add(new Factor(factorHeader, "value"));
    private BaselineExperiment singleFactorExperiment =
            MockExperiment.createBaselineExperiment("single_factor_experiment_accession");
    private BaselineExperimentProfile singleFactorProfile =
            new BaselineExperimentProfile(
                    singleFactorExperiment, factorGroup.withoutTypes(ImmutableList.of(queryFactorType)));

    private FactorGroup twoFactorsFactorGroup =
            new FactorSet().add(new Factor(factorHeader, "value")).add(new Factor("header_2", "value_21"));
    private FactorGroup secondTwoFactorsFactorGroup =
            new FactorSet().add(new Factor(factorHeader, "value")).add(new Factor("header_2", "value_22"));
    private BaselineExperiment twoFactorExperiment =
            MockExperiment.createBaselineExperiment("two_factor_experiment_accession");
    private BaselineExperimentProfile twoFactorProfile =
            new BaselineExperimentProfile(
                    twoFactorExperiment, twoFactorsFactorGroup.withoutTypes(ImmutableList.of(queryFactorType)));
    private BaselineExperimentProfile secondTwoFactorProfile =
            new BaselineExperimentProfile(
                    twoFactorExperiment, secondTwoFactorsFactorGroup.withoutTypes(ImmutableList.of(queryFactorType)));


    @Test
    public void useRelativeUrls() {

        URI result = new LinkToBaselineProfile(SemanticQuery.create()).apply(singleFactorProfile);

        assertThat(result.isAbsolute(), is(false));
    }

    @Test
    public void includeGeneQueryAndExperimentAccession() {

        URI result = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(singleFactorProfile);

        assertThat(result.toString(), containsString("ASPM"));
        assertThat(result.toString(), containsString(singleFactorExperiment.getAccession()));
    }

    @Test
    public void profilesWithDifferentFactorGroupsHaveDifferentUrls() {
        URI result = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(twoFactorProfile);
        URI secondResult = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(secondTwoFactorProfile);

        assertNotEquals(result, secondResult);
    }

    @Test
    public void geneQueryParameter() {

        assertThat(new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(singleFactorProfile).toString(),
                containsString("geneQuery="));

        assertThat(new LinkToBaselineProfile(SemanticQuery.create()).apply(singleFactorProfile).toString(),
                not(containsString("geneQuery=")));
    }

    @Test
    public void filterFactorsParameter() {

        LinkToBaselineProfile linkToBaselineProfile = new LinkToBaselineProfile(SemanticQuery.create("ASPM"));

        assertThat(linkToBaselineProfile.apply(twoFactorProfile).toString(),
                containsString("filterFactors="));
        assertThat(linkToBaselineProfile.apply(singleFactorProfile).toString(),
                not(containsString("filterFactors=")));

    }
}
