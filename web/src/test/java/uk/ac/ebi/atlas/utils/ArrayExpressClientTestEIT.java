package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayExpressClientTestEIT {

    private ArrayExpressClient subject;

    private final static String EXPERIMENT_ACC = "E-MTAB-513";

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        //ToDo: better use injection
        ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
        when(applicationProperties.getArrayExpressRestURL(anyString())).thenAnswer(arrayExpressUrl());

        subject = new ArrayExpressClient(restTemplate, applicationProperties);

    }

    private static Answer<String> arrayExpressUrl() {
        return new Answer<String>() {
            public static final String AE2_URL = "http://www.ebi.ac.uk/arrayexpress/xml/v2/experiments?accession=";

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return AE2_URL + invocationOnMock.getArguments()[0];
            }
        };
    }


    @Test
    public void testFetchExperimentName() {
        String result = subject.fetchExperimentName(EXPERIMENT_ACC);
        assertThat(result, is("RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)"));
    }

}
