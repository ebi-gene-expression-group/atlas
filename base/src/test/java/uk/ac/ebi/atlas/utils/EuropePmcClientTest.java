package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EuropePmcClientTest {
    private static final String URL = "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query={0}&format=json";

    @Mock
    private RestTemplate restTemplateMock;

    private EuropePmcClient subject;

    @Before
    public void setUp() throws Exception {
        subject = new EuropePmcClient(restTemplateMock);
    }

    @Test
    public void returnsNoResultsIfEuropePmcIsUnavailable() {
        when(restTemplateMock.getForEntity(MessageFormat.format(URL, anyString()), eq(String.class)))
                .thenThrow(RestClientException.class);

        assertThat(subject.getPublicationByIdentifier("foo")).isEmpty();
    }

}
