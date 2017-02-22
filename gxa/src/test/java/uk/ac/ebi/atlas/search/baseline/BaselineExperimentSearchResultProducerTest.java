package uk.ac.ebi.atlas.search.baseline;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class BaselineExperimentSearchResultProducerTest {

    @Test
    public void extractAverageExpressionLevel() throws IOException{
        List<BaselineExperimentExpression> expressions = BaselineExperimentSearchResultProducer.extractAverageExpressionLevel(loadJsonWithExperiments());

        BaselineExperimentExpression expression1 = BaselineExperimentExpression.create("E-MTAB-1733", "g22", BaselineExpressionLevelRounder.round(467512.3999999801 / 25247));
        BaselineExperimentExpression expression2 = BaselineExperimentExpression.create("E-MTAB-1733", "g15", BaselineExpressionLevelRounder.round(486396.0999999961 / 25247));
        BaselineExperimentExpression expression3 = BaselineExperimentExpression.create("E-MTAB-513", "g13", BaselineExpressionLevelRounder.round(4510.1 / 352));

        assertThat(expressions, contains(expression1, expression2, expression3));
    }

    private static List<Map<String, Object>> loadJsonWithExperiments() throws IOException {
        InputStream in = BaselineExperimentSearchResultProducerTest.class.getResourceAsStream("/uk/ac/ebi/atlas/search/analyticsindex/baseline/baseline.heatmap.pivot.response.json");

        return JsonPath.read(in, BaselineAnalyticsSearchDao.EXPERIMENTS_PATH);
    }
}