package uk.ac.ebi.atlas.profiles.json;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import junit.framework.Assert;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSlice;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ExternallyViewableProfilesListTest {

    @Test
    public void weCanSerializeDataForTheWidget() throws Exception {
        Function<BaselineExperimentProfile, URI> provideLinkToProfile = new Function<BaselineExperimentProfile, URI>() {

            @Nullable
            @Override
            public URI apply(@Nullable BaselineExperimentProfile o) {
                try {
                    return new URI("https://www.ebi.ac.uk/gxa/experiments/" + o.getId());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        String defaultQueryFactorType = "type1";

        BaselineExperimentProfile firstProfile = new BaselineExperimentProfile(BaselineExperimentSlice.create
                (BaselineExperimentTest.mockExperiment("experiment_1"),
                new FactorSet()
                .add(new Factor(defaultQueryFactorType, "11"))
                .add(new Factor("type2", "12"))
                .add(new Factor("type3", "13"))));

        FactorAcrossExperiments f11 = new FactorAcrossExperiments(new Factor(defaultQueryFactorType, "11"));
        firstProfile.add(f11, new BaselineExpression(12.34, "11"));

        BaselineExperimentProfile secondProfile = new BaselineExperimentProfile(BaselineExperimentSlice.create(
                BaselineExperimentTest.mockExperiment("experiment_2"),
                new FactorSet()
                        .add(new Factor(defaultQueryFactorType, "21"))));
        FactorAcrossExperiments f21 = new FactorAcrossExperiments(new Factor(defaultQueryFactorType, "21"));

        secondProfile.add(f21, new BaselineExpression(56.78, "21"));


        FactorAcrossExperiments f3 = new FactorAcrossExperiments(new Factor(defaultQueryFactorType, "3"));

        List<FactorAcrossExperiments> factorsAcrossExperiments = ImmutableList.of(f11, f21, f3);

        BaselineExperimentProfilesList profiles = new BaselineExperimentProfilesList();
        profiles.add(firstProfile);
        profiles.add(secondProfile);

        JsonObject result = new ExternallyViewableProfilesList<>(profiles, provideLinkToProfile, factorsAcrossExperiments).asJson();


        assertThat(result.get("expressions").getAsJsonArray().size(),
                is(factorsAcrossExperiments.size()));

        assertThat(result.get("expressions").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsDouble(),
                is(12.34));
        assertThat(result.get("expressions").getAsJsonArray().get(1).getAsJsonObject().get("value").getAsDouble(),
                is(56.78));
        assertThat(result.get("expressions").getAsJsonArray().get(2).getAsJsonObject(), is(new JsonObject()));

        Assert.fail("Look at the output and write some tests");

    }


    @Test
    public void weCanSerializeDataForTheBaselinePage() throws Exception {
        Function<BaselineProfile, URI> provideLinkToProfile = new Function<BaselineProfile, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable BaselineProfile o) {
                try {
                    return new URI("https://www.ebi.ac.uk/gxa/genes/" + o.getId());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
        AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");

        List<AssayGroup> factorsAcrossExperiments = ImmutableList.of(g1, g2);


        BaselineExperiment experiment = BaselineExperimentTest.mockExperiment(mock(ExperimentalFactors.class),
                mock(ExperimentDesign.class), factorsAcrossExperiments, "accession");

        BaselineProfile p1 = new BaselineProfile("gene_1_id", "gene_1_name");
        p1.add(g1, new BaselineExpression(1000, "g1"));
        p1.add(g2, new BaselineExpression(2000, "g2"));


        BaselineProfile p2 = new BaselineProfile("gene_2_id", "gene_2_name");
        p2.add(g1, new BaselineExpression(3000, "g1"));

        BaselineProfilesList profiles = new BaselineProfilesList();
        profiles.add(p1);
        profiles.add(p2);

        JsonObject result = new ExternallyViewableProfilesList<>(profiles, provideLinkToProfile, factorsAcrossExperiments).asJson();


        assertThat(result.get("expressions").getAsJsonArray().size(),
                is(factorsAcrossExperiments.size()));

        Assert.fail("Look at the output and write some tests");
    }

    @Test
    public void weCanSerializeDataForTheDifferentialPage() throws Exception {
        Function<RnaSeqProfile, URI> provideLinkToProfile = new Function<RnaSeqProfile, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable RnaSeqProfile o) {
                try {
                    return new URI("https://www.ebi.ac.uk/gxa/genes/" + o.getId());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
        AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");
        AssayGroup g3 = new AssayGroup("g3", "run_31", "run_32");

        Contrast g1_g2 = new Contrast("g1_g2","", g1, g2, "first contrast");
        Contrast g1_g3 = new Contrast("g1_g2","", g1, g3, "second contrast");


        List<Contrast> factorsAcrossExperiments = ImmutableList.of(g1_g2, g1_g3);


        DifferentialExperiment experiment = DifferentialExperimentTest.mockExperiment("accession", factorsAcrossExperiments);

        RnaSeqProfile p1 = new RnaSeqProfile("gene_1_id", "gene_1_name");
        p1.add(g1_g2, new DifferentialExpression(1000, 10, ""));
        p1.add(g1_g3, new DifferentialExpression(2000, 20, ""));


        RnaSeqProfile p2 = new RnaSeqProfile("gene_2_id", "gene_2_name");
        p2.add(g1_g2, new DifferentialExpression(3000, 30, ""));

        DifferentialProfilesList<RnaSeqProfile> profiles = new DifferentialProfilesList<>();
        profiles.add(p1);
        profiles.add(p2);

        JsonObject result = new ExternallyViewableProfilesList<>(profiles, provideLinkToProfile, factorsAcrossExperiments).asJson();


        assertThat(result.get("expressions").getAsJsonArray().size(),
                is(factorsAcrossExperiments.size()));

        Assert.fail("Look at the output and write some tests");
    }
}