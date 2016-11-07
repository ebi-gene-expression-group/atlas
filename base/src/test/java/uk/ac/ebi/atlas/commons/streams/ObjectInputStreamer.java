package uk.ac.ebi.atlas.commons.streams;

import static org.junit.Assert.assertEquals;

public abstract class ObjectInputStreamer {

    protected abstract ObjectInputStream<?> createStream(String experimentAccession);

    public void testMultithreaded(final String experimentAccession,
                                  final int concurrentThreads,
                                  final int concurrentStreamsPerThread){
        for(int i = 0; i< concurrentThreads; i++){
            new Runnable(){
                @Override
                public void run() {
                    testSinglethreaded(experimentAccession,concurrentStreamsPerThread);
                }
            }.run();
        }
    }

    public void testSinglethreaded(String experimentAccession, int concurrentStreams){
        ObjectInputStream<?>[] streams = new ObjectInputStream[concurrentStreams];
        for(int i = 0; i< concurrentStreams; i++){
            streams[i]= createStream(experimentAccession);
        }
        int[] counts = new int[concurrentStreams];

        int encounteredClosed=0;
        int pos = 0;
        while(encounteredClosed<concurrentStreams){
            pos = encounteredClosed ==0? (int) Math.floor(Math.random()*concurrentStreams) : (pos+1)%concurrentStreams;
            ObjectInputStream<?> s = streams[pos];
            if(s.readNext()!=null){
                counts[pos]=counts[pos]+1;
                encounteredClosed=0;
            } else {
                encounteredClosed++;
            }
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int count: counts){
            max = Math.max(max, count);
            min = Math.min(min,count);
        }

        assertEquals("All streams should have accumulated the same amount of values", max, min);
    }
}