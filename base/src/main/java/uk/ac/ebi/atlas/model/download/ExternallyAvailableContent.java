package uk.ac.ebi.atlas.model.download;

import com.google.auto.value.AutoValue;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonObject;
import org.apache.commons.lang.NotImplementedException;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;

public class ExternallyAvailableContent {

    public final URI uri;

    public final Description description;

    public final Function<HttpServletResponse, Void> stream;


    public ExternallyAvailableContent(URI uri, Description description, Function<HttpServletResponse, Void> stream){
        this.uri = uri;
        this.description = description;
        this.stream = stream;
    }

    public ExternallyAvailableContent(final String redirect, Description description){
        this.uri = URI.create("redirect:"+redirect);
        this.description = description;
        this.stream = new Function<HttpServletResponse, Void>() {
            @Override
            public Void apply(HttpServletResponse response) {
                throw new NotImplementedException(MessageFormat.format(
                        "This content doesn't stream. This shouldn't be reachable as {0} is a redirect.", redirect)
                );
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternallyAvailableContent that = (ExternallyAvailableContent) o;
        return Objects.equal(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri);
    }

    @AutoValue
    public abstract static class Description {

        /*
        A strong hint to developers that if they have a multi-part group or type they should use this
         */
        public static String join (String ... sections){
            return Joiner.on("/").join(sections);
        }
        /*
        where on the download tab this would go?
        I'm imagining this to be plot/data/supplementary info, sometimes followed by a contrast name for resources per contrast
        Sometimes it will be irrelevant because it is called by something else e.g. extra link for experiment design
        */
        public abstract String group();
        /*
        how to render?
         for images where we have icons it would be icon/ma
        for links: link
        */
        public abstract String type();

        /*
        the text that goes next to icon or on link
         */
        public abstract String description();

        public static Description create(String group, String type, String description){
            return new AutoValue_ExternallyAvailableContent_Description(group, type, description);
        }

        public JsonObject asJson(){
            JsonObject result = new JsonObject();
            result.addProperty("group", group());
            result.addProperty("type", type());
            result.addProperty("description", description());
            return result;
        }
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
            String[] xs = this.getClass().getCanonicalName().split(".");
            String uniqueEnoughButShortName = xs.length >1 ? xs[xs.length-2]+"."+xs[xs.length-1] : xs[xs.length-1];
            return URI.create(uniqueEnoughButShortName+"/");
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
                    return externallyAvailableContent.uri.equals(uri);
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
