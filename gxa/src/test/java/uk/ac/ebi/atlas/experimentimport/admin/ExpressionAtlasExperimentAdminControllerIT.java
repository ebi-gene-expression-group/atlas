package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrudIT;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class ExpressionAtlasExperimentAdminControllerIT {

    @Inject
    ExpressionAtlasExperimentAdminController subject;

    String accession = ExperimentCrudIT.accession_rnaseq_baseline;


    @After
    public void tryCleanUp() throws Exception{
        subject.doOp(accession,Op.CLEAR_LOG.name(), new MockHttpServletResponse());
        subject.doOp(accession,Op.DELETE.name(), new MockHttpServletResponse());
        subject.doOp(accession,Op.CLEAR_LOG.name(), new MockHttpServletResponse());
    }
    @Test
    public void ourScenario() throws Exception{
        MockHttpServletResponse response;

        response = new MockHttpServletResponse();
        subject.doOp(accession,Op.CACHE_READ.name(), response);
        isError(
                "Before import cache read should report not found",
                response.getContentAsString()
        );

        response = new MockHttpServletResponse();
        subject.doOp(accession, Joiner.on(",").join(ImmutableList.copyOf(Op.opsForParameter("LOAD"))), response);
        isOk(
                "Load is fine",
                response.getContentAsString()
        );

        response = new MockHttpServletResponse();
        subject.doOp(accession,Op.CACHE_READ.name(), response);
        isOk(
                "Cache read after load is fine",
                response.getContentAsString()
        );

        response = new MockHttpServletResponse();
        subject.doOp(accession,Op.DELETE.name(), response);
        isOk(
                "Delete is fine",
                response.getContentAsString()
        );

        response = new MockHttpServletResponse();
        subject.doOp(accession,Op.CACHE_READ.name(), response);
        isError(
                "After delete cache read should report not found",
                response.getContentAsString()
        );

        response = new MockHttpServletResponse();
        subject.doOp(accession,Op.CLEAR_LOG.name(), response);
        isOk(
                "Clear log is fine",
                response.getContentAsString()
        );

    }

    void isOk(String messageAboutWhatIsExpected, String result){
        isOk(messageAboutWhatIsExpected+", was: " + result , new Gson().fromJson(result, JsonArray.class).get(0).getAsJsonObject());
    }

    void isError(String messageAboutWhatIsExpected, String result){
        isError(messageAboutWhatIsExpected+", was: " + result , new Gson().fromJson(result, JsonArray.class).get(0).getAsJsonArray().get(0).getAsJsonObject());
    }

    void isOk(String message, JsonObject object){
        assertAboutResponseObject(message, object, true);
    }

    void isError(String message, JsonObject object){
        assertAboutResponseObject(message, object, false);
    }

    void assertAboutResponseObject(String message, JsonObject object, boolean expectedSuccessful){
        assertThat(
                message,
                object.has("result"),
                is(expectedSuccessful)
        );
        assertThat(
                message,
                object.has("error"),
                not(is(expectedSuccessful))
        );
    }





}