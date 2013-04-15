package uk.ac.ebi.atlas.model.baseline;

import java.util.Iterator;
import java.util.List;

public class TranscriptProfiles implements Iterable<TranscriptProfile> {

    private List<TranscriptProfile> transcriptProfiles;

    public TranscriptProfiles(List<TranscriptProfile> transcriptProfiles) {
        this.transcriptProfiles = transcriptProfiles;
    }

    @Override
    public Iterator<TranscriptProfile> iterator() {
        return transcriptProfiles.iterator();
    }
}
