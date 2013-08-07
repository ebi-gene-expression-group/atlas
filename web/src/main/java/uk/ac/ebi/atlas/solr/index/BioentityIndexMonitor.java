/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.solr.index;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

//Monitor can be checked in poll way, for example from synchronous channels like http,
//or can be also "observed" by client that implements the Observer interface
//(these clients will be notified of completion or failure)
@Named
@Scope("singleton")
public class BioentityIndexMonitor extends Observable {

    @Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertiesDirectory;

    private static final String PROCESSING_STATUS_DESCRIPTION_TEMPLATE = Status.PROCESSING
            + ",\ntotal time elapsed: {0} minutes,\nestimated progress: {1}%,\nestimated minutes to completion: {2},\nfile being processed: {3}"
            + ",\ntime elapsed for current file: {4} seconds,\nfiles successfully processed:\n{5}\n";

    private static final String IN_PROGRESS_STATUS_DESCRIPTION_TEMPLATE = Status.IN_PROGRESS
            + ",\ntotal time elapsed: {0} minutes,\nestimated progress: {1}%,\nestimated minutes to completion: {2},\nfiles successfully processed:\n{3}\n";

    public Status status;

    private Path currentFile;

    private IndexingProgress indexingProgress;

    private Exception failureReason;

    private Stopwatch totalTimeStopwatch = new Stopwatch();

    private Stopwatch currentFileStopwatch = new Stopwatch();

    @Inject
    public BioentityIndexMonitor(IndexingProgress indexingProgress){
        status = Status.INITIALIZED;
        this.indexingProgress = indexingProgress;
    }

    public synchronized boolean start(){
        if (Status.INITIALIZED == status || Status.COMPLETED == status || Status.FAILED == status){
            totalTimeStopwatch.reset().start();
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
        notifyStatus();
    }

    public synchronized void processing(Path filePath) {
        checkState(Status.INITIALIZED == status || Status.STARTED == status || Status.IN_PROGRESS == status, "Illegal status: " + status);
        status = Status.PROCESSING;
        currentFile = filePath;
        currentFileStopwatch.reset().start();
    }

    public synchronized void completed(Path filePath) {
        checkState(Status.PROCESSING == status, "Illegal status: " + status);

        status = Status.IN_PROGRESS;
        currentFileStopwatch.stop();
        indexingProgress.completed(filePath, currentFileStopwatch.elapsed(TimeUnit.SECONDS));
    }

    public synchronized void failed(Exception e) {
        checkState(Status.COMPLETED != status, "Illegal status: " + status);

        failureReason = e;
        status = Status.FAILED;
        totalTimeStopwatch.stop();
        notifyStatus();
    }

    private void notifyStatus(){
        setChanged();
        notifyObservers(status);
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        INITIALIZED, STARTED, PROCESSING, IN_PROGRESS, COMPLETED, FAILED;
    }

    public String statusDescription(){
        long totalDiskSpace = FileUtils.sizeOf(Paths.get(bioentityPropertiesDirectory.toString()).toFile());

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
