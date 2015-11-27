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

package uk.ac.ebi.atlas.solr.admin.monitor;

import com.google.common.collect.Iterators;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Named
@Scope("singleton")
public class IndexingProgress implements Iterable<IndexingProgress.ProcessedFile>{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexingProgress.class);

    // This list may be iterated from a different thread than the index build thread, generating a
    // ConcurrentModificationException if we didn't use a thread safe implementation like CopyOnWriteArrayList
    private List<ProcessedFile> processedFiles = new CopyOnWriteArrayList<>();

    private long processedDiskSpace;
    private long totalTimeTaken;

    public IndexingProgress(){
    }

    public void completed(Path filePath, long timeTaken){
        processedDiskSpace += FileUtils.sizeOf(filePath.toFile());
        totalTimeTaken += timeTaken;
        processedFiles.add(new ProcessedFile(filePath, timeTaken));
    }

    int minutesToCompletion(long totalDiskSpace) {
        double estimatedSecondsToCompletion = totalTimeTaken * (((double)(totalDiskSpace - processedDiskSpace)/processedDiskSpace));
        return (int)estimatedSecondsToCompletion/60;
    }

    int progress(long totalDiskSpace) {
        return (int)(processedDiskSpace * 100 / totalDiskSpace);
    }

    @Override
    public Iterator<ProcessedFile> iterator() {
        return Iterators.unmodifiableIterator(processedFiles.iterator());
    }

    public void reset() {
        totalTimeTaken = 0;
        processedDiskSpace = 0;
        processedFiles.clear();
    }

    static class ProcessedFile{
        private final Path filePath;
        private final long seconds;

        ProcessedFile(Path filePath, long seconds){

            this.filePath = filePath;
            this.seconds = seconds;

        }

        public long getSize(){
            try {
                return Files.size(filePath);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new IllegalStateException(e);
            }
        }

        @Override
        public String toString(){
            return filePath + ", size: " + getSize() + ", time taken: " + seconds + " seconds";
        }

    }

}
