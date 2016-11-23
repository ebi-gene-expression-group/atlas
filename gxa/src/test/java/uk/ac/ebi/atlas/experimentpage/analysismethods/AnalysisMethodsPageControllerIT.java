package uk.ac.ebi.atlas.experimentpage.analysismethods;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalysisMethodsPageControllerIT {

    private static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    @Inject
    private AnalysisMethodsPageController subject;

    @Inject
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;

    private HttpServletRequest requestMock;

    private Model model = new BindingAwareModelMap();

    @Before
    public void initSubject() throws Exception {
        requestMock = mock(HttpServletRequest.class);
        BaselineExperiment proteomicsBaselineExperiment = proteomicsBaselineExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/analysis-methods");
    }

    @Test
    public void testExtractProteomicsAnalysisMethods() throws IOException {
        //given
//        subject.proteomicsAnalysisMethods(EXPERIMENT_ACCESSION,"", model, requestMock);
//
//        TsvReader tsvReader = fileTsvReaderBuilder.forTsvFilePathTemplate(analysisMethodsTemplate).withExperimentAccession(EXPERIMENT_ACCESSION).build();
//        List<String[]> result = tsvReader.readAll();
//
//        assertThat(result.size(), is(greaterThan(0)));
//        assertThat(Arrays.asList(result.get(0)), hasItem("Pipeline "));
    }

    @Test
    public void testExtractPdfURL() throws IOException {
        assertThat(subject.generatePDFPath(EXPERIMENT_ACCESSION, "HumanDataAnalysisFigure"), is("/expdata/E-PROT-1/HumanDataAnalysisFigure.pdf"));
    }

}