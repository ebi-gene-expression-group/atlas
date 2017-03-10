package uk.ac.ebi.atlas.model.download;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
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

    public abstract String group();

    public static ExternallyAvailableContent create(URI uri, ResourceType resourceType, Function<OutputStream, Void> stream){
        return create(uri, resourceType, stream, "");
    }

    public static ExternallyAvailableContent create(URI uri, ResourceType resourceType, Function<OutputStream, Void> stream, String group){
        return new AutoValue_ExternallyAvailableContent(uri, resourceType, stream, group);
    }

    public JsonObject asJson(){
        JsonObject result = new JsonObject();
        result.addProperty("kind", resourceType().resourceKind.name());
        //I'm imagining this to be a contrast name for resources per contrast
        result.addProperty("group", group());
        //how to render this content
        result.addProperty("type", resourceType().name());
        //maybe the description will vary for some resources - if so then the supplier should create it and pass as argument
        result.addProperty("description", resourceType().description());
        result.addProperty("uri", uri().toString());



        return result;
    }


    public static abstract class Supplier<E extends Experiment> {

        /*
        Tell the user what resources are available for this experiment
         */
        public abstract Collection<ExternallyAvailableContent> get(E experiment);

        /*
        If o1.equals(o2) then o1.base() should equal o2.base()
        Needs to finish with the slash!
        */
        protected URI base(){
            return URI.create(this.getClass().getSimpleName()+"/");
        }

        protected final URI makeUri(String id){
            return base().resolve(id);
        }

        public final boolean comesFromThisSupplier(URI uri){
            return uri.resolve(".").equals(base());
        }

        /*
        The user wants a specific resource - give him that
        Subclasses could override this method for efficiency
        */
        public ExternallyAvailableContent get(E experiment, final URI uri){
            return FluentIterable.from(get(experiment)).firstMatch(new Predicate<ExternallyAvailableContent>() {
                @Override
                public boolean apply(ExternallyAvailableContent externallyAvailableContent) {
                    return externallyAvailableContent.uri().equals(uri);
                }
            }).or(new com.google.common.base.Supplier<ExternallyAvailableContent>() {
                @Override
                public ExternallyAvailableContent get() {
                    throw new ResourceNotFoundException(uri.toString());
                }
            });
        }
    }
}
