package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import java.util.Arrays;

public final class RnaSeqBaselineTranscript {
    private final String geneId;
    private final String transcriptId;
    private final Double[] levels;

    public static RnaSeqBaselineTranscript create(String geneId, String transcriptId, Double[] levels) {
        return new RnaSeqBaselineTranscript(geneId, transcriptId, levels.clone());
    }

    RnaSeqBaselineTranscript(
            String geneId,
            String transcriptId,
            Double[] levels) {
        if (geneId == null) {
            throw new NullPointerException("Null geneId");
        }
        this.geneId = geneId;
        if (transcriptId == null) {
            throw new NullPointerException("Null transcriptId");
        }
        this.transcriptId = transcriptId;
        if (levels == null) {
            throw new NullPointerException("Null levels");
        }
        this.levels = levels;
    }

    public String geneId() {
        return geneId;
    }

    public String transcriptId() {
        return transcriptId;
    }

    public Double[] levels() {
        return levels.clone();
    }

    @Override
    public String toString() {
        return "RnaSeqBaselineTranscript{"
                + "geneId=" + geneId
                + ", transcriptId=" + transcriptId
                + ", levels=" + Arrays.toString(levels)
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof RnaSeqBaselineTranscript) {
            RnaSeqBaselineTranscript that = (RnaSeqBaselineTranscript) o;
            return (this.geneId.equals(that.geneId()))
                    && (this.transcriptId.equals(that.transcriptId()))
                    && (Arrays.equals(this.levels, that.levels));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= geneId.hashCode();
        h *= 1000003;
        h ^= transcriptId.hashCode();
        h *= 1000003;
        h ^= Arrays.hashCode(levels);
        return h;
    }

}

