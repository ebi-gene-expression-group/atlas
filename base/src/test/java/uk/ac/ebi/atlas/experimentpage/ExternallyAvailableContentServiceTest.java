package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ExternallyAvailableContentServiceTest {
    private Experiment experiment = MockExperiment.createBaselineExperiment();

    private Function<HttpServletResponse, Void> streamFunction = new Function<HttpServletResponse, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable HttpServletResponse outputStream) {
            return null;
        }
    };

    private static final ExternallyAvailableContent.ContentType TEST_CONTENT_TYPE =
            ExternallyAvailableContent.ContentType.DATA;

    abstract class TestSupplier extends ExternallyAvailableContent.Supplier<Experiment> {
        @Override
        public ExternallyAvailableContent.ContentType contentType() {
            return TEST_CONTENT_TYPE;
        }
    }

    class MockSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(
                    new ExternallyAvailableContent(makeUri("test-resource"),
                            ExternallyAvailableContent.Description.create("", ""),
                            streamFunction));
        }
    }

    private ExternallyAvailableContent.Supplier<Experiment> supplier = new MockSupplier();

    private Function<HttpServletResponse, Void> differentStreamFunction = new Function<HttpServletResponse, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable HttpServletResponse outputStream) {
            return null;
        }
    };

    class MockDifferentSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(
                    new ExternallyAvailableContent(
                            makeUri("different-resource"),
                            ExternallyAvailableContent.Description.create("", ""),
                            differentStreamFunction));
        }
    }

    private ExternallyAvailableContent.Supplier<Experiment> differentSupplier = new MockDifferentSupplier();

    class MockNullSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of();
        }
    }

    private ExternallyAvailableContent.Supplier<Experiment> nullSupplier = new MockNullSupplier();


    class RedirectingSupplier extends TestSupplier {
        private String redirectRoot;

        RedirectingSupplier(String redirectRoot) {
            this.redirectRoot = redirectRoot;
        }
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of(
                    new ExternallyAvailableContent(redirectRoot+"/"+experiment.getAccession(),
                            ExternallyAvailableContent.Description.create("", ""))
            );
        }
    }

    private ExternallyAvailableContent.Supplier<Experiment> redirectingSupplier =
            new RedirectingSupplier("/experiments/");

    private ExternallyAvailableContent externallyAvailableContent = supplier.get(experiment).iterator().next();
    private ExternallyAvailableContent differentContent = differentSupplier.get(experiment).iterator().next();

    private ExternallyAvailableContentService<Experiment> subject;

    private void testNoResourcesListed(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers) {
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment, TEST_CONTENT_TYPE);
        assertThat(result, Matchers.empty());
    }

    private void testSomeResourcesListed(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers) {
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment, TEST_CONTENT_TYPE);
        assertThat(result, Matchers.not(Matchers.empty()));
    }

    private void testNotFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers,
                              ExternallyAvailableContent externallyAvailableContent) {
        try {
            subject = new ExternallyAvailableContentService<>(suppliers);

            subject.stream(experiment, externallyAvailableContent.uri);
        } catch (ResourceNotFoundException e) {
            return;
        }

        fail("Should throw exception");
    }

    private void testFound(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers,
                           ExternallyAvailableContent externallyAvailableContent) {
        subject = new ExternallyAvailableContentService<>(suppliers);

        Function<HttpServletResponse, Void> result = subject.stream(experiment, externallyAvailableContent.uri);

        assertSame(result, externallyAvailableContent.stream);
    }


    @Test
    public void noSuppliersReturnsEmptyList() {
        testNoResourcesListed(ImmutableList.<ExternallyAvailableContent.Supplier<Experiment>>of());
    }

    @Test
    public void noResourcesReturnsEmptyList() {
        testNoResourcesListed(ImmutableList.of(nullSupplier));
    }

    @Test
    public void noResourcesThrows() {
        testNotFound(ImmutableList.<ExternallyAvailableContent.Supplier<Experiment>>of(), externallyAvailableContent);
        testNotFound(ImmutableList.of(nullSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(differentSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(nullSupplier, differentSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(redirectingSupplier), externallyAvailableContent);
    }

    @Test
    public void supplierListsResources() {
        testSomeResourcesListed(ImmutableList.of(supplier));
        testSomeResourcesListed(ImmutableList.of(supplier, nullSupplier));
        testSomeResourcesListed(ImmutableList.of(nullSupplier, supplier));
    }

    @Test
    public void canCreateSupplier() {
        testFound(ImmutableList.of(supplier), externallyAvailableContent);
        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testFound(ImmutableList.of(differentSupplier, supplier), externallyAvailableContent);
        testFound(ImmutableList.of(differentSupplier, nullSupplier, supplier), externallyAvailableContent);
        testFound(ImmutableList.of(redirectingSupplier, supplier), externallyAvailableContent);
    }

    @Test
    public void differentSuppliers() {
        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testFound(ImmutableList.of(supplier, differentSupplier), differentContent);

        testFound(ImmutableList.of(supplier, differentSupplier), externallyAvailableContent);
        testNotFound(ImmutableList.of(supplier, nullSupplier), differentContent);
    }

    @Test
    public void redirectingSupplier() {
        ExternallyAvailableContent contentWithRedirect = redirectingSupplier.get(experiment).iterator().next();
        assertThat(contentWithRedirect.uri.getScheme(), is("redirect"));

        assertThat(externallyAvailableContent.uri.getScheme(), isEmptyOrNullString());
    }

    @Test
    public void redirectsBothInternalAndExternal() {
        assertThat(
                new RedirectingSupplier("/experiments").get(experiment).iterator().next().uri.getScheme(),
                is("redirect"));
        assertThat(
                new RedirectingSupplier("www.ebi.ac.uk/gxa/experiments")
                        .get(experiment).iterator().next().uri.getScheme(),
                is("redirect"));
        assertThat(
                new RedirectingSupplier("https://www.ebi.ac.uk/gxa/experiments")
                        .get(experiment).iterator().next().uri.getScheme(),
                is("redirect"));

        assertThat(
                new RedirectingSupplier("/experiments")
                        .get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                containsString("/experiments"));
        assertThat(
                new RedirectingSupplier("www.ebi.ac.uk/gxa/experiments")
                        .get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                containsString("www.ebi.ac.uk/gxa/experiments"));
        assertThat(
                new RedirectingSupplier("https://www.ebi.ac.uk/gxa/experiments")
                        .get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                is("https://www.ebi.ac.uk/gxa/experiments/"+experiment.getAccession()));

    }

    @Test
    public void supplierWithDifferentTypeIsFilteredOut() {
        class MockSupplierWithDifferentContentType extends MockSupplier {
            @Override
            public ExternallyAvailableContent.ContentType contentType() {
                return ExternallyAvailableContent.ContentType.PLOTS;
            }
        }

        ExternallyAvailableContent.Supplier<Experiment> supplierWithDifferentContentType =
                new MockSupplierWithDifferentContentType();

        testNoResourcesListed(ImmutableList.of(supplierWithDifferentContentType));
        testSomeResourcesListed(ImmutableList.of(supplier, supplierWithDifferentContentType));
        testSomeResourcesListed(ImmutableList.of(supplierWithDifferentContentType, supplier));
    }
}
