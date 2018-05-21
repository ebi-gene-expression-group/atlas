package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.Publication;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static uk.ac.ebi.atlas.utils.ArrayExpressClient.AE_URL_TEMPLATE;

@RunWith(MockitoJUnitRunner.class)
public class ArrayExpressClientTest {
    private static final String E_FOOBAR_ACCESSION = "E-FOOBAR";
    private static final String E_FOOBAR_IDF_TITLE = "Experiment Foo Bar";
    private static final String E_FOOBAR_IDF_EXPERIMENT_DESCRIPTION = "Experiment Description Foo Bar";

    private MockRestServiceServer mockServer;

    @Mock
    private IdfParser idfParserMock;

    private ArrayExpressClient subject;

    @Before
    public void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        when(idfParserMock.parse(eq(E_FOOBAR_ACCESSION)))
                .thenReturn(new IdfParserOutput(E_FOOBAR_IDF_TITLE, E_FOOBAR_IDF_TITLE, Collections.emptyList(), 0, Collections.emptyList()));

        subject = new ArrayExpressClient(restTemplate, idfParserMock);
    }

    @Test
    public void onSuccessRetrievesNameFromXml() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(
                        withSuccess(
                                "<xml><experiment><name>Experiment name from XML</name></experiment></xml>",
                                MediaType.APPLICATION_XML));

        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo("Experiment name from XML");
        verifyZeroInteractions(idfParserMock);
    }

    @Test
    public void multipleNames() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(
                        withSuccess(
                                "<xml><experiment>" +
                                        "<name>Experiment name from XML 1</name>" +
                                        "<name>Experiment name from XML 2</name>" +
                                "</experiment></xml>",
                                MediaType.APPLICATION_XML));

        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo("Experiment name from XML 1");

    }

    @Test
    public void onEmptyNameUsesIdfAsFallback() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(withSuccess("<xml><experiment></experiment></xml>", MediaType.APPLICATION_XML));

        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo(E_FOOBAR_IDF_TITLE);
    }

    @Test
    public void onEmptyResponseUsesIdfAsFallback() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(withNoContent());
        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo(E_FOOBAR_IDF_TITLE);
    }

    @Test
    public void onServerErrorUsesIdfAsFallback() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(withServerError());
        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo(E_FOOBAR_IDF_TITLE);
    }

    @Test
    public void onNotFoundUsesIdfAsFallback() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThat(subject.fetchExperimentName(E_FOOBAR_ACCESSION)).isEqualTo(E_FOOBAR_IDF_TITLE);
    }

    @Test
    public void throwIfBothXmlAndIdfHaveNoName() {
        mockServer.expect(requestTo(MessageFormat.format(AE_URL_TEMPLATE, E_FOOBAR_ACCESSION)))
                .andRespond(withSuccess("<xml><experiment></experiment></xml>", MediaType.APPLICATION_XML));
        when(idfParserMock.parse(eq(E_FOOBAR_ACCESSION))).thenReturn(new IdfParserOutput("", "", Collections.emptyList(), 0, Collections.emptyList()));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> subject.fetchExperimentName(E_FOOBAR_ACCESSION));
    }

}
