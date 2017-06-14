package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationPropertiesTest {
    private static final String FEEDBACK_EMAIL_PROPERTY_KEY = "feedback.email";
    private static final String FEEDBACK_EMAIL_VALUE = "abc@abc.com";
    private static final String ARRAYEXPRESS_URL = "https://www.ebi.ac.uk/arrayexpress/";
    private static final String EXPERIMENT_ARRAYEXPRESS_URL_TEMPLATE = "experiment.arrayexpress.url.template";
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String A_AFFY_35_NAME = "A-AFFY-35-NAME";
    private static final String EXPERIMENT_ARRAYEXPRESS_ARRAYS_URL_TEMPLATE = "experiment.arrayexpress.arrays.url.template";
    private static final String ARRAYEXPRESS_ARRAYS_URL = "https://www.ebi.ac.uk/arrayexpress/arrays/";
    private static final String EXPERIMENT_PUBMED_URL_TEMPLATE = "experiment.pubmed.url.template";
    private static final String PUBMED_URL = "https://europepmc.org/abstract/MED/";
    private static final String PUB_MED_ID = "123456";

    private static final String EXPERIMENT_URL = "http://x.y/z/experiments/X";
    private static final String CONTEXT_PATH = "/z";

    private static final Map<String, String[]> REQUEST_PARAMETERS_MAP =
            ImmutableMap.of("p1",new String[]{"1"},"p2",new String[]{"2"});
    private static final String DOWNLOAD_URL =
            "/experiments/X.tsv?p1=1&geneQuery="+SemanticQuery.create().toUrlEncodedJson()+"&p2=2";

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Mock
    private Properties configurationPropertiesMock;

    @Mock
    private ArrayDesignTrader arrayDesignTraderMock;

    private ApplicationProperties subject;

    @Before
    public void setUp() throws Exception {
        when(configurationPropertiesMock.getProperty(
                EXPERIMENT_ARRAYEXPRESS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_URL + "{0}");
        when(configurationPropertiesMock.getProperty(
                FEEDBACK_EMAIL_PROPERTY_KEY)).thenReturn(FEEDBACK_EMAIL_VALUE);

        when(configurationPropertiesMock.getProperty(
                EXPERIMENT_ARRAYEXPRESS_ARRAYS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_ARRAYS_URL + "{0}");
        when(configurationPropertiesMock.getProperty(EXPERIMENT_PUBMED_URL_TEMPLATE)).thenReturn(PUBMED_URL + "{0}");

        //given
        when(httpServletRequestMock.getContextPath()).thenReturn(CONTEXT_PATH);
        when(httpServletRequestMock.getRequestURI()).thenReturn(EXPERIMENT_URL);
        when(httpServletRequestMock.getParameterMap()).thenReturn(REQUEST_PARAMETERS_MAP);

        when(arrayDesignTraderMock.getArrayDesignAccession(A_AFFY_35_NAME)).thenReturn(A_AFFY_35);

        subject = new ApplicationProperties(configurationPropertiesMock, arrayDesignTraderMock);
    }

    @Test
    public void testGetArrayExpressURL() throws Exception {
        assertThat(subject.getArrayExpressURL(EXPERIMENT_ACCESSION), is(ARRAYEXPRESS_URL + EXPERIMENT_ACCESSION));
    }

    @Test
    public void testGetArrayExpressArrayURL() throws Exception {
        assertThat(subject.getArrayExpressArrayURL(A_AFFY_35_NAME), is(ARRAYEXPRESS_ARRAYS_URL + A_AFFY_35));
    }

    @Test
    public void testGetPubMedURL() throws Exception {
        assertThat(subject.getPubMedURL(PUB_MED_ID), is(PUBMED_URL + PUB_MED_ID));
    }

    @Test
    public void testGetFeedbackEmailAddress() throws Exception {
        assertThat(subject.getFeedbackEmailAddress(), is(FEEDBACK_EMAIL_VALUE));
    }

    @Test
    public void buildDownloadUrl() {
        //when
        String downloadUrl = subject.buildDownloadURL(SemanticQuery.create(),httpServletRequestMock);

        // TODO Assert that the parameters are present with the required values in any order
        //then
        assertThat(downloadUrl, is(DOWNLOAD_URL));
    }

    @Test
    public void buildServerUrl() throws Exception {

        when(httpServletRequestMock.getServerName()).thenReturn("localhost");
        when(httpServletRequestMock.getServerPort()).thenReturn(9090);
        when(httpServletRequestMock.getContextPath()).thenReturn("/gxa");
        when(httpServletRequestMock.isSecure()).thenReturn(false);

        assertThat(ApplicationProperties.buildServerURL(httpServletRequestMock), is("http://localhost:9090/gxa"));
    }

    @Test
    public void getBaselineReferenceExperimentAccession() throws Exception {
        SpeciesProperties homoSapiensProperties =
                SpeciesProperties.create(
                        "Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.<ImmutableMap<String, String>>of());

        Species species = new Species("Homo sapiens", homoSapiensProperties);

        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(species), startsWith("E-"));
    }

    @Test
    public void getUnknownSpeciesBaselineReferenceExperimentAccession() throws Exception {
        Species unknownSpecies = new Species("foobar", SpeciesProperties.UNKNOWN);

        assertThat(unknownSpecies.isUnknown(), is (true));
        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(unknownSpecies), is(nullValue()));
    }

}
