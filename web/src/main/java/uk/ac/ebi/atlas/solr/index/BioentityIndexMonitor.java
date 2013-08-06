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

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("singleton")
public class BioentityIndexMonitor {

    private static final String PROCESSING_STATUS_DESCRIPTION_TEMPLATE = Status.PROCESSING
            + ",\ntotal time elapsed: {0} minutes,\nfile being processed: {1}, time elapsed for current file: {2} seconds,\n files successfully processed:"
            + "\n{3}";

    private static final String IN_PROGRESS_STATUS_DESCRIPTION_TEMPLATE = Status.IN_PROGRESS
            + ",\ntotal time elapsed: {0},\n files successfully processed:"
            + "\n{1}";

    public Status status;

    private Path currentFile;

    private List<ProcessedFile> completedFiles = Lists.newArrayList();

    private Exception failureReason;

    private Stopwatch totalTimeStopwatch = new Stopwatch();

    private Stopwatch currentFileStopwatch = new Stopwatch();

    public BioentityIndexMonitor(){
        status = Status.INITIALIZED;
    }

    void startMonitoring(){
        totalTimeStopwatch.reset();
        totalTimeStopwatch.start();
        completedFiles.clear();
        currentFile = null;

        status = Status.STARTED;
    }

    public synchronized boolean start(){
        if (Status.INITIALIZED == status || Status.COMPLETED == status || Status.FAILED == status){
            startMonitoring();
            return true;
        }
        return false;
    }

    public synchronized void stop() {
        status = Status.COMPLETED;
        totalTimeStopwatch.stop();
    }

    public synchronized void processing(Path filePath) {
        checkState(Status.INITIALIZED == status || Status.STARTED == status || Status.IN_PROGRESS == status, "Illegal status: " + status);
        status = Status.PROCESSING;
        currentFile = filePath;
        currentFileStopwatch.start();
    }

    public synchronized void completed(Path filePath) {
        checkState(Status.PROCESSING == status, "Illegal status: " + status);

        status = Status.IN_PROGRESS;
        completedFiles.add(new ProcessedFile(filePath, currentFileStopwatch.elapsed(TimeUnit.SECONDS)));
        currentFileStopwatch.reset();
    }

    public synchronized void failed(Exception e) {
        checkState(Status.COMPLETED != status, "Illegal status: " + status);

        failureReason = e;
        status = Status.FAILED;
        totalTimeStopwatch.stop();
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        INITIALIZED, STARTED, PROCESSING, IN_PROGRESS, COMPLETED, FAILED;
    }

    public String statusDescription(){
        switch(status){
            case PROCESSING:
                return MessageFormat.format(PROCESSING_STATUS_DESCRIPTION_TEMPLATE,
                        totalTimeStopwatch.elapsed(TimeUnit.MINUTES), currentFile, currentFileStopwatch.elapsed(TimeUnit.SECONDS), StringUtils.join(completedFiles, "\n"));
            case IN_PROGRESS:
                return MessageFormat.format(IN_PROGRESS_STATUS_DESCRIPTION_TEMPLATE,
                        totalTimeStopwatch.elapsed(TimeUnit.MINUTES), StringUtils.join(completedFiles, "\n"));
            case FAILED:
                return status + ", reason:\n" + failureReason.getMessage();
            case COMPLETED:
                return status + ", time taken: " + totalTimeStopwatch.elapsed(TimeUnit.MINUTES)
                              + ",\ncompleted files:\n" + StringUtils.join(completedFiles, "\n");
            default:
                return status.toString();
        }
    }

    static class ProcessedFile{
        private final Path filePath;
        private final long seconds;

        ProcessedFile(Path filePath, long seconds){

            this.filePath = filePath;
            this.seconds = seconds;

        }

        @Override
        public String toString(){
            return filePath + ", time taken: " + seconds + " seconds";
        }

    }
}
