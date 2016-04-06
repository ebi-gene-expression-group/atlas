package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableSet;
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

import java.util.*;

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

        for (ExperimentOps.Op op : ExperimentOps.Op.values()) {
            List<String> accessions = new ArrayList<>();
            for (int i = -1; i < rand.nextInt(5); i++) {
                accessions.add("E-DUMMY-" + rand.nextInt(10000));
            }

            Iterator<JsonElement> result = experimentOps.perform(accessions, op).iterator();

            int i = 0;
            while (result.hasNext()) {
                JsonObject o = result.next().getAsJsonObject();
                Assert.assertEquals(accessions.get(i), o.get("accession").getAsString());
                Assert.assertNotNull(o.get("result"));
                Assert.assertNull(o.get("error"));
                i++;
            }
        }
    }

    @Test
    public void statefulOpsModifyTheOpLog() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (ExperimentOps.Op op : ExperimentOps.Op.values()) {
            if (!op.equals(ExperimentOps.Op.CLEAR_LOG)) {
                experimentOps.perform(Arrays.asList(accession), op);
            }
        }
        Assert.assertEquals(
                ExperimentOps.Op.values().length
                        - Arrays.asList(ExperimentOps.Op.LIST, ExperimentOps.Op.LOG, ExperimentOps.Op.STATUS,
                        ExperimentOps.Op.CLEAR_LOG).size()
                , fileSystem.get(accession).size());
    }

    @Test
    public void timestampsLookRight() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        for (ExperimentOps.Op op : ExperimentOps.Op.values()) {
            experimentOps.perform(Arrays.asList(accession), op);
        }
        for (Pair<String, Pair<Long, Long>> p : fileSystem.get(accession)) {
            Long start = p.getRight().getLeft();
            Long finish = p.getRight().getRight();
            Assert.assertTrue("Starting time before finish", finish - start >= 0);
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, start);
            Assert.assertNotEquals(ExperimentOps.UNFINISHED, finish);
        }
    }

    @Test
    public void errorLeavesLogDirty() {
        String accession = "E-DUMMY-" + new Random().nextInt(10000);
        Mockito.when(experimentCRUD.deleteExperiment(accession)).thenThrow(new RuntimeException("Woosh!"));

        JsonObject result = experimentOps.perform(Arrays.asList(accession), ExperimentOps.Op.DELETE)
                .iterator().next().getAsJsonObject();

        Assert.assertEquals(accession, result.get("accession").getAsString());
        Assert.assertNull(result.get("result"));
        Assert.assertNotNull(result.get("error"));

        Assert.assertEquals(ExperimentOps.UNFINISHED, fileSystem.get(accession).iterator().next().getRight().getRight());

    }
}
