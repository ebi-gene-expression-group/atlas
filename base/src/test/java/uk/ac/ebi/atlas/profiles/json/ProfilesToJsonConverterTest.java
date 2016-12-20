package uk.ac.ebi.atlas.profiles.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSlice;

import java.net.URI;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ProfilesToJsonConverterTest {

    ProfilesToJsonConverter<Factor,BaselineExperimentProfile> subject;

    BaselineExperiment experiment = BaselineExperimentTest.mockExperiment();

    @Before
    public void setUp() throws Exception{
        subject = ProfilesToJsonConverter
                .createForCrossExperimentResults(
                        new URI("https://www.ebi.ac.uk/gxa/experiments"), ImmutableMap.of("geneQuery", SemanticQuery.create
                                ("zinc finger").toUrlEncodedJson())
                );
    }

    @Test
    public void valuesCorrespondToOrderedFactors() throws Exception {

        FactorSet factors = new FactorSet();

        factors.add(new Factor("type1", "value1"))
                .add(new Factor("type2", "value2"))
                .add(new Factor("type3", "value3"));

        List<Factor> orderedFactors = ImmutableList.of(new Factor("type3", "value3"),new Factor("type3",
                "different_value"));

        BaselineExperimentProfile profile = new BaselineExperimentProfile(BaselineExperimentSlice.create(experiment,
                factors));
        profile.add("type3", new BaselineExpression(13.37, factors));

        JsonObject result = subject.convert(profile, orderedFactors);

        assertThat(result.get("expressions").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsDouble(), is(13.37));
        assertThat(result.get("expressions").getAsJsonArray().get(1).getAsJsonObject(), is(new JsonObject()));
    }

    @Test
    public void factorValuesCanBeReallyFunky() throws Exception {

        FactorSet factors = new FactorSet();

        factors.add(new Factor("type1", "really funky value of type 1"))
                .add(new Factor("type2", "¯\\_(ツ)_/¯"))
                .add(new Factor("type3", "≧◔◡◔≦"));

        List<Factor> orderedFactors = ImmutableList.of(new Factor("type3", "≧◔◡◔≦"));

        BaselineExperimentProfile profile = new BaselineExperimentProfile(BaselineExperimentSlice.create(experiment,
                factors));
        profile.add("type3", new BaselineExpression(13.37, factors));

        JsonObject result = subject.convert(profile, orderedFactors);

        assertThat(result.get("expressions").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsDouble(), is(13.37));
    }
}