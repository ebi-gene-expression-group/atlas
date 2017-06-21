package uk.ac.ebi.atlas.markergenes;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MarkerGene {
    static MarkerGene create(String geneId, String experimentAccession, int perplexity, int clusterId, double p) {
        return new AutoValue_MarkerGene(geneId, experimentAccession, perplexity, clusterId, p);
    }

    public abstract String geneId();
    public abstract String experimentAccession();
    public abstract int perplexity();
    public abstract int clusterId();
    public abstract double p();
}
