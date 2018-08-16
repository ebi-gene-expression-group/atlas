package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class ExternallyAvailableContentService<E extends Experiment> {

    private final List<ExternallyAvailableContent.Supplier<E>> suppliers;

    private static final String LIST_RESOURCES_URL = "json/experiments/{experimentAccession}/resources/{contentType}";
    private static final String STREAM_RESOURCES_URL = "experiments-content/{experimentAccession}/resources/**";

    public ExternallyAvailableContentService(List<ExternallyAvailableContent.Supplier<E>> suppliers) {
        this.suppliers = suppliers;
    }

    private Function<ExternallyAvailableContent.Supplier<E>,
                     Stream<ExternallyAvailableContent>> extractContentFromSupplier(final E experiment) {
        return supplier -> supplier.get(experiment).stream();
    }

    public Function<HttpServletResponse, Void> stream(E experiment, final URI uri) {
        return suppliers.stream()
                .filter(eSupplier -> eSupplier.comesFromThisSupplier(uri))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(uri.toString()))
                .get(experiment, uri).stream;
    }

    public List<ExternallyAvailableContent> list(final E experiment,
                                                 final ExternallyAvailableContent.ContentType contentType) {
        return suppliers.stream()
                .filter(eSupplier -> eSupplier.contentType().equals(contentType))
                .flatMap(extractContentFromSupplier(experiment))
                .collect(Collectors.toList());
    }

    public static String listResourcesUrl(String experimentAccession,
                                          String accessKey,
                                          ExternallyAvailableContent.ContentType contentType) {
        return LIST_RESOURCES_URL
                .replace("{experimentAccession}", experimentAccession)
                .replace(
                        "{contentType}",
                        contentType.name()) + (isNotEmpty(accessKey) ? "?accessKey=" + accessKey : "");
    }

    public static String streamResourcesUrl(String experimentAccession,
                                            String accessKey,
                                            String resourceName) {
        return STREAM_RESOURCES_URL
                .replace("{experimentAccession}", experimentAccession)
                .replace("**", resourceName) +
                (isNotEmpty(accessKey) ? "?accessKey=" + accessKey : "");
    }
}
