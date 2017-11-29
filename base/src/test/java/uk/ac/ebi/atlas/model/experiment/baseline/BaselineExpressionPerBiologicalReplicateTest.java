package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.model.BiologicalReplicate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineExpressionPerBiologicalReplicateTest {

    /*
    The data production pipeline uses the same method.
     */
    @Test
    public void expressionLevelIsTheMedian() throws Exception {

        assertThat(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0)
                        )
                ).getLevel(), is(1.0));

        assertThat(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(1.0)

                        )
                ).getLevel(), is(1.0));

        assertThat(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(2.0)

                        )
                ).getLevel(), is(1.5));

    }

    @Test
    public void toJson() {
        assertThat(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(2.0),
                                new BiologicalReplicate("t1", ImmutableSet.of("assay_3", "assay_4")), new BaselineExpression(0.5)
                        )
                ).toJson(), is(new Gson().fromJson(
                        "{\n" +
                                "    \"stats\": {\n" +
                                "        \"total\": 3.5,\n" +
                                "        \"min\": 0.5,\n" +
                                "        \"lower_quartile\": 0.5,\n" +
                                "        \"median\": 1.0,\n" +
                                "        \"upper_quartile\": 0.5,\n" +
                                "        \"max\": 2.0\n" +
                                "    },\n" +
                                "    \"values\": [{\n" +
                                "        \"id\": \"assay_1\",\n" +
                                "        \"assays\": [\"assay_1\"],\n" +
                                "        \"value\": {\n" +
                                "            \"expression_absolute_units\": 1.0,\n" +
                                "            \"expression_as_fraction_of_total\": 0.286,\n" +
                                "            \"expression_dominance\": \"present\"\n" +
                                "        }\n" +
                                "    }, {\n" +
                                "        \"id\": \"assay_2\",\n" +
                                "        \"assays\": [\"assay_2\"],\n" +
                                "        \"value\": {\n" +
                                "            \"expression_absolute_units\": 2.0,\n" +
                                "            \"expression_as_fraction_of_total\": 0.571,\n" +
                                "            \"expression_dominance\": \"present\"\n" +
                                "        }\n" +
                                "    }, {\n" +
                                "        \"id\": \"t1\",\n" +
                                "        \"assays\": [\"assay_3\", \"assay_4\"],\n" +
                                "        \"value\": {\n" +
                                "            \"expression_absolute_units\": 0.5,\n" +
                                "            \"expression_as_fraction_of_total\": 0.143,\n" +
                                "            \"expression_dominance\": \"present\"\n" +
                                "        }\n" +
                                "    }]\n" +
                                "}", JsonObject.class
                )));
    }

    void hasDominantTranscript(BaselineExpressionPerBiologicalReplicate values, boolean t) {

        assertThat(new Gson().toJson(values.toJson()).contains(BaselineExpressionPerBiologicalReplicate.DOMINANCE_AMONG_RELATED_VALUES.dominant.name()), is(t));
    }

    @Test
    public void hasDominantTranscript() {
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                        )
                ), false
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(0.0)
                        )
                ), false
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0)
                        )
                ), true
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(1.0)
                        )
                ), false
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(100.0)
                        )
                ), true
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(100.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(1.0)
                        )
                ), true
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(100.0),
                                new BiologicalReplicate("assay_3"), new BaselineExpression(100.0)
                        )
                ), false
        );
        hasDominantTranscript(
                new BaselineExpressionPerBiologicalReplicate(
                        ImmutableMap.of(
                                new BiologicalReplicate("assay_1"), new BaselineExpression(1.0),
                                new BiologicalReplicate("assay_2"), new BaselineExpression(100.0),
                                new BiologicalReplicate("assay_3"), new BaselineExpression(1000.0)
                        )
                ), true
        );
    }
}