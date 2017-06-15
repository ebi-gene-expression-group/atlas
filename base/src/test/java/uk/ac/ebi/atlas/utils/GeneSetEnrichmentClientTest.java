package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class GeneSetEnrichmentClientTest {

    GeneSetEnrichmentClient subject = new GeneSetEnrichmentClient(Mockito.mock(RestTemplate.class));

    @Test
    public void testFormatResponse() {

        assertThat(GeneSetEnrichmentClient.isSuccess(subject.formatResponse(ImmutableList.<String[]>of())),
                Matchers.is(false));

        assertThat(GeneSetEnrichmentClient.isSuccess(subject.formatResponse(
                ImmutableList.of(
                        "I am a process and I say hello ".split("\t")
                ))),
                Matchers.is(false));

        assertThat(GeneSetEnrichmentClient.isSuccess(subject.formatResponse(
                ImmutableList.of(
                        "Header which is not the expected one".split("\t"),
                        "Result line".split("\t")
                ))),
                Matchers.is(false));
        assertThat(GeneSetEnrichmentClient.isSuccess(subject.formatResponse(
                ImmutableList.of(
                        "EXPERIMENT\tCOMPARISON_ID\tP-VALUE\tOBSERVED\tEXPECTED\tADJUSTED P-VALUE\tEFFECT SIZE\tCOMPARISON_TITLE\tEXPERIMENT_URL".split("\t"),
                        "E-GEOD-7631\tg26_g24\t2.67507404529931e-07\t6\t0.35\t0.000403133658626605\t17.14\thttp://www.ebi.ac.uk/gxa/experiments/E-GEOD-7631?queryFactorValues=g26_g24&_specific=on".split("\t")
                ))),
                Matchers.is(true));
    }


}