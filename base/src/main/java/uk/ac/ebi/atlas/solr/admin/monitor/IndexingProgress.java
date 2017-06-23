
package uk.ac.ebi.atlas.solr.admin.monitor;

import com.google.common.collect.Iterators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.resource.BioentityPropertyFile;

import javax.inject.Named;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Named
@Scope("singleton")
public class IndexingProgress implements Iterable<IndexingProgress.ProcessedFile>{

    // This list may be iterated from a different thread than the index build thread, generating a
    // ConcurrentModificationException if we didn't use a thread safe implementation like CopyOnWriteArrayList
    private List<ProcessedFile> processedFiles = new CopyOnWriteArrayList<>();

    private long processedDiskSpace;
    private long totalTimeTaken;

    public IndexingProgress(){
    }

    public void completed(BioentityPropertyFile filePath, long timeTaken){
        processedDiskSpace += filePath.size();
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
        private final BioentityPropertyFile filePath;
        private final long seconds;

        ProcessedFile(BioentityPropertyFile filePath, long seconds){

            this.filePath = filePath;
            this.seconds = seconds;

        }

        public long getSize(){
            return filePath.size();
        }

        @Override
        public String toString(){
            return filePath + ", size: " + getSize() + ", time taken: " + seconds + " seconds";
        }

    }

}
