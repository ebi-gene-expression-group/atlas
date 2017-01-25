package uk.ac.ebi.atlas.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class HeatmapDataToJsonServiceIT {

    @Inject
    HeatmapDataToJsonService subject;

    @Inject
    ExperimentTrader experimentTrader;

    @Test
    public void testConfigAsJsonObject() throws Exception {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getServerPort()).thenReturn(0);
        when(request.getServerName()).thenReturn("");
        when(request.getContextPath()).thenReturn("");
        when(request.getScheme()).thenReturn("");

        //there are normally some other attributes too
        JsonObject result = subject.configAsJsonObject(request,
                experimentTrader.getPublicExperiment("E-MTAB-513").getAttributes());

        JsonArray resource = result.get("resources").getAsJsonObject().get("genome_browser").getAsJsonArray();

        assertThat(resource.get(0).getAsString(), startsWith("http"));
    }
}