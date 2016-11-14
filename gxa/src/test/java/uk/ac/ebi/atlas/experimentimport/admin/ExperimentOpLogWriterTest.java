package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class ExperimentOpLogWriterTest {

    private static final String DUMMY_ACCESSION = "DUMMY_ACCESSION";

    private ExperimentOpLogWriter subject;

    @Before
    public void setUp() {
        subject = new ExperimentOpLogWriter(new FileTsvReaderBuilder(), new FileTsvWriterBuilder());

        File dir = new File(System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis());
        dir.mkdir();
        dir.deleteOnExit();
        subject.opLogTemplate = dir.getAbsolutePath() + "/{0}-temp.tsv";
    }

    @Test
    public void initiallyGetEmptyLog() {
        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), hasSize(0));
    }

    @Test
    public void writeAndThenReadTheLog() {
        List<Pair<String, Pair<Long, Long>>> opLog = ImmutableList.of(Pair.of("update", Pair.of(100L, 150L)));

        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), hasSize(0));

        subject.persistOpLog(DUMMY_ACCESSION, opLog);

        assertThat(subject.getCurrentOpLog(DUMMY_ACCESSION), is(opLog));
    }

    @Test
    public void writeManyExperimentsAndPersistJustTheTail() {
        List<Pair<String, Pair<Long, Long>>> opLog = new ArrayList<>();

        int ourMax = ExperimentOpLogWriter.MAX_LENGTH + 10;
        for (int i = 0; i < ourMax; i++) {
            opLog.add(Pair.of("update", Pair.of(100L * i, 100L * i + 50)));

            if (i % 10 == 0) {
                subject.persistOpLog(DUMMY_ACCESSION, opLog);
            }
        }
        subject.persistOpLog(DUMMY_ACCESSION, opLog);

        List<Pair<String, Pair<Long, Long>>> opLogNow = subject.getCurrentOpLog(DUMMY_ACCESSION);

        assertThat(opLogNow, hasSize(ExperimentOpLogWriter.MAX_LENGTH));

        assertThat(opLogNow.get(ExperimentOpLogWriter.MAX_LENGTH - 1), is(opLog.get(ourMax - 1)));
    }
}
