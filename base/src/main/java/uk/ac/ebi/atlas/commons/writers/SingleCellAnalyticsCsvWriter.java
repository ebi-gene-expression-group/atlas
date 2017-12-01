package uk.ac.ebi.atlas.commons.writers;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.SingleCellAnalyticsStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

public class SingleCellAnalyticsCsvWriter implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellAnalyticsCsvWriter.class);

    private final BufferedWriter outWriter;

    public SingleCellAnalyticsCsvWriter(Writer writer) {
        outWriter = new BufferedWriter(writer);
    }

    public void write(SingleCellAnalyticsStream scaStream) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        scaStream.stream().forEach(sca -> writeLine(sca.toCsvLine()));
        close();
        stopwatch.stop();
        LOGGER.info("Finished writing SC analytics in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private void writeLine(String line) {
        try {
            outWriter.write(line);
            outWriter.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        try {
            outWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
