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

public class ExternallyAvailableContentService<E extends Experiment> {

    private final List<ExternallyAvailableContent.Supplier<E>> suppliers;

    public ExternallyAvailableContentService(List<ExternallyAvailableContent.Supplier<E>> suppliers) {
        this.suppliers = suppliers;
    }

    private Function<ExternallyAvailableContent.Supplier<E>,
                     Stream<ExternallyAvailableContent>> extractContentFromSupplier(final E experiment) {
        return supplier -> supplier.get(experiment).stream();
    }

    public Function<HttpServletResponse, Void> stream(E experiment, final URI uri){
        return suppliers.stream()
                .filter(eSupplier -> eSupplier.comesFromThisSupplier(uri))
                .findFirst()
                .orElseGet(() -> { throw new ResourceNotFoundException(uri.toString());
        }).get(experiment, uri).stream;
    }

    public List<ExternallyAvailableContent> list(final E experiment, final ExternallyAvailableContent.ContentType contentType){
        return suppliers.stream()
                .filter(eSupplier -> eSupplier.contentType().equals(contentType))
                .flatMap(extractContentFromSupplier(experiment))
                .collect(Collectors.toList());
    }

}
