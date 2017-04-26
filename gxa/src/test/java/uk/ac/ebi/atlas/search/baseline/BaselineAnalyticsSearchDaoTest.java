package uk.ac.ebi.atlas.search.baseline;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchDao;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaselineAnalyticsSearchDaoTest {

    @Mock
    AnalyticsQueryClient analyticsQueryClient;

    BaselineAnalyticsSearchDao subject;

    @Before
    public void setUp(){
        subject = new BaselineAnalyticsSearchDao(analyticsQueryClient);
    }

    @Test
    public void extractAverageExpressionLevel() throws IOException{
        Map<String, Map<String, Double>> expressions = subject.aggregateExpressionLevelByDataColumnAndExperiment(loadJsonWithExperiments());

        assertThat(expressions.get("E-MTAB-1733").get("g22"), is(BaselineExpressionLevelRounder.round(467512.3999999801 / 25247)));
        assertThat(expressions.get("E-MTAB-1733").get("g15"), is(BaselineExpressionLevelRounder.round(486396.0999999961 / 25247)));
        assertThat(expressions.get("E-MTAB-513").get("g13"), is(BaselineExpressionLevelRounder.round(4510.1 / 352)));
    }

    private static List<Map<String, Object>> loadJsonWithExperiments() throws IOException {
        InputStream in = BaselineAnalyticsSearchDaoTest.class.getResourceAsStream("/uk/ac/ebi/atlas/search/analyticsindex/baseline/baseline.heatmap.pivot.response.json");

        return JsonPath.read(in, BaselineAnalyticsSearchDao.EXPERIMENTS_PATH);
    }

}