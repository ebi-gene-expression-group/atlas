package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTOTest;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentOpsTest {

    @Mock
    private ExperimentCrud experimentCrud;
    @Mock
    private ExperimentOpLogWriter experimentOpLogWriter;
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

    private Map<String, ImmutableList<OpLogEntry>> fileSystem;

    private String accession = "E-EXAMPLE-1";

    private ExperimentOps subject;

    @Before
    public void setUp() {
        subject = new ExperimentOps(experimentOpLogWriter,
                new ExpressionAtlasExperimentOpsExecutionService(
                        experimentCrud, baselineCoexpressionProfileLoader, analyticsIndexerManager,
                        expressionSerializerService,experimentTrader));

        fileSystem = new HashMap<>();

        when(expressionSerializerService.kryoSerializeExpressionData(Matchers.any(Experiment.class))).thenReturn("skipped");

        when(experimentMock.getAttributes()).thenReturn(new HashMap<String, Object>());
        when(experimentTrader.getExperiment(anyString(), anyString())).thenReturn(experimentMock);

        Mockito.doAnswer(new Answer<ExperimentDTO>() {
            @Override
            public ExperimentDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String) invocationOnMock.getArguments()[0];

                ExperimentType experimentType = ExperimentType.values()
                        [new Random().nextInt(ExperimentType.values().length)];

                return ExperimentDTOTest.mockDTO(accession, experimentType);
            }
        }).when(experimentCrud).findExperiment(Matchers.anyString());


        Mockito.doAnswer(new Answer<ImmutableList<OpLogEntry>>() {
            @Override
            public ImmutableList<OpLogEntry> answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String) invocationOnMock.getArguments()[0];
                if (fileSystem.containsKey(accession)) {
                    return fileSystem.get(accession);
                } else {
                    return ImmutableList.of();
                }
            }
        }).when(experimentOpLogWriter).getCurrentOpLog(Matchers.anyString());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String)
                        invocationOnMock.getArguments()[0];
                ImmutableList<OpLogEntry> opLog =
                        (ImmutableList<OpLogEntry>) invocationOnMock.getArguments()[1];
                fileSystem.put(accession, opLog);
                return null;
            }
        }).when(experimentOpLogWriter)
                .persistOpLog(Matchers.anyString(), Matchers.<List<OpLogEntry>>any());
    }

    @Test
    public void allOpsReturnTheSameKindOfJson() {
        Random rand = new Random();

        for (Op op : Op.values()) {
            List<String> accessions = new ArrayList<>();
            for (int i = -1; i < rand.nextInt(5); i++) {
                accessions.add("E-DUMMY-" + rand.nextInt(10000));
            }

            Iterator<JsonElement> result = subject.perform(Optional.of(accessions), Collections.singleton(op))
                    .iterator();

            int i = 0;
            while (result.hasNext()) {
                JsonObject o = result.next().getAsJsonObject();
                assertThat(accessions.get(i), is(o.get("accession").getAsString()));
                Assert.assertNotNull(o.get("result"));
                Assert.assertNull(o.get("error"));
                i++;
            }
        }
    }

    @Test
    public void aggregateOpsInANeatFashion() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        when(experimentCrud.deleteExperiment(accession)).thenThrow(new RuntimeException("Woosh!"));
        List<Op> ops= new ArrayList<>();
        ops.add(Op.UPDATE_DESIGN_ONLY); // says "success!"
        ops.add(Op.CLEAR_LOG); // says "success!"
        ops.add(Op.LIST); // says something else
        ops.add(Op.DELETE); //throws, should give an error
        ops.add(Op.COEXPRESSION_DELETE); // should not be started
        ops.add(Op.COEXPRESSION_IMPORT); // should not be started

        JsonArray result = subject.perform(Optional.of(Collections.singletonList(accession)),ops);

        assertThat(result.get(0).getAsJsonObject().has("accession"), is(true));
        assertThat(result.get(0).getAsJsonObject().get("accession").getAsJsonPrimitive()
                .getAsString(), is(accession));
        JsonArray successes = result.get(0).getAsJsonObject().get("result").getAsJsonArray();
        JsonArray failures = result.get(0).getAsJsonObject().get("error").getAsJsonArray();

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
    public void statefulOpsModifyTheOpLog() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (Op op : Op.values()) {
            if (!op.equals(Op.CLEAR_LOG)) {
                subject.perform(Optional.of(Collections.singletonList(accession)), Collections.singletonList(op));
            }
        }
        assertThat(
                Op.values().length - Arrays.asList(Op.LIST, Op.LOG, Op.STATUS, Op.CLEAR_LOG, Op.CACHE_READ).size(),
                is(fileSystem.get(accession).size()));
    }

    @Test
    public void timestampsLookRight() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (Op op : Op.values()) {
            subject.perform(Optional.of(Collections.singletonList(accession)), Collections.singletonList(op));
        }
        for (OpLogEntry p : fileSystem.get(accession)) {
            Long start = Long.valueOf(p.toArray()[1]);
            Long finish = Long.valueOf(p.toArray()[2]);
            assertThat(finish - start >= 0, is (true));
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, start);
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, finish);
        }
    }

    @Test
    public void errorLeavesLogDirty() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        when(experimentCrud.deleteExperiment(accession)).thenThrow(new RuntimeException("Woosh!"));

        JsonObject result = subject.perform(Optional.of(Collections.singletonList(accession)), Collections.singleton(Op
                .DELETE))
                .iterator().next().getAsJsonObject();

        assertThat(accession, is(result.get("accession").getAsString()));
        assertThat(result.get("result"), is(nullValue()));
        assertThat(result.get("error"), is(not(nullValue())));

        assertThat(fileSystem.get(accession).iterator().next().toJson().has("error"), is(true));

    }

    @Test
    public void loadingExperimentsWorksAsExpectedWhenEverythingIsGood() throws Exception {
        //if not you should update the tests
        assertThat(
                Op.opsForParameter("LOAD_PUBLIC"),
                contains(Op.IMPORT_PUBLIC, Op.COEXPRESSION_UPDATE, Op.SERIALIZE, Op.ANALYTICS_IMPORT));

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCrud).findExperiment(accession);
        verify(expressionSerializerService).kryoSerializeExpressionData(Matchers.any(Experiment.class));
        verify(analyticsIndexerManager).addToAnalyticsIndex(accession);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled1() throws Exception {
        Mockito.doThrow(new RuntimeException("The files are bad!"))
                .when(experimentCrud)
                .importExperiment(accession,false);

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCrud).importExperiment(accession, false);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled2() throws Exception {
        Mockito.doThrow(new RuntimeException("Database down, or something"))
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(accession);

        new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled3() throws Exception {
        Mockito.doThrow(new RuntimeException("Serializing failed"))
                .when(expressionSerializerService)
                .kryoSerializeExpressionData(Matchers.any(Experiment.class));

        String response =
                new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCrud).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCrud).findExperiment(accession);
        verify(expressionSerializerService).kryoSerializeExpressionData(Matchers.any(Experiment.class));

        verifyNoMoreInteractions(experimentCrud, experimentCrud,analyticsIndexerManager,baselineCoexpressionProfileLoader);

        assertThat(response, containsString("Serializing failed"));
        assertThat(response, containsString("error"));
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled4() throws Exception {
        Mockito.doThrow(new NullPointerException())
                .when(expressionSerializerService)
                .kryoSerializeExpressionData(Matchers.any(Experiment.class));

        String response =
                new ExperimentAdminController(subject).doOp(accession, "LOAD_PUBLIC");

        assertThat(response, containsString("error"));
    }

    private String readFromStatus(List<OpLogEntry> persisted){
        String accession = "ACCESSION-statusReadsOpLog";
        experimentOpLogWriter.persistOpLog(accession, persisted);

        JsonArray response =
                new Gson().fromJson(new ExperimentAdminController(subject).doOp(accession, "STATUS"), JsonArray.class).getAsJsonArray();

        return response.get(0).getAsJsonObject().get("result").getAsString();
    }

    @Test
    public void statusReadsLastOpLogEntry() {
        assertThat(readFromStatus(ImmutableList.<OpLogEntry>of()), is(""));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.NULL("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(ImmutableList.of(OpLogEntry.NULL("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))), not(containsString("msg")));
    }

}
