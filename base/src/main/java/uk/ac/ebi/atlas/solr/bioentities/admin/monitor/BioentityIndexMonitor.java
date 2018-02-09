package uk.ac.ebi.atlas.solr.bioentities.admin.monitor;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.resource.BioentityPropertyFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

/**
 * BioentityIndexMonitor can be polled by clients to get a progress report, useful for synchronous request protocols
 * like HTTP REST, or can be also "observed" by Observer clients that want to be notified of indexing completion or
 * failure (for example useful in tests that need to wait for completion before doing any assertion)
 *
 * A more elaborate but maybe better distributed design would be to have BioentityIndexMonitor only used for polling
 * progress report and BioentityIndex be the Observable that can be listened for processing and completion events;
 * Pros would be:
 * - Monitor would not be Observable anymore
 * - BioentityIndex would not depend on Monitor BioentityIndexMonitor, making it usable and testable independently of
 *   BioentityIndexMonitor stopwatch state
 * - BioentityIndexMonitor would be updating progress state without the need for synchronous messages, by being an
 *   Observer of BioentityIndex
 *
 * Cons would be:
 * - BioentityIndex would have to be Observable not only for completion but also for file processing events
 * - BioentityIndexMonitor would have to be an Observer and implement its own update
 * - Similar responsibility (progress report and progress events) would be executed by separate classes
 */
@Named
public class BioentityIndexMonitor {

    @Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertiesDirectory;

    private static final String PROCESSING_STATUS_DESCRIPTION_TEMPLATE = Status.PROCESSING
            + ",\n" +
            "total time elapsed: {0} minutes,\n" +
            "estimated progress: {1}%,\n" +
            "estimated minutes to completion: {2},\n" +
            "file being processed: {3}\n" +
            "time elapsed for current file: {4} seconds,\n" +
            "files successfully processed:\n" +
            "{5}\n";

    public Status status;

    private BioentityPropertyFile currentFile;
    private IndexingProgress indexingProgress;
    private Exception failureReason;
    private Stopwatch totalTimeStopwatch;
    private Stopwatch currentFileStopwatch;

    @Inject
    public BioentityIndexMonitor(IndexingProgress indexingProgress){
        status = Status.INITIALIZED;
        this.indexingProgress = indexingProgress;
    }

    public synchronized boolean start(){
        if (Status.INITIALIZED == status || Status.COMPLETED == status || Status.FAILED == status) {
            totalTimeStopwatch = Stopwatch.createStarted();
            indexingProgress.reset();
            currentFile = null;
            status = Status.STARTED;
            return true;
        }
        return false;
    }

    public synchronized void stop() {
        status = Status.COMPLETED;
        totalTimeStopwatch.stop();
    }

    public synchronized void processing(BioentityPropertyFile file) {
        checkState(
                Status.INITIALIZED == status || Status.STARTED == status || Status.IN_PROGRESS == status,
                "Illegal status: " + status);
        status = Status.PROCESSING;
        currentFile = file;
        currentFileStopwatch = Stopwatch.createStarted();
    }

    public synchronized void completed() {
        checkState(Status.PROCESSING == status, "Illegal status: " + status);

        status = Status.IN_PROGRESS;
        currentFileStopwatch.stop();
        indexingProgress.completed(currentFile, currentFileStopwatch.elapsed(TimeUnit.SECONDS));
    }

    public synchronized void failed(Exception e) {
        checkState(Status.COMPLETED != status, "Illegal status: " + status);

        failureReason = e;
        status = Status.FAILED;
        if (totalTimeStopwatch.isRunning()){
            totalTimeStopwatch.stop();
        }
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        INITIALIZED, STARTED, PROCESSING, IN_PROGRESS, COMPLETED, FAILED
    }

    public String reportProgress(){
        long totalDiskSpace = FileUtils.sizeOf(Paths.get(bioentityPropertiesDirectory).toFile());

        switch(status){
            case PROCESSING:
            case IN_PROGRESS:
                return MessageFormat.format(PROCESSING_STATUS_DESCRIPTION_TEMPLATE,
                        totalTimeStopwatch.elapsed(TimeUnit.MINUTES),
                        indexingProgress.progress(totalDiskSpace),
                        indexingProgress.minutesToCompletion(totalDiskSpace),
                        currentFile,
                        currentFileStopwatch.elapsed(TimeUnit.SECONDS),
                        Joiner.on("\n").join(indexingProgress));
            case FAILED:
                return status + ", reason:\n" + failureReason.getMessage()+"\n";
            case COMPLETED:
                return status + ", time taken: " + totalTimeStopwatch.elapsed(TimeUnit.MINUTES)
                              + ",\ncompleted files:\n"
                              + Joiner.on("\n").join(indexingProgress)+"\n";
            default:
                return status.toString();
        }
    }


}
