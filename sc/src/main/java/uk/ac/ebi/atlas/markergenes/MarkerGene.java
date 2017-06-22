package uk.ac.ebi.atlas.markergenes;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

@AutoValue
public abstract class MarkerGene {
    public static MarkerGene create(String geneId, String experimentAccession, int perplexity, List<ImmutablePair<Integer, Double>> clusterIds) {
        return new AutoValue_MarkerGene(geneId, experimentAccession, perplexity, ImmutableList.copyOf(clusterIds));
    }

    public abstract String geneId();
    public abstract String experimentAccession();
    public abstract int perplexity();
    public abstract ImmutableList<ImmutablePair<Integer, Double>> clusterIds();
}
