package uk.ac.ebi.atlas.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

public class GsonProvider {
    protected GsonProvider() {
        throw new UnsupportedOperationException();
    }

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(
                    SemanticQueryTerm.create("").getClass(),
                    SemanticQueryTerm.getGsonTypeAdapter())
            .registerTypeAdapter(
                    TSnePoint.create(0.0, 0.0, "").getClass(),
                    TSnePoint.getGsonTypeAdapter())
            .serializeSpecialFloatingPointValues()
            .create();
}
