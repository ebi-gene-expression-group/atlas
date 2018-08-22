package uk.ac.ebi.atlas.model.download;

import com.google.auto.value.AutoValue;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.apache.commons.lang.NotImplementedException;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.function.Function;

public class ExternallyAvailableContent {
    public final URI uri;
    public final Description description;
    public final Function<HttpServletResponse, Void> stream;

    public ExternallyAvailableContent(URI uri, Description description, Function<HttpServletResponse, Void> stream) {
        this.uri = uri;
        this.description = description;
        this.stream = stream;
    }

    public ExternallyAvailableContent(final String redirect, Description description) {
        this.uri = URI.create("redirect:" + redirect);
        this.description = description;
        this.stream = response -> {
            throw new NotImplementedException(MessageFormat.format(
                    "This content doesn't stream. This shouldn't be reachable as {0} is a redirect.", redirect));
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExternallyAvailableContent that = (ExternallyAvailableContent) o;
        return Objects.equal(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri);
    }

    @AutoValue
    public abstract static class Description {
        // Where in the resources tab this should go?
        public abstract String group();
        // How to render? For images where we have icons it would be icon/ma, For links: link
        public abstract String type();
        // The text that goes next to icon or on link
        public abstract String description();

        public static Description create(String type, String description) {
            return create("", type, description);
        }

        public static Description create(String group, String type, String description) {
            return new AutoValue_ExternallyAvailableContent_Description(group, type, description);
        }

        public JsonObject asJson() {
            JsonObject result = new JsonObject();
            result.addProperty("group", group());
            result.addProperty("type", type());
            result.addProperty("description", description());
            return result;
        }
    }

    public abstract static class Supplier<E extends Experiment> {
        // Tell the user what resources are available for this experiment
        public abstract Collection<ExternallyAvailableContent> get(E experiment);
        public abstract ContentType contentType();

        // If o1.equals(o2) then o1.base() should equal o2.base(); needs to finish with the slash!
        protected URI base() {
            String[] xs = this.getClass().getCanonicalName().split("\\.");

            String uniqueEnoughButShortName =
                    xs.length > 1 ?
                            xs[xs.length - 2] + "." + xs[xs.length - 1] :
                            xs[xs.length - 1];
            return URI.create(uniqueEnoughButShortName + "/");
        }

        protected Collection<String> reservedUris() {
            return ImmutableSet.of();
        }

        private boolean matchesReservedUri(URI uri) {
            return reservedUris().contains(uri.toString().toLowerCase());
        }

        protected final URI makeUri(String id) {
            return base().resolve(id);
        }

        public final boolean comesFromThisSupplier(URI uri) {
            return uri.resolve(".").equals(base()) || matchesReservedUri(uri);
        }

        // The user wants a specific resource - give him that. Subclasses could override this method for efficiency
        public ExternallyAvailableContent get(E experiment, final URI uri) {
            return get(experiment).stream()
                    .filter(externallyAvailableContent ->
                            externallyAvailableContent.uri.equals(uri) || matchesReservedUri(uri))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(uri.toString()));
        }
    }

    public enum ContentType {
        DATA, SUPPLEMENTARY_INFORMATION, PLOTS
    }
}
