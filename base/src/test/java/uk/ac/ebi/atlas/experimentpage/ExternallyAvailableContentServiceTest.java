package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import javax.annotation.Nullable;
import java.io.OutputStream;
import java.net.URI;
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

    ExternallyAvailableContent externallyAvailableContent;

    ExternallyAvailableContent.Supplier<Experiment> supplier = new ExternallyAvailableContent.Supplier<Experiment>() {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(externallyAvailableContent);
        }
    };

    ExternallyAvailableContent differentContent;

    Function<OutputStream, Void> differentStreamFunction = new Function<OutputStream, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable OutputStream outputStream) {
            return null;
        }
    };

    ExternallyAvailableContent.Supplier<Experiment> differentSupplier = new ExternallyAvailableContent.Supplier<Experiment>() {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(differentContent);
        }
    };

    ExternallyAvailableContent.Supplier<Experiment> nullSupplier = new ExternallyAvailableContent.Supplier<Experiment>() {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of();
        }
    };

    ExternallyAvailableContentService<Experiment> subject;


    @Before
    public void setUp() throws Exception {
        externallyAvailableContent =  ExternallyAvailableContent.create(new URI("test-resource"), ResourceType.EXTRA_INFO, streamFunction);
        differentContent =  ExternallyAvailableContent.create(new URI("different-resource"), ResourceType.EXTRA_INFO, differentStreamFunction);
    }

    public void testNoResourcesFor(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers){
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment);
        assertThat(result, Matchers.empty());
    }

    public void testNotFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers, ExternallyAvailableContent externallyAvailableContent){
        try {
            subject = new ExternallyAvailableContentService<>(suppliers);

            subject.stream(experiment, externallyAvailableContent.uri());
        } catch (ResourceNotFoundException e){
            return;
        }

        fail("Should throw exception");
    }

    public void testFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers, ExternallyAvailableContent externallyAvailableContent){
        subject = new ExternallyAvailableContentService<>(suppliers);

        Function result = subject.stream(experiment, externallyAvailableContent.uri());

        assertSame(result, externallyAvailableContent.stream());
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