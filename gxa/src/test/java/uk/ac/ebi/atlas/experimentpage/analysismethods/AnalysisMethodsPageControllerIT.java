package uk.ac.ebi.atlas.experimentpage.analysismethods;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class AnalysisMethodsPageControllerIT {
    private static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    @Inject
    private AnalysisMethodsPageController subject;

    private HttpServletRequest requestMock;

    @Before
    public void initSubject() {
        requestMock = mock(HttpServletRequest.class);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/analysis-methods");
    }

    @Test
    public void testExtractPdfURL()  {
        assertThat(
                subject.generatePDFPath(EXPERIMENT_ACCESSION, "HumanDataAnalysisFigure"),
                is("/expdata/E-PROT-1/HumanDataAnalysisFigure.pdf"));
    }
}
