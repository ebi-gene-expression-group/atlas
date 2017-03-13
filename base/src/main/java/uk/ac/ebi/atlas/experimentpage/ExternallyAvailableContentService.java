package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
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

    private Function<ExternallyAvailableContent.Supplier<E>, Iterable<ExternallyAvailableContent>> makeTransform(final E experiment){
        return new Function<ExternallyAvailableContent.Supplier<E>, Iterable<ExternallyAvailableContent>>() {
            @Override
            public Iterable<ExternallyAvailableContent> apply(ExternallyAvailableContent.Supplier<E> supplier) {
                return supplier.get(experiment);
            }
        };
    }

    public Function<HttpServletResponse, Void> stream(E experiment, final URI uri){
        return FluentIterable.from(suppliers).firstMatch(new Predicate<ExternallyAvailableContent.Supplier<E>>() {
            @Override
            public boolean apply(ExternallyAvailableContent.Supplier<E> eSupplier) {
                return eSupplier.comesFromThisSupplier(uri);
            }
        }).or(new Supplier<ExternallyAvailableContent.Supplier<E>>() {
            @Override
            public ExternallyAvailableContent.Supplier<E> get() {
                throw new ResourceNotFoundException(uri.toString());
            }
        }).get(experiment, uri).stream;
    }

    public List<ExternallyAvailableContent> list(final E experiment){
        return FluentIterable.from(suppliers).transformAndConcat(makeTransform(experiment)).toList();
    }

}
