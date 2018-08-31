package uk.ac.ebi.atlas.bioentity.properties;

import com.google.gson.JsonObject;

public class PropertyLink {

    private String text;
    private String url;
    private int relevance;

    PropertyLink(String text, String url, int relevance) {
        this.text = text;
        this.url = url;
        this.relevance = relevance;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public int getRelevance() {
        return relevance;
    }

    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("text", text);
        result.addProperty("url", url);
        result.addProperty("relevance", relevance);
        return result;
    }

}
