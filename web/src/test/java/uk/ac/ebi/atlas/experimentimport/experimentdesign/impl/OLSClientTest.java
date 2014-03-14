package uk.ac.ebi.atlas.experimentimport.experimentdesign.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class OLSClientTest {

    private static final String TERM = "degree celsius";
    private static final String TERM_WRONG = "degree b";

    private static final String RESPONSE = "<ajax-response>\n" +
            "<response>\n" +
            "<item>\n" +
            "<name>degree</name>\n" +
            "<value>UO:0000185</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>arc degree</name>\n" +
            "<value>UO:0000185</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>degree (angle)</name>\n" +
            "<value>UO:0000185</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>degree celsius</name>\n" +
            "<value>UO:0000027</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>degree centigrade</name>\n" +
            "<value>UO:0000027</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>degree fahrenheit</name>\n" +
            "<value>UO:0000195</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>\n" +
            "Bipolar affective disorder, manic, unspecified degree\n" +
            "</name>\n" +
            "<value>EFO:0000289</value>\n" +
            "</item>\n" +
            "<item>\n" +
            "<name>\n" +
            "Bipolar affective disorder, mixed, unspecified degree\n" +
            "</name>\n" +
            "<value>EFO:0000289</value>\n" +
            "</item>\n" +
            "</response>\n" +
            "</ajax-response>";

    private OLSClient subject;

    @Mock
    private RestTemplate restTemplateMock;


    @Before
    public void setUp() throws Exception {
        subject = new OLSClient(restTemplateMock);
    }

    @Test
    public void isValidShouldReturnTrue() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(RESPONSE);

        boolean valid = subject.isValid(TERM);

        assertThat(valid, is(true));
    }

    @Test
    public void isValidShouldReturnFalse() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(RESPONSE);

        boolean valid = subject.isValid(TERM_WRONG);

        assertThat(valid, is(false));
    }

    @Test
    public void isValidShouldThrowExceptionWhenResponseIsEmpty() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn("");

        boolean valid = subject.isValid(TERM);

        assertThat(valid, is(false));
    }

    @Test
    public void isValidShourldReturnFalseOntologyTermIsEmpty() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(RESPONSE);

        boolean valid = subject.isValid("");

        assertThat(valid, is(false));
    }

    @Test
    public void isValidShourldReturnFalseOntologyTermIsNull() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(RESPONSE);

        boolean valid = subject.isValid(null);

        assertThat(valid, is(false));
    }

    @Test
    public void isValidShouldThrowExceptionWhenResponseIsNull() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(null);

        boolean valid = subject.isValid(TERM);

        assertThat(valid, is(false));
    }

}
