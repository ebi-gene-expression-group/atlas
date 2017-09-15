package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTOTest;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentOpsTest {

    @Mock
    private ExperimentCrud experimentCrud;
    @Mock
    private BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    @Mock
    private AnalyticsIndexerManager analyticsIndexerManager;
    @Mock
    private ExpressionSerializerService expressionSerializerService;
    @Mock
    private ExperimentTrader experimentTrader;
    @Mock
    private Experiment experimentMock;

    private String accession = "E-EXAMPLE-1";

    private ExperimentOps subject;

    private ExperimentOpLogWriter experimentOpLogWriter;

    @Before
    public void setUp() throws Exception {
        experimentOpLogWriter = new ExperimentOpLogWriter(new MockDataFileHub());

        subject = new ExperimentOps(experimentOpLogWriter,
                new ExpressionAtlasExperimentOpsExecutionService(
                        experimentCrud, baselineCoexpressionProfileLoader, analyticsIndexerManager,
                        expressionSerializerService,experimentTrader));

        when(expressionSerializerService.kryoSerializeExpressionData(any())).thenReturn("skipped");

        when(experimentMock.getAttributes()).thenReturn(new HashMap<String, Object>());
        when(experimentTrader.getExperiment(anyString(), anyString())).thenReturn(experimentMock);

        Mockito.doAnswer(invocationOnMock -> {
            String accession1 = (String) invocationOnMock.getArguments()[0];

            ExperimentType experimentType = ExperimentType.values()
                    [new Random().nextInt(ExperimentType.values().length)];

            return ExperimentDTOTest.mockDTO(accession1, experimentType);
        }).when(experimentCrud).findExperiment(anyString());

    }

    @Test
    public void allOpsReturnTheSameKindOfJson() throws Exception {
        Random rand = new Random();

        for (Op op : Op.values()) {
            List<String> accessions = new ArrayList<>();
            for (int i = -1; i < rand.nextInt(5); i++) {
                accessions.add("E-DUMMY-" + rand.nextInt(10000));
            }

            Iterator<JsonElement> result =
                    subject.dispatchAndPerform(accessions, Collections.singleton(op)).iterator();

            int i = 0;
            while (result.hasNext()) {
                JsonObject o = result.next().getAsJsonObject();
                assertThat(accessions.get(i), is(o.get("accession").getAsString()));
                if (o.get("error") == null) {
                    assertThat(o.get("result"), is(not(nullValue())));
                    assertThat(o.get("error"), is(nullValue()));
                } else {
                    assertThat(o.get("result"), is(nullValue()));
                    assertThat(o.get("error").getAsString(), startsWith("Op not supported in Expression Atlas"));
                }
                i++;
            }
        }
    }

    @Test
    public void aggregateOpsInANeatFashion() throws Exception {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        doThrow(new RuntimeException("Woosh!")).when(experimentCrud).deleteExperiment(accession);
        List<Op> ops= new ArrayList<>();
        ops.add(Op.UPDATE_DESIGN_ONLY); // says "success!"
        ops.add(Op.CLEAR_LOG); // says "success!"
        ops.add(Op.LIST); // says something else
        ops.add(Op.DELETE); //throws, should give an error
        ops.add(Op.COEXPRESSION_DELETE); // should not be started
        ops.add(Op.COEXPRESSION_IMPORT); // should not be started

        JsonObject result = subject.dispatchAndPerform(Collections.singletonList(accession), ops).iterator().next().getAsJsonObject();

        assertThat(result.has("accession"), is(true));
        assertThat(result.get("accession").getAsJsonPrimitive()
                .getAsString(), is(accession));
        JsonArray successes = result.get("result").getAsJsonArray();
        JsonArray failures = result.get("error").getAsJsonArray();

        assertThat(successes.toString(), successes.size(), is(new String[]{"success!", "<sth else>"}.length));
        assertThat(failures.toString(),failures.size(), is(new String[]{"<error msg>", "not started"}.length));

        Set<Map.Entry<String, JsonElement>> firstSuccess = successes.get(0).getAsJsonObject().entrySet();
        assertThat(firstSuccess.size(), is(1));
        String opNameOfFirstSuccessEntry = firstSuccess.iterator().next().getKey();
        assertThat(opNameOfFirstSuccessEntry, containsString(Op.UPDATE_DESIGN_ONLY.name()));
        assertThat(opNameOfFirstSuccessEntry, containsString(Op.CLEAR_LOG.name()));

        Set<Map.Entry<String, JsonElement>> secondSuccess = successes.get(1).getAsJsonObject().entrySet();
        assertThat(secondSuccess.size(), is(1));
        assertThat(Op.LIST.name(), is(secondSuccess.iterator().next().getKey()));

        Set<Map.Entry<String, JsonElement>> firstFailure = failures.get(0).getAsJsonObject().entrySet();
        assertThat(firstFailure.size(), is(1));
        assertThat(Op.DELETE.name(), is(firstFailure.iterator().next().getKey()));


        Set<Map.Entry<String, JsonElement>> secondFailure = failures.get(1).getAsJsonObject().entrySet();
        assertThat(secondFailure.size(), is(1));
        String key3 = secondFailure.iterator().next().getKey();
        assertThat(key3, containsString(Op.COEXPRESSION_DELETE.name()));
        assertThat(key3, containsString(Op.COEXPRESSION_IMPORT.name()));
    }

    @Test
    public void statefulOpsModifyTheOpLog() throws Exception {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (Op op : Op.values()) {
            if (!op.equals(Op.CLEAR_LOG)) {
                ImmutableList.copyOf(subject.dispatchAndPerform(Collections.singletonList(accession), Collections.singletonList(op)));
            }
        }


        String logResult = ImmutableList.copyOf(subject.dispatchAndPerform(Collections.singletonList(accession), Collections.singletonList(Op.LOG))).toString();

        for(Op op: Op.values()){
            boolean isStateful =! Arrays.asList(Op.LIST, Op.LOG, Op.STATUS, Op.CLEAR_LOG, Op.CACHE_READ).contains(op);
            assertThat(op.toString(), logResult.contains( op.name()), is(isStateful));
        }

    }

    @Test
    public void errorLeavesLogDirty() throws Exception {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        doThrow(new RuntimeException("Woosh!")).when(experimentCrud).deleteExperiment(accession);

        JsonObject result = subject.dispatchAndPerform(Collections.singletonList(accession), Collections.singleton(Op
                .DELETE))
                .iterator().next().getAsJsonObject();

        assertThat(accession, is(result.get("accession").getAsString()));
        assertThat(result.get("result"), is(nullValue()));
        assertThat(result.get("error"), is(not(nullValue())));

        assertThat(ImmutableList.copyOf(subject.dispatchAndPerform(Collections.singletonList(accession), Collections.singletonList(Op.LOG))).toString(),
                containsString("error"));

    }

    @Test
    public void loadingExperimentsWorksAsExpectedWhenEverythingIsGood() throws Exception {
        //if not you should update the tests
        assertThat(
                Op.opsForParameter("LOAD_PUBLIC"),
                contains(Op.IMPORT_PUBLIC, Op.COEXPRESSION_UPDATE, Op.SERIALIZE, Op.ANALYTICS_IMPORT));

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC", new MockHttpServletResponse());

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCrud).findExperiment(accession);
        verify(expressionSerializerService).kryoSerializeExpressionData(any(Experiment.class));
        verify(analyticsIndexerManager).addToAnalyticsIndex(accession);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled1() throws Exception {
        doThrow(new RuntimeException("The files are bad!"))
                .when(experimentCrud)
                .importExperiment(accession,false);

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC", new MockHttpServletResponse());

        verify(experimentCrud).importExperiment(accession, false);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled2() throws Exception {
        doThrow(new RuntimeException("Database down, or something"))
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(accession);

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC",new MockHttpServletResponse());

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled3() throws Exception {
        doThrow(new RuntimeException("Serializing failed"))
                .when(expressionSerializerService)
                .kryoSerializeExpressionData(any(Experiment.class));

        MockHttpServletResponse responseObject = new MockHttpServletResponse();

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC",responseObject);


        String response = responseObject.getContentAsString();

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCrud).findExperiment(accession);
        verify(expressionSerializerService).kryoSerializeExpressionData(any(Experiment.class));

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);

        assertThat(response, containsString("Serializing failed"));
        assertThat(response, containsString("error"));
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled4() throws Exception {
        doThrow(new NullPointerException())
                .when(expressionSerializerService)
                .kryoSerializeExpressionData(any(Experiment.class));

        MockHttpServletResponse responseObject = new MockHttpServletResponse();
        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC",responseObject);
        String response = responseObject.getContentAsString();

        assertThat(response, containsString("error"));
    }

    private String readFromStatus(List<OpLogEntry> persisted) throws Exception{
        String accession = "ACCESSION-statusReadsOpLog";
        experimentOpLogWriter.persistOpLog(accession, persisted);

        MockHttpServletResponse responseObject = new MockHttpServletResponse();
        new ExperimentAdminController(subject).doOp(accession, "STATUS",responseObject);

        JsonArray response =
                new Gson().fromJson(responseObject.getContentAsString(), JsonArray.class).getAsJsonArray();

        return response.get(0).getAsJsonObject().get("result").getAsString();
    }

    @Test
    public void statusReadsLastOpLogEntry() throws Exception {
        assertThat(readFromStatus(ImmutableList.of()), is(""));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.NULL("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.NULL("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), not(containsString("msg")));
    }

}
