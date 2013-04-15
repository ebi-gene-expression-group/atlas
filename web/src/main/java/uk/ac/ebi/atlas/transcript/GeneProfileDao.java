package uk.ac.ebi.atlas.transcript;

import uk.ac.ebi.atlas.model.baseline.TranscriptProfiles;

public interface GeneProfileDao {

    public TranscriptProfiles getTranscriptProfiles(String geneId, String experimentAccession);
}
