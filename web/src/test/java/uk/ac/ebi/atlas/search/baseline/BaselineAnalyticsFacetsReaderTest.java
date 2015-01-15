package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class BaselineAnalyticsFacetsReaderTest {

    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder = new BaselineExpressionLevelRounder();

    private BaselineAnalyticsFacetsReader subject = new BaselineAnalyticsFacetsReader(baselineExpressionLevelRounder);

    @Test
    public void extractAverageExpressionLevel() {
        ImmutableList<RnaSeqBslnExpression> expressions = subject.extractAverageExpressionLevel(loadJson(), "Homo sapiens", "ORGANISM_PART");

        RnaSeqBslnExpression expression1 = RnaSeqBslnExpression.create("E-MTAB-1733", "g22", baselineExpressionLevelRounder.round(467512.3999999801 / 25247));
        RnaSeqBslnExpression expression2 = RnaSeqBslnExpression.create("E-MTAB-1733", "g15", baselineExpressionLevelRounder.round(486396.0999999961 / 25247));
        RnaSeqBslnExpression expression3 = RnaSeqBslnExpression.create("E-MTAB-513", "g13", baselineExpressionLevelRounder.round(4510.1 / 352));

        assertThat(expressions, contains(expression1, expression2, expression3));
    }

    static String loadJson() {
        InputStream in = BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream("/test/analytics.test.json");
        try {
            return IOUtils.toString(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}