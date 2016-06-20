package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.experimentimport.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ExperimentOpsTest {

    ExperimentOps experimentOps;

    @Mock
    ExperimentCRUD experimentCRUD;
    @Mock
    ExperimentMetadataCRUD experimentMetadataCRUD;
    @Mock
    ExperimentOpLogWriter experimentOpLogWriter;
    @Mock
    BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader;
    @Mock
    AnalyticsIndexerManager analyticsIndexerManager;

    final Map<String, List<Pair<String, Pair<Long, Long>>>> fileSystem = new HashMap<>();

    String accession = "E-EXAMPLE-1";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        experimentOps = new ExperimentOps(experimentCRUD, experimentMetadataCRUD, experimentOpLogWriter,
                baselineCoexpressionProfileLoader, analyticsIndexerManager);


        Mockito.doAnswer(new Answer<ExperimentDTO>() {
            @Override
            public ExperimentDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String) invocationOnMock.getArguments()[0];

                ExperimentType experimentType = ExperimentType.values()
                        [new Random().nextInt(ExperimentType.values().length)];

                return new ExperimentDTO(accession, experimentType,
                        ImmutableSet.of("Homo sapiens"), new HashSet<String>(),
                        "title", new Date(), false, UUID.randomUUID().toString());
            }
        }).when(experimentMetadataCRUD).findExperiment(Matchers.anyString());


        Mockito.doAnswer(new Answer<List<Pair<String, Pair<Long, Long>>>>() {
            @Override
            public List<Pair<String, Pair<Long, Long>>> answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String) invocationOnMock.getArguments()[0];
                if (fileSystem.containsKey(accession)) {
                    return fileSystem.get(accession);
                } else {
                    return new ArrayList<>();
                }
            }
        }).when(experimentOpLogWriter).getCurrentOpLog(Matchers.anyString());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                String accession = (String)
                        invocationOnMock.getArguments()[0];
                List<Pair<String, Pair<Long, Long>>> opLog = (List<Pair<String, Pair<Long, Long>>>)
                        invocationOnMock.getArguments()[1];
                fileSystem.put(accession, opLog);
                return null;
            }
        }).when(experimentOpLogWriter).persistOpLog(Matchers.anyString(), Matchers.any(List.class));

    }

    @Test
    public void allOpsReturnTheSameKindOfJson() {
        Random rand = new Random();

        for (Op op : Op.values()) {
            List<String> accessions = new ArrayList<>();
            for (int i = -1; i < rand.nextInt(5); i++) {
                accessions.add("E-DUMMY-" + rand.nextInt(10000));
            }

            Iterator<JsonElement> result = experimentOps.perform(Optional.of(accessions), Collections.singleton(op))
                    .iterator();

            int i = 0;
            while (result.hasNext()) {
                JsonObject o = result.next().getAsJsonObject();
                assertEquals(accessions.get(i), o.get("accession").getAsString());
                Assert.assertNotNull(o.get("result"));
                Assert.assertNull(o.get("error"));
                i++;
            }
        }
    }
    
    @Test
    public void aggregateOpsInANeatFashion() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        Mockito.when(experimentCRUD.deleteExperiment(accession)).thenThrow(new RuntimeException("Woosh!"));
        List<Op> ops= new ArrayList<>();
        ops.add(Op.UPDATE_DESIGN); // says "success!"
        ops.add(Op.CLEAR_LOG); // says "success!"
        ops.add(Op.LIST); // says something else
        ops.add(Op.DELETE); //throws, should give an error
        ops.add(Op.COEXPRESSION_DELETE); // should not be started
        ops.add(Op.COEXPRESSION_IMPORT); // should not be started


        JsonArray result = experimentOps.perform(Optional.of(Collections.singletonList(accession)),ops);

        assertTrue(result.toString(), result.get(0).getAsJsonObject().has("accession"));
        assertThat(result.get(0).getAsJsonObject().get("accession").getAsJsonPrimitive()
                .getAsString(), is(accession));
        JsonArray successes = result.get(0).getAsJsonObject().get("result").getAsJsonArray();
        JsonArray failures = result.get(0).getAsJsonObject().get("error").getAsJsonArray();

        assertThat(successes.toString(), successes.size(), is(new String[]{"success!", "<sth else>"}.length));
        assertThat(failures.toString(),failures.size(), is(new String[]{"<error msg>", "not started"}.length));

        Set<Map.Entry<String, JsonElement>> firstSuccess = successes.get(0).getAsJsonObject().entrySet();
        assertThat(firstSuccess.size(), is(1));
        String opNameOfFirstSuccessEntry = firstSuccess.iterator().next().getKey();
        assertThat(opNameOfFirstSuccessEntry, containsString(Op.UPDATE_DESIGN.name()));
        assertThat(opNameOfFirstSuccessEntry, containsString(Op.CLEAR_LOG.name()));

        Set<Map.Entry<String, JsonElement>> secondSuccess = successes.get(1).getAsJsonObject().entrySet();
        assertThat(secondSuccess.size(), is(1));
        assertEquals(Op.LIST.name(), secondSuccess.iterator().next().getKey());

        Set<Map.Entry<String, JsonElement>> firstFailure = failures.get(0).getAsJsonObject().entrySet();
        assertThat(firstFailure.size(), is(1));
        assertEquals(Op.DELETE.name(), firstFailure.iterator().next().getKey());


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
                experimentOps.perform(Optional.of(Collections.singletonList(accession)), Collections.singletonList
                        (op));
            }
        }
        assertEquals(
                Op.values().length
                        - Arrays.asList(Op.LIST, Op.LOG, Op.STATUS,
                        Op.CLEAR_LOG).size()
                , fileSystem.get(accession).size());
    }

    @Test
    public void timestampsLookRight() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (Op op : Op.values()) {
            experimentOps.perform(Optional.of(Collections.singletonList(accession)), Collections.singletonList(op));
        }
        for (Pair<String, Pair<Long, Long>> p : fileSystem.get(accession)) {
            Long start = p.getRight().getLeft();
            Long finish = p.getRight().getRight();
            assertTrue("Starting time before finish", finish - start >= 0);
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, start);
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, finish);
        }
    }

    @Test
    public void errorLeavesLogDirty() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        Mockito.when(experimentCRUD.deleteExperiment(accession)).thenThrow(new RuntimeException("Woosh!"));

        JsonObject result = experimentOps.perform(Optional.of(Collections.singletonList(accession)), Collections.singleton(Op
                .DELETE))
                .iterator().next().getAsJsonObject();

        assertEquals(accession, result.get("accession").getAsString());
        Assert.assertNull(result.get("result"));
        Assert.assertNotNull(result.get("error"));

        assertTrue("Log failure in op name", Pattern.matches("^FAILED.*",
                fileSystem.get(accession).iterator().next().getLeft()));

    }

    @Test
    public void loadingExperimentsWorksAsExpectedWhenEverythingIsGood() throws Exception {
        //if not you should update the tests
        assertEquals(Op.opsForParameter("LOAD_PUBLIC"), ImmutableList.of(Op.IMPORT_PUBLIC,Op.COEXPRESSION_UPDATE,
                Op.SERIALIZE,Op.ANALYTICS_IMPORT) );

        new ExperimentAdminController(experimentOps,experimentMetadataCRUD).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCRUD).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCRUD).serializeExpressionData(accession);
        verify(analyticsIndexerManager).addToAnalyticsIndex(accession);

        verifyNoMoreInteractions(experimentCRUD,experimentMetadataCRUD,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled1() throws Exception {
        Mockito.doThrow(new RuntimeException("The files are bad!"))
                .when(experimentCRUD)
                .importExperiment(accession,false);

        new ExperimentAdminController(experimentOps,experimentMetadataCRUD).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCRUD).importExperiment(accession, false);

        verifyNoMoreInteractions(experimentCRUD,experimentMetadataCRUD,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled2() throws Exception {
        Mockito.doThrow(new RuntimeException("Database down, or something"))
                .when(baselineCoexpressionProfileLoader)
                .loadBaselineCoexpressionsProfile(accession);

        new ExperimentAdminController(experimentOps,experimentMetadataCRUD).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCRUD).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);

        verifyNoMoreInteractions(experimentCRUD,experimentMetadataCRUD,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }

    @Test
    public void loadingExperimentsCanFailAndThenTheRestOfMethodsIsNotCalled3() throws Exception {
        Mockito.doThrow(new RuntimeException("Serializing failed"))
                .when(experimentCRUD)
                .serializeExpressionData(accession);

        new ExperimentAdminController(experimentOps,experimentMetadataCRUD).doOp(accession, "LOAD_PUBLIC");

        verify(experimentCRUD).importExperiment(accession, false);
        verify(baselineCoexpressionProfileLoader).deleteCoexpressionsProfile(accession);
        verify(baselineCoexpressionProfileLoader).loadBaselineCoexpressionsProfile(accession);
        verify(experimentCRUD).serializeExpressionData(accession);

        verifyNoMoreInteractions(experimentCRUD,experimentMetadataCRUD,analyticsIndexerManager,baselineCoexpressionProfileLoader);
    }
}
