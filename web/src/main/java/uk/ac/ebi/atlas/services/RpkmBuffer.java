package uk.ac.ebi.atlas.services;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class RpkmBuffer {

    private String transcriptId;
    private Queue<String> rpkmValues = new LinkedList<>();

    public RpkmBuffer() {
    }

    public String poll() {
        return rpkmValues.poll();
    }


    public String peek() {
        return rpkmValues.peek();
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public RpkmBuffer reload(String... rpkmValues) {
        Collections.addAll(this.rpkmValues, rpkmValues);
        transcriptId = this.rpkmValues.poll();
        return this;
    }
}
