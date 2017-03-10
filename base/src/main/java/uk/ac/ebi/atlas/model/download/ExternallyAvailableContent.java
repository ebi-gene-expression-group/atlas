package uk.ac.ebi.atlas.model.download;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;

@AutoValue
public abstract class ExternallyAvailableContent {

    public abstract URI uri();

    public abstract ResourceType resourceType();

    public abstract Function<OutputStream, Void> stream();

    public static ExternallyAvailableContent create(URI uri, ResourceType resourceType, Function<OutputStream, Void> stream){
        return new AutoValue_ExternallyAvailableContent(uri, resourceType, stream);
    }

    public JsonObject asJson(){
        JsonObject result = new JsonObject();
        result.addProperty("kind", resourceType().resourceKind.name());
        result.addProperty("type", resourceType().name());
        //maybe the description will vary for some resources - if so then the supplier should create it and pass as argument
        result.addProperty("description", resourceType().description());
        result.addProperty("uri", uri().toString());



        return result;
    }


    public interface Supplier<E extends Experiment> {
        Collection<ExternallyAvailableContent> get(E experiment);
    }
}
