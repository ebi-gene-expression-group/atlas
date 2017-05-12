package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HistogramAcrossGenes {

    private static final Gson gson = new Gson();
    private final int[] histogram;
    private final double [] cutoffs;


    public HistogramAcrossGenes(int[] histogram, double [] cutoffs){
        Preconditions.checkArgument(histogram.length == cutoffs.length +1);

        this.histogram = histogram;
        this.cutoffs = cutoffs;
    }

    public JsonObject asJson(){
        JsonObject result = new JsonObject();

        result.add("bins", gson.toJsonTree(cutoffs).getAsJsonArray());
        result.add("values", gson.toJsonTree(histogram).getAsJsonArray());


        return result;
    }
}
