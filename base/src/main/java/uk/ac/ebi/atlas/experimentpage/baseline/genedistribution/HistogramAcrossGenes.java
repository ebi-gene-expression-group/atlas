package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class HistogramAcrossGenes {
    private final int[] histogram;
    private final double[] cutoffs;

    public HistogramAcrossGenes(int[] histogram, double[] cutoffs) {
        Preconditions.checkArgument(histogram.length == cutoffs.length);

        this.histogram = histogram;
        this.cutoffs = cutoffs;
    }

    public JsonObject asJson() {
        JsonObject result = new JsonObject();

        result.add("bins", GSON.toJsonTree(cutoffs).getAsJsonArray());
        result.add("counts", GSON.toJsonTree(histogram).getAsJsonArray());

        return result;
    }
}
