package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExperimentDesignFileTest {

    MockDataFileHub fileHub = MockDataFileHub.create();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    @Mock
    BaselineExperiment baselineExperiment;

    @Mock
    MicroarrayExperiment differentialMicroarrayExperiment;

    @Mock
    DifferentialExperiment differentialRnaSeqExperiment;

    // subject
    ExperimentDesignFile.Baseline baselineExperimentDesignFile =
            new ExperimentDesignFile.Baseline(fileHub);
    ExperimentDesignFile.Microarray differentialMicroarrayExperimentDesignFile =
            new ExperimentDesignFile.Microarray(fileHub);
    ExperimentDesignFile.RnaSeq differentialRnaSeqExperimentDesignFile =
            new ExperimentDesignFile.RnaSeq(fileHub);

    @Test
    public void hasOnlyOneContentItem() throws Exception {
        assertThat(baselineExperimentDesignFile.get(baselineExperiment), hasSize(1));
        assertThat(differentialMicroarrayExperimentDesignFile.get(differentialMicroarrayExperiment), hasSize(1));
        assertThat(differentialRnaSeqExperimentDesignFile.get(differentialRnaSeqExperiment), hasSize(1));
    }

    @Test
    public void hasTheRightContentType() throws Exception {
        assertThat(
                baselineExperimentDesignFile.contentType(),
                is(ExternallyAvailableContent.ContentType.DATA));
        assertThat(
                differentialMicroarrayExperimentDesignFile.contentType(),
                is(ExternallyAvailableContent.ContentType.DATA));
        assertThat(
                differentialRnaSeqExperimentDesignFile.contentType(),
                is(ExternallyAvailableContent.ContentType.DATA));
    }

    @Test
    public void hasTheRightURI() throws Exception {
        assertThat(baselineExperimentDesignFile.reservedUris(), contains("experiment-design"));
        assertThat(differentialMicroarrayExperimentDesignFile.reservedUris(), contains("experiment-design"));
        assertThat(differentialRnaSeqExperimentDesignFile.reservedUris(), contains("experiment-design"));
    }

    @Test
    public void buildsGoodURLs() throws Exception {
        assertThat(
                ExperimentDesignFile.makeUrl("E-FOOBAR", "50m3-c0d3"),
                is(equalTo("experiments-content/E-FOOBAR/resources/experiment-design?accessKey=50m3-c0d3")));
    }

    @Test
    public void lookingGood() throws Exception {
        fileHub.addExperimentDesignFile(
                "E-FOOBAR",
                ImmutableList.of(
                        new String[]{"Run", "Sample Characteristic[foo]", "Sample Charactersitic Ontology Term[foo]"},
                        new String[]{"assay1", "bar", "http://www.ebi.ac.uk/efo/EFO_BAR"},
                        new String[]{"assay2", "bar", "http://www.ebi.ac.uk/efo/EFO_BAR"},
                        new String[]{"assay3", "bar", "http://www.ebi.ac.uk/efo/EFO_BAR"},
                        new String[]{"assay4", "bar", "http://www.ebi.ac.uk/efo/EFO_BAR"},
                        new String[]{"assay5", "bar", "http://www.ebi.ac.uk/efo/EFO_BAR"}

                )
        );

        when(baselineExperiment.getAccession()).thenReturn("E-FOOBAR");
        when(baselineExperiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(
                new AssayGroup(
                        "g1", "assay1", "assay2", "assay3"),
                new AssayGroup(
                        "g2", ImmutableSet.of(new BiologicalReplicate("t1", ImmutableSet.of("assay4", "assay5"))))));

        ExternallyAvailableContent externallyAvailableContent =
                baselineExperimentDesignFile.get(baselineExperiment).iterator().next();

        externallyAvailableContent.stream.apply(httpServletResponse);
        assertThat(httpServletResponse.getContentAsString(), is(equalTo(
                "Run\tSample Characteristic[foo]\tSample Charactersitic Ontology Term[foo]\tAnalysed\n" +
                "assay1\tbar\thttp://www.ebi.ac.uk/efo/EFO_BAR\tYes\n" +
                "assay2\tbar\thttp://www.ebi.ac.uk/efo/EFO_BAR\tYes\n" +
                "assay3\tbar\thttp://www.ebi.ac.uk/efo/EFO_BAR\tYes\n" +
                "assay4\tbar\thttp://www.ebi.ac.uk/efo/EFO_BAR\tYes\n" +
                "assay5\tbar\thttp://www.ebi.ac.uk/efo/EFO_BAR\tYes\n")));
    }

}
