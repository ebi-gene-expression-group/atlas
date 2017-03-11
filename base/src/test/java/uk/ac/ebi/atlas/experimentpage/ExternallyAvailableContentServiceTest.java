package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;

import javax.annotation.Nullable;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ExternallyAvailableContentServiceTest {

    Experiment experiment = BaselineExperimentTest.mockExperiment();

    Function<OutputStream, Void> streamFunction = new Function<OutputStream, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable OutputStream outputStream) {
            return null;
        }
    };

    class MockSupplier extends ExternallyAvailableContent.Supplier<Experiment> {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(new ExternallyAvailableContent(makeUri("test-resource"), ExternallyAvailableContent.Description.create("", "",""), streamFunction));
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> supplier = new MockSupplier();

    Function<OutputStream, Void> differentStreamFunction = new Function<OutputStream, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable OutputStream outputStream) {
            return null;
        }
    };

    class MockDifferentSupplier extends ExternallyAvailableContent.Supplier<Experiment> {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(new ExternallyAvailableContent(makeUri("different-resource"), ExternallyAvailableContent.Description.create("", "",""), differentStreamFunction));
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> differentSupplier = new MockDifferentSupplier();

    class MockNullSupplier extends ExternallyAvailableContent.Supplier<Experiment> {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of();
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> nullSupplier = new MockNullSupplier();

    ExternallyAvailableContent externallyAvailableContent = supplier.get(experiment).iterator().next();
    ExternallyAvailableContent differentContent = differentSupplier.get(experiment).iterator().next();

    ExternallyAvailableContentService<Experiment> subject;

    public void testNoResourcesFor(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers){
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment);
        assertThat(result, Matchers.empty());
    }

    public void testNotFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers, ExternallyAvailableContent externallyAvailableContent){
        try {
            subject = new ExternallyAvailableContentService<>(suppliers);

            subject.stream(experiment, externallyAvailableContent.uri);
        } catch (ResourceNotFoundException e){
            return;
        }

        fail("Should throw exception");
    }

    public void testFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers, ExternallyAvailableContent externallyAvailableContent){
        subject = new ExternallyAvailableContentService<>(suppliers);

        Function result = subject.stream(experiment, externallyAvailableContent.uri);

        assertSame(result, externallyAvailableContent.stream);
    }


    @Test
    public void noSuppliersReturnsEmptyList() {
        testNoResourcesFor(ImmutableList.<ExternallyAvailableContent.Supplier<Experiment>>of());
    }

    @Test
    public void noResourcesReturnsEmptyList() {
        testNoResourcesFor(ImmutableList.of(nullSupplier));
    }

    @Test
    public void noResourcesThrows() {
        testNotFound(ImmutableList.<ExternallyAvailableContent.Supplier<Experiment>>of(), externallyAvailableContent);
        testNotFound(ImmutableList.of(nullSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(differentSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(nullSupplier, differentSupplier), externallyAvailableContent);
    }

    @Test
    public void canCreateSupplier() {
        testFound(ImmutableList.of(supplier), externallyAvailableContent);
        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testFound(ImmutableList.of(differentSupplier, supplier), externallyAvailableContent);
        testFound(ImmutableList.of(differentSupplier, nullSupplier, supplier), externallyAvailableContent);
    }

    @Test
    public void differentSuppliers() {
        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testFound(ImmutableList.of(supplier, differentSupplier), differentContent);

        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(supplier, nullSupplier), differentContent);
    }


}