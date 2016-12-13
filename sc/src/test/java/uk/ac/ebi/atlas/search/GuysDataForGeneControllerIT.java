package uk.ac.ebi.atlas.search;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class GuysDataForGeneControllerIT {


    @Inject
    GuysDataForGeneController subject;


    @Test
    public void returnsDataForKnownCase() throws Exception {
        JsonObject responseForGoodData = subject.baselineExperimentDataAsArray(SemanticQuery.fromJson
                ("[{\"value\":\"Mxd1\",\"category\":\"symbol\"}]"));

        assertThat(responseForGoodData.entrySet().size(), greaterThan(0));
    }

    @Test
    public void returnsEmptyObjectForMissingData() throws Exception {
        JsonObject responseForGoodData = subject.baselineExperimentDataAsArray(SemanticQuery.create
                ("NONEXISTENT"));

        assertEquals(new JsonObject(), responseForGoodData);
    }

}