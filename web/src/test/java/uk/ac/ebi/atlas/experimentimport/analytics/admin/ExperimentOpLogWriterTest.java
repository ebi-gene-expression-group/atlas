package uk.ac.ebi.atlas.experimentimport.analytics.admin;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.experimentimport.analytics.admin.ExperimentOpLogWriter;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperimentOpLogWriterTest {

    ExperimentOpLogWriter experimentOpLogWriter;

    String accession = "DUMMY_ACCESSION";

    @Before
    public void setUp() {
        experimentOpLogWriter = new ExperimentOpLogWriter(new FileTsvReaderBuilder(), new FileTsvWriterBuilder());

        File dir = new File(System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis());
        dir.mkdir();
        dir.deleteOnExit();
        experimentOpLogWriter.opLogTemplate = dir.getAbsolutePath() + "/{0}-temp.tsv";
    }

    @Test
    public void initiallyGetEmptyLog() {
        Assert.assertEquals("Initially get empty op log", Arrays.asList(),
                experimentOpLogWriter.getCurrentOpLog(accession));
    }

    @Test
    public void writeAndThenReadTheLog() {
        List<Pair<String, Pair<Long, Long>>> opLog = Arrays.asList(Pair.of("update", Pair.of(100L, 150L)));

        Assert.assertEquals("Initially get empty op log", Arrays.asList(),
                experimentOpLogWriter.getCurrentOpLog(accession));

        experimentOpLogWriter.persistOpLog(accession, opLog);

        Assert.assertEquals("Get op log back", opLog,
                experimentOpLogWriter.getCurrentOpLog(accession));
    }

    @Test
    public void writeManyExperimentsAndPersistJustTheTail() {
        List<Pair<String, Pair<Long, Long>>> opLog = new ArrayList<>();

        int ourMax = ExperimentOpLogWriter.MAX_LENGTH + 10;
        for (int i = 0; i < ourMax; i++) {
            opLog.add(Pair.of("update", Pair.of(100L * i, 100L * i + 50)));

            if (i % 10 == 0) {
                experimentOpLogWriter.persistOpLog(accession, opLog);
            }
        }
        experimentOpLogWriter.persistOpLog(accession, opLog);

        List<Pair<String, Pair<Long, Long>>> opLogNow = experimentOpLogWriter.getCurrentOpLog(accession);

        Assert.assertEquals(ExperimentOpLogWriter.MAX_LENGTH, opLogNow.size());

        Assert.assertEquals(opLog.get(ourMax - 1), opLogNow.get(ExperimentOpLogWriter.MAX_LENGTH - 1));


    }
}
