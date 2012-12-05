package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayExpressClientTestIT {

    private ArrayExpressClient subject;

    private final static String EXPERIMENT_ACC = "E-MTAB-513";

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        //ToDo: better use injection
        ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
        when(applicationProperties.getArrayExpressRestURL(EXPERIMENT_ACC)).thenReturn("http://www.ebi.ac.uk/arrayexpress/xml/v2/experiments?accession=E-MTAB-513");

        subject = new ArrayExpressClient(restTemplate, applicationProperties);

    }

    @Test
    public void testFetchExperimentName() throws Exception {
        String result = subject.fetchExperimentName(EXPERIMENT_ACC);
        assertThat(result, is("RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)"));
    }
}
