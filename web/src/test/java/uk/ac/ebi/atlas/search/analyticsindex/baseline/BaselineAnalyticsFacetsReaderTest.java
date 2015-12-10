package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BaselineAnalyticsFacetsReaderTest {

    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder = new BaselineExpressionLevelRounder();

    private BaselineAnalyticsFacetsReader subject = new BaselineAnalyticsFacetsReader(baselineExpressionLevelRounder);

    private final static String RESPONSE_2_JSON_FACETS =
            "{\n" +
            "  \"Homo sapiens\": [\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Mus musculus\": [\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void extractAverageExpressionLevel() {
        ImmutableList<BaselineExperimentExpression> expressions = subject.extractAverageExpressionLevel(loadJson(), "Homo sapiens", "ORGANISM_PART");

        BaselineExperimentExpression expression1 = BaselineExperimentExpression.create("E-MTAB-1733", "g22", baselineExpressionLevelRounder.round(467512.3999999801 / 25247));
        BaselineExperimentExpression expression2 = BaselineExperimentExpression.create("E-MTAB-1733", "g15", baselineExpressionLevelRounder.round(486396.0999999961 / 25247));
        BaselineExperimentExpression expression3 = BaselineExperimentExpression.create("E-MTAB-513", "g13", baselineExpressionLevelRounder.round(4510.1 / 352));

        assertThat(expressions, contains(expression1, expression2, expression3));
    }

    static String loadJson() {
        InputStream in = BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream("/test/baseline.heatmap.pivot.response.json");
        try {
            return IOUtils.toString(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    static String load2Json() {
        InputStream in = BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream("/test/baseline.heatmap.pivot.response2.json");
        try {
            return IOUtils.toString(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void extractTreeFacets() {
        String facetsTreeJson = subject.generateFacetsTreeJson(load2Json());

        assertThat(facetsTreeJson, is(RESPONSE_2_JSON_FACETS));
    }

}