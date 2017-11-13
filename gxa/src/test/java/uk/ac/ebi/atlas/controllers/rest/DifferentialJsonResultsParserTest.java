package uk.ac.ebi.atlas.controllers.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalytics;

import uk.ac.ebi.atlas.trader.ContrastTrader;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialJsonResultsParserTest {

    private DifferentialJsonResultsParser subject;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Mock
    private ContrastTrader contrastTraderMock;

    @Before
    public void setUp() throws Exception {

        subject = new DifferentialJsonResultsParser(contrastTraderMock);
    }

    @Test
    public void invalidJsonResultThrows() throws Exception {

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Input Json is missing a results field OR results field is not an Array");

        JsonObject result = new JsonObject();
        result.addProperty("results",1);

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);
    }

    @Test
    public void emptyJsonResultArrayProducesEmptyList() throws Exception {

        JsonObject result = new JsonObject();

        result.add("results", new JsonArray());

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);
        assertThat(expression,hasSize(0));
    }

    @Test
    public void numberOfJsonObjectsInResultArrayProducesEqualLengthList() throws Exception {

        JsonObject result = new JsonObject();
        JsonArray results = new JsonArray();

        for(int i = 1; i<=3; i++) {

            JsonObject element = new JsonObject();
            element.addProperty("experiment_accession", "e"+ i);
            element.addProperty("contrast_id", "c"+ i);
            element.addProperty("bioentity_identifier", "id"+ i);
            element.addProperty("species", "org"+ i);
            element.addProperty("p_value", 1.11);
            element.addProperty("fold_change", -1.12);
            element.addProperty("t_statistic", 0.0);
            element.addProperty("keyword_symbol", i);
            results.add(element);
        }

        result.add("results", results);

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);
        assertThat(expression,hasSize(3));
    }

    @Test
    public void secondJsonObjectsInResultArrayEqualsSecondElementOfList() throws Exception {

        JsonObject result = new JsonObject();
        //JsonObject testObject = new JsonObject();
        JsonArray results = new JsonArray();

        for(int i = 1; i<=3; i++) {

            JsonObject element = new JsonObject();
            element.addProperty("experiment_accession", "e"+ i);
            element.addProperty("contrast_id", "c"+ i);
            element.addProperty("bioentity_identifier", "id"+ i);
            element.addProperty("species", "org"+ i);
            element.addProperty("p_value", 1.11);
            element.addProperty("fold_change", -1.12);
            element.addProperty("t_statistic", 0.0);
            element.addProperty("keyword_symbol", i);
            results.add(element);
        }

        result.add("results", results);

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);

        assertThat(expression.get(1).getExperimentAccession(),isA(String.class));
        assertThat(expression.get(1).getBioentityId(),isA(String.class));
        assertThat(expression.get(1).getSpecies(),isA(String.class));
        assertThat(expression.get(1).getExpression().getPValue(),isA(Double.class));
        assertThat(expression.get(1).getExpression().getFoldChange(),isA(Double.class));
    }

    @Test
    public void rejectInvalidJsonObjectsInResultArray() throws Exception {

        JsonObject result = new JsonObject();
        JsonObject testObject = new JsonObject();
        JsonArray results = new JsonArray();

        for(int i = 1; i<=3; i++) {

            JsonObject element = new JsonObject();
            element.addProperty("experiment_accession", "e"+ i);
            element.addProperty("contrast_id", "c"+ i);
            element.addProperty("bioentity_identifier", "id"+ i);
            element.addProperty("species", "org"+ i);
            element.addProperty("p_value", 1.11);
            element.addProperty("fold_change", -1.12);
            element.addProperty("t_statistic", 0.0);
            element.addProperty("keyword_symbol", i);
            results.add(element);
        }

        results.add(testObject);

        testObject.addProperty("experiment accession","e"+4);
        testObject.addProperty("contrast_id", "c"+4);
        testObject.addProperty("bioentity_identifier", "id"+4);
        testObject.addProperty("species", "org"+4);
        testObject.addProperty("p_value", 1.14);
        testObject.addProperty("fold_change", -1.14);
        testObject.addProperty("t_statistic", 0.0);
        testObject.addProperty("keyword_symbol", 4);


        result.add("results", results);

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);

        assertThat(expression,hasSize(3));
    }

    @Test
    public void testingJsonObjectWithoutTstatistic() throws Exception {

        JsonObject testObject = new JsonObject();

        testObject.addProperty("experiment_accession","e"+4);
        testObject.addProperty("contrast_id", "c"+4);
        testObject.addProperty("bioentity_identifier", "id"+4);
        testObject.addProperty("species", "org"+4);
        testObject.addProperty("p_value", 1.14);
        testObject.addProperty("fold_change", -1.14);
        testObject.addProperty("keyword_symbol", 4);

        JsonArray testArray = new JsonArray();

        testArray.add(testObject);

        JsonObject result = new JsonObject();

        result.add("results", testArray);

        List<DiffAnalytics> expression = subject.parseDifferentialResults(result);

        assertThat(expression,hasSize(1));
    }

}