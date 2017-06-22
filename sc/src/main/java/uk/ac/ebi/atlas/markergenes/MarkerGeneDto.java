package uk.ac.ebi.atlas.markergenes;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.tuple.Pair;

@AutoValue
public abstract class MarkerGeneDto {
    public static MarkerGeneDto create(String geneId, String experimentAccession, int perplexity, int clusterId, double p) {
        return new AutoValue_MarkerGeneDto(geneId, experimentAccession, perplexity, clusterId, p);
    }

    public static MarkerGeneDto create(String geneId, String experimentAccession,
                                       Pair<Integer, Integer> perplexityAndClusterId, double p) {
        return new AutoValue_MarkerGeneDto(geneId, experimentAccession,
                perplexityAndClusterId.getLeft(), perplexityAndClusterId.getRight(), p);
    }

    public abstract String geneId();
    public abstract String experimentAccession();
    public abstract int perplexity();
    public abstract int clusterId();
    public abstract double p();
}
