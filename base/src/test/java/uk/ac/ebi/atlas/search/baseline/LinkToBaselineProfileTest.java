package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
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
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class LinkToBaselineProfileTest {

    String factorHeader = "header";
    String queryFactorType = Factor.normalize(factorHeader);
    FactorGroup factorGroup = new FactorSet().add(new Factor(factorHeader, "value"));
    BaselineExperiment singleFactorExperiment = BaselineExperimentTest.mockExperiment("single_factor_experiment_accession");
    BaselineExperimentProfile singleFactorProfile = new BaselineExperimentProfile(singleFactorExperiment, factorGroup.withoutTypes(ImmutableList.of(queryFactorType)));

    FactorGroup twoFactorsFactorGroup = new FactorSet().add(new Factor(factorHeader, "value")).add(new Factor("header_2", "value_21"));
    FactorGroup secondTwoFactorsFactorGroup = new FactorSet().add(new Factor(factorHeader, "value")).add(new Factor("header_2", "value_22"));
    BaselineExperiment twoFactorExperiment = BaselineExperimentTest.mockExperiment("two_factor_experiment_accession");
    BaselineExperimentProfile twoFactorProfile = new BaselineExperimentProfile(twoFactorExperiment, twoFactorsFactorGroup.withoutTypes(ImmutableList.of(queryFactorType)));
    BaselineExperimentProfile secondTwoFactorProfile = new BaselineExperimentProfile(twoFactorExperiment, secondTwoFactorsFactorGroup.withoutTypes(ImmutableList.of(queryFactorType)));


    @Test
    public void useRelativeUrls() throws Exception {

        URI result = new LinkToBaselineProfile(SemanticQuery.create()).apply(singleFactorProfile);

        assertThat(result.isAbsolute(), is(false));
    }

    @Test
    public void includeGeneQueryAndExperimentAccession() throws Exception {

        URI result = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(singleFactorProfile);

        assertThat(result.toString(), containsString("ASPM"));
        assertThat(result.toString(), containsString(singleFactorExperiment.getAccession()));
    }

    @Test
    public void profilesWithDifferentFactorGroupsHaveDifferentUrls(){
        URI result = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(twoFactorProfile);
        URI secondResult = new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(secondTwoFactorProfile);

        assertNotEquals(result, secondResult);
    }

    @Test
    public void geneQueryParameter(){

        assertThat(new LinkToBaselineProfile(SemanticQuery.create("ASPM")).apply(singleFactorProfile).toString(),
                containsString("geneQuery="));

        assertThat(new LinkToBaselineProfile(SemanticQuery.create()).apply(singleFactorProfile).toString(),
                not(containsString("geneQuery=")));
    }

    @Test
    public void filterFactorsParameter(){

        LinkToBaselineProfile linkToBaselineProfile = new LinkToBaselineProfile(SemanticQuery.create("ASPM"));

        assertThat(linkToBaselineProfile.apply(twoFactorProfile).toString(),
                containsString("filterFactors="));
        assertThat(linkToBaselineProfile.apply(singleFactorProfile).toString(),
                not(containsString("filterFactors=")));

    }

}