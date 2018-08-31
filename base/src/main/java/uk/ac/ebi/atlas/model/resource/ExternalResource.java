package uk.ac.ebi.atlas.model.resource;

import com.google.gson.JsonObject;

import java.nio.file.Path;

public abstract class ExternalResource<T> extends AtlasResource<T> {
    protected final String uri;
    private final ResourceType type;

    ExternalResource(ResourceType type, Path path, String uri) {
        super(path);
        this.uri = uri;
        this.type = type;
    }

    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("type", type.resourceName);
        result.addProperty("uri", uri);
        return result;
    }
}
