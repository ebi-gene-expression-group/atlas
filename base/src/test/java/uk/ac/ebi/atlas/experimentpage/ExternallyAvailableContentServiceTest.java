package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;

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

    Experiment experiment = BaselineExperimentTest.mockExperiment();

    Function<HttpServletResponse, Void> streamFunction = new Function<HttpServletResponse, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable HttpServletResponse outputStream) {
            return null;
        }
    };

    static final ExternallyAvailableContent.ContentType testContentType = ExternallyAvailableContent.ContentType.DATA;

    abstract class TestSupplier extends ExternallyAvailableContent.Supplier<Experiment> {
        @Override
        public ExternallyAvailableContent.ContentType contentType() {
            return testContentType;
        }
    }

    class MockSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(new ExternallyAvailableContent(makeUri("test-resource"), ExternallyAvailableContent.Description.create("",""), streamFunction));
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> supplier = new MockSupplier();

    Function<HttpServletResponse, Void> differentStreamFunction = new Function<HttpServletResponse, Void>() {
        @Nullable
        @Override
        public Void apply(@Nullable HttpServletResponse outputStream) {
            return null;
        }
    };

    class MockDifferentSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return Collections.singleton(new ExternallyAvailableContent(makeUri("different-resource"), ExternallyAvailableContent.Description.create("",""), differentStreamFunction));
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> differentSupplier = new MockDifferentSupplier();

    class MockNullSupplier extends TestSupplier {
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of();
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> nullSupplier = new MockNullSupplier();


    class RedirectingSupplier extends TestSupplier {

        String redirectRoot;

        public RedirectingSupplier(String redirectRoot){
            this.redirectRoot = redirectRoot;
        }
        @Override
        public Collection<ExternallyAvailableContent> get(Experiment experiment) {
            return ImmutableList.of(
                    new ExternallyAvailableContent(redirectRoot+"/"+experiment.getAccession(),
                            ExternallyAvailableContent.Description.create("",""))
            );
        }
    }

    ExternallyAvailableContent.Supplier<Experiment> redirectingSupplier = new RedirectingSupplier("/experiments/");

    ExternallyAvailableContent externallyAvailableContent = supplier.get(experiment).iterator().next();
    ExternallyAvailableContent differentContent = differentSupplier.get(experiment).iterator().next();

    ExternallyAvailableContentService<Experiment> subject;

    public void testNoResourcesListed(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers){
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment, testContentType);
        assertThat(result, Matchers.empty());
    }

    public void testSomeResourcesListed(List<ExternallyAvailableContent.Supplier<Experiment>> suppliers){
        subject = new ExternallyAvailableContentService<>(suppliers);
        List<?> result = subject.list(experiment, testContentType);
        assertThat(result, Matchers.not(Matchers.empty()));
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
    public void redirectingSupplier(){
        ExternallyAvailableContent contentWithRedirect = redirectingSupplier.get(experiment).iterator().next();
        assertThat(contentWithRedirect.uri.getScheme(), is("redirect"));

        assertThat(externallyAvailableContent.uri.getScheme(), isEmptyOrNullString());
    }

    @Test
    public void redirectsBothInternalAndExternal(){
        assertThat(new RedirectingSupplier("/experiments").get(experiment).iterator().next().uri.getScheme(), is("redirect"));
        assertThat(new RedirectingSupplier("www.ebi.ac.uk/gxa/experiments").get(experiment).iterator().next().uri.getScheme(), is("redirect"));
        assertThat(new RedirectingSupplier("https://www.ebi.ac.uk/gxa/experiments").get(experiment).iterator().next().uri.getScheme(), is("redirect"));

        assertThat(new RedirectingSupplier("/experiments").get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                containsString("/experiments"));
        assertThat(new RedirectingSupplier("www.ebi.ac.uk/gxa/experiments").get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                containsString("www.ebi.ac.uk/gxa/experiments"));
        assertThat(new RedirectingSupplier("https://www.ebi.ac.uk/gxa/experiments").get(experiment).iterator().next().uri.getSchemeSpecificPart(),
                is("https://www.ebi.ac.uk/gxa/experiments/"+experiment.getAccession()));

    }

    @Test
    public void supplierWithDifferentTypeIsFilteredOut(){
        class MockSupplierWithDifferentContentType extends MockSupplier {

            @Override
            public ExternallyAvailableContent.ContentType contentType() {
                return ExternallyAvailableContent.ContentType.PLOTS;
            }
        }

        ExternallyAvailableContent.Supplier<Experiment> supplierWithDifferentContentType = new MockSupplierWithDifferentContentType();

        testNoResourcesListed(ImmutableList.of(supplierWithDifferentContentType));
        testSomeResourcesListed(ImmutableList.of(supplier, supplierWithDifferentContentType));
        testSomeResourcesListed(ImmutableList.of(supplierWithDifferentContentType, supplier));
    }


}