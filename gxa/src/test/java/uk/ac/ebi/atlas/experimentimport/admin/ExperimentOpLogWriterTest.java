package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class ExperimentOpLogWriterTest {
    private static final String DUMMY_ACCESSION = "DUMMY_ACCESSION";

    private ExperimentOpLogWriter subject;

    @Before
    public void setUp() throws Exception {
        Path dir = Files.createTempDirectory("");
        dir.toFile().deleteOnExit();
        Files.createDirectory(dir.resolve("admin"));

        subject = new ExperimentOpLogWriter(new DataFileHub(dir));
    }

    @Test
    public void initiallyGetEmptyLog() {
        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), hasSize(0));
    }

    @Test
    public void writeAndThenReadTheLog() {
        List<OpLogEntry> opLog = ImmutableList.of(OpLogEntry.successfulOp(Op.UPDATE_PUBLIC, 100L, 150L));

        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), hasSize(0));

        subject.persistOpLog(DUMMY_ACCESSION, opLog);

        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), contains(opLog.toArray(new OpLogEntry[0])));
    }

    @Test
    public void writeManyExperimentsAndPersistJustTheTail() {
        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), hasSize(0));
        List<OpLogEntry> opLog = new ArrayList<>();

        int ourMax = ExperimentOpLogWriter.MAX_LENGTH + 10;
        for (int i = 0; i < ourMax; i++) {
            opLog.add(OpLogEntry.successfulOp(Op.UPDATE_PUBLIC, 100L * i, 100L * i + 50));
            if (i % 10 == 0) {
                subject.persistOpLog(DUMMY_ACCESSION, opLog);
            }
        }
        subject.persistOpLog(DUMMY_ACCESSION, opLog);

        List<OpLogEntry> opLogNow = subject.getCurrentOpLog(DUMMY_ACCESSION);

        assertThat(opLogNow, hasSize(ExperimentOpLogWriter.MAX_LENGTH));
        assertThat(opLogNow.get(ExperimentOpLogWriter.MAX_LENGTH - 1), is(opLog.get(ourMax - 1)));
    }
}
