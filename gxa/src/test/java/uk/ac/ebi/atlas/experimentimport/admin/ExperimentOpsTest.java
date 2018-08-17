package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
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
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentOpsTest {
    private static final String ACCESSION = "E-EXAMPLE-1";

    @Mock
    private ExperimentCrud experimentCrudMock;

    @Mock
    private BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;

    @Mock
    private AnalyticsIndexerManager analyticsIndexerManager;

    @Mock
    private ExperimentTrader experimentTrader;

    @Mock
    private Experiment experimentMock;

    @Mock
    private ExperimentAttributesService experimentAttributesService;

    private ExperimentOps subject;

    private ExperimentOpLogWriter experimentOpLogWriter;

    @Before
    public void setUp() {
        experimentOpLogWriter = new ExperimentOpLogWriter(MockDataFileHub.create());

        subject =
                new ExperimentOps(
                        experimentOpLogWriter,
                        new ExpressionAtlasExperimentOpsExecutionService(
                                experimentCrudMock,
                                baselineCoexpressionProfileLoader,
                                analyticsIndexerManager,
                                experimentTrader,
                                experimentAttributesService));

        when(experimentAttributesService.getAttributes(any())).thenReturn(new HashMap<>());
        when(experimentTrader.getExperiment(anyString(), anyString())).thenReturn(experimentMock);

        Mockito.doAnswer(invocationOnMock -> {
            String accession1 = (String) invocationOnMock.getArguments()[0];

            ExperimentType experimentType = ExperimentType.values()
                    [new Random().nextInt(ExperimentType.values().length)];

            return mockDTO(accession1, experimentType);
        }).when(experimentCrudMock).findExperiment(anyString());

    }

    @Test
    public void allOpsReturnTheSameKindOfJson() {
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
    public void aggregateOpsInANeatFashion() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        doThrow(new RuntimeException("Woosh!")).when(experimentCrudMock).deleteExperiment(accession);
        List<Op> ops = new ArrayList<>();
        ops.add(Op.UPDATE_DESIGN); // says "success!"
        ops.add(Op.CLEAR_LOG); // says "success!"
        ops.add(Op.LIST); // says something else
        ops.add(Op.DELETE); //throws, should give an error
        ops.add(Op.COEXPRESSION_DELETE); // should not be started
        ops.add(Op.COEXPRESSION_IMPORT); // should not be started

        JsonObject result =
                subject.dispatchAndPerform(singletonList(accession), ops)
                        .iterator()
                        .next()
                        .getAsJsonObject();

        assertThat(result.get("accession").getAsJsonPrimitive().getAsString(), is(accession));
        JsonArray successes = result.get("result").getAsJsonArray();
        JsonArray failures = result.get("error").getAsJsonArray();

        assertThat(successes.toString(), successes.size(), is(new String[]{"success!", "<sth else>"}.length));
        assertThat(failures.toString(), failures.size(), is(new String[]{"<error msg>", "not started"}.length));

        Set<Map.Entry<String, JsonElement>> firstSuccess = successes.get(0).getAsJsonObject().entrySet();
        assertThat(firstSuccess.size(), is(1));
        String opNameOfFirstSuccessEntry = firstSuccess.iterator().next().getKey();
        assertThat(opNameOfFirstSuccessEntry, containsString(Op.UPDATE_DESIGN.name()));
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
                subject.dispatchAndPerform(singletonList(accession), singletonList(op));
            }
        }

        String logResult =
                ImmutableList.copyOf(
                        subject.dispatchAndPerform(singletonList(accession), singletonList(Op.LOG))).toString();

        for (Op op: Op.values()) {
            boolean isStateful =
                    !Arrays.asList(Op.LIST, Op.LOG, Op.STATUS, Op.CLEAR_LOG, Op.CACHE_READ, Op.CACHE_REMOVE, Op.CHECK)
                            .contains(op);
            assertThat(op.toString(), logResult.contains(op.name()), is(isStateful));
        }
    }

    @Test
    public void errorLeavesLogDirty() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        doThrow(new RuntimeException("Woosh!")).when(experimentCrudMock).deleteExperiment(accession);

        JsonObject result =
                subject.dispatchAndPerform(
                        singletonList(accession),
                        Collections.singleton(Op.DELETE)).iterator().next().getAsJsonObject();

        assertThat(accession, is(result.get("accession").getAsString()));
        assertThat(result.get("result"), is(nullValue()));
        assertThat(result.get("error"), is(not(nullValue())));

        assertThat(ImmutableList.copyOf(
                subject.dispatchAndPerform(singletonList(accession), singletonList(Op.LOG))).toString(),
                containsString("error"));
    }

    @Test
    public void loadingExperimentsWorksAsExpectedWhenEverythingIsGood() throws Exception {
        //if not you should update the tests
        assertThat(
                Op.opsForParameter("LOAD_PUBLIC"),
                contains(Op.IMPORT_PUBLIC, Op.COEXPRESSION_UPDATE, Op.ANALYTICS_IMPORT));

        new ExperimentAdminController(subject).doOp(ACCESSION, "LOAD_PUBLIC", new MockHttpServletResponse());

        verify(experimentCrudMock).importExperiment(ACCESSION, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(ACCESSION);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(ACCESSION);
        verify(analyticsIndexerManager).addToAnalyticsIndex(ACCESSION);

        verifyNoMoreInteractions(
                experimentCrudMock, experimentCrudMock, analyticsIndexerManager, baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled1() throws Exception {
        doThrow(new IOException("The files are bad!"))
                .when(experimentCrudMock)
                .importExperiment(ACCESSION, false);

        new ExperimentAdminController(subject).doOp(ACCESSION, "LOAD_PUBLIC", new MockHttpServletResponse());

        verify(experimentCrudMock).importExperiment(ACCESSION, false);

        verifyNoMoreInteractions(
                experimentCrudMock, experimentCrudMock, analyticsIndexerManager, baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled2() throws IOException {
        doThrow(new UncheckedIOException(new IOException("Database down, or something")))
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(ACCESSION);

        new ExperimentAdminController(subject).doOp(ACCESSION, "LOAD_PUBLIC", new MockHttpServletResponse());

        verify(experimentCrudMock).importExperiment(ACCESSION, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(ACCESSION);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(ACCESSION);

        verifyNoMoreInteractions(
                experimentCrudMock, experimentCrudMock, analyticsIndexerManager, baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled3() throws IOException {
        doThrow(new UncheckedIOException(new IOException("Coexpression load failed")))
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(anyString());

        MockHttpServletResponse responseObject = new MockHttpServletResponse();
        new ExperimentAdminController(subject).doOp(ACCESSION, "LOAD_PUBLIC", responseObject);

        verify(experimentCrudMock).importExperiment(ACCESSION, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(ACCESSION);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(ACCESSION);

        verifyNoMoreInteractions(
                experimentCrudMock, experimentCrudMock, analyticsIndexerManager, baselineCoexpressionProfileLoader);

        String response = responseObject.getContentAsString();
        assertThat(response, containsString("Coexpression load failed"));
        assertThat(response, containsString("error"));
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled4() throws IOException {
        doThrow(new NullPointerException())
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(anyString());

        MockHttpServletResponse responseObject = new MockHttpServletResponse();
        new ExperimentAdminController(subject).doOp(ACCESSION, "LOAD_PUBLIC", responseObject);

        String response = responseObject.getContentAsString();
        assertThat(response, containsString("error"));
    }

    private String readFromStatus(List<OpLogEntry> persisted) throws IOException {
        String accession = "ACCESSION-statusReadsOpLog";
        experimentOpLogWriter.persistOpLog(accession, persisted);

        MockHttpServletResponse responseObject = new MockHttpServletResponse();
        new ExperimentAdminController(subject).doOp(accession, "STATUS", responseObject);

        JsonArray response = GSON.fromJson(responseObject.getContentAsString(), JsonArray.class).getAsJsonArray();

        return response.get(0).getAsJsonObject().get("result").getAsString();
    }

    @Test
    public void statusReadsLastOpLogEntry() throws IOException {
        assertThat(readFromStatus(ImmutableList.of()), is(""));
        assertThat(readFromStatus(
                ImmutableList.of(OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))),
                containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(
                ImmutableList.of(OpLogEntry.nullOp("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))),
                containsString("ANALYTICS_IMPORT"));
        assertThat(readFromStatus(
                ImmutableList.of(OpLogEntry.nullOp("msg"), OpLogEntry.newlyStartedOp(Op.ANALYTICS_IMPORT))),
                not(containsString("msg")));
    }

    private static ExperimentDTO mockDTO(String accession, ExperimentType experimentType) {
        return new ExperimentDTO(
                accession,
                experimentType,
                "Homo sapiens",
                new HashSet<>(),
                new HashSet<>(),
                "title",
                new Date(),
                false,
                UUID.randomUUID().toString());
    }
}
