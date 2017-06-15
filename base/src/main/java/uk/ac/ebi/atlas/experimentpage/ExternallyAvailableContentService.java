package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

public class ExternallyAvailableContentService<E extends Experiment> {

    private final List<ExternallyAvailableContent.Supplier<E>> suppliers;

    public ExternallyAvailableContentService(List<ExternallyAvailableContent.Supplier<E>> suppliers){
        this.suppliers = suppliers;
    }

    private Function<ExternallyAvailableContent.Supplier<E>, Iterable<ExternallyAvailableContent>> extractContentFromSupplier(final E experiment){
        return supplier -> supplier.get(experiment);
    }

    public Function<HttpServletResponse, Void> stream(E experiment, final URI uri){
        return FluentIterable.from(suppliers).firstMatch(eSupplier -> eSupplier.comesFromThisSupplier(uri)).or(() -> {
            throw new ResourceNotFoundException(uri.toString());
        }).get(experiment, uri).stream;
    }

    public List<ExternallyAvailableContent> list(final E experiment, final ExternallyAvailableContent.ContentType contentType){
        return FluentIterable.from(suppliers).filter(eSupplier -> {
            return eSupplier.contentType().equals(contentType);
        }).transformAndConcat(extractContentFromSupplier(experiment)).toList();
    }

}
