package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class CoexpressedGenesSIT extends RestAssuredFixture {

    Matcher<String> resultWith(final Collection<String> elements){
        return new BaseMatcher<String>() {
            @Override
            public void describeTo(Description description) {
            }
            @Override
            public boolean matches(Object o) {
                if (! (o instanceof String)){
                    return false;
                } else {
                    List<String> res = new Gson().fromJson((String) o, List.class);
                    return res.size()>= elements.size() && res.size()<=200 && res.containsAll(elements);
                }
            }
        };
    }

    @Test
    public void getTheRightDataBack(){
        checkWeGetTheRightThing("E-MTAB-3028","GRMZM2G353195", Arrays.<String>asList("AC148152.3_FG005", "GRMZM6G986387"));
        checkWeGetTheRightThing("E-MTAB-3028","bad gene", Collections.EMPTY_LIST);
        checkWeGetTheRightThing("bad experiment","GRMZM2G353195", Collections.EMPTY_LIST);
    }

    @Test
    public void ifThereAreNoParametersProvidedTheServerShouldComplain(){
        Response response = get("/json/experiments/coexpression");

        response.then().assertThat().statusCode(400);
    }


    void checkWeGetTheRightThing(String experiment, String identifier, Collection<String> results) {

        Response response = get(
                String.format("/json/experiments/coexpression?experiment=%s&identifier=%s", experiment,identifier));

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(resultWith(results));
    }
}