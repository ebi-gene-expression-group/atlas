package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReactomeClientTest {

    private static Set<String> SET_OF_45_STABLE_IDS = ImmutableSet.of(
            "R-HSA-15869", "R-HSA-37001", "R-HSA-43124", "R-HSA-48888", "R-HSA-49155",
            "R-HSA-49189", "R-HSA-49191", "R-HSA-49291", "R-HSA-49335", "R-HSA-49459",
            "R-HSA-49489", "R-HSA-49491", "R-HSA-49493", "R-HSA-49495", "R-HSA-49699",
            "R-HSA-49701", "R-HSA-49725", "R-HSA-49741", "R-HSA-49743", "R-HSA-49745",
            "R-HSA-49859", "R-HSA-49865", "R-HSA-49925", "R-HSA-49927", "R-HSA-49929",
            "R-HSA-50099", "R-HSA-50119", "R-HSA-50171", "R-HSA-50270", "R-HSA-50320",
            "R-HSA-50498", "R-HSA-50500", "R-HSA-50508", "R-HSA-50514", "R-HSA-50516",
            "R-HSA-50518", "R-HSA-50522", "R-HSA-50662", "R-HSA-50690", "R-HSA-50757",
            "R-HSA-50825", "R-HSA-50845", "R-HSA-50847", "R-HSA-50849", "R-HSA-50851");

    @Mock
    RestTemplate restTemplateMock;

    ReactomeClient subject;

    @Before
    public void setUp() throws Exception {
        subject = new ReactomeClient(restTemplateMock);
    }

    @Test
    public void returnEmptyForAnEmptyCollection() {
        assertThat(subject.getPathwayNames(Collections.emptySet()).entrySet(), hasSize(0));
    }

    @Test
    public void skipUnfoundStableIds() {
        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), anyString(), eq(String.class)))
                .thenReturn("[" +
                        "{\"stId\":\"R-FOO-111\", \"displayName\":\"Bar signal\"}," +
                        "{\"stId\":\"R-FOO-333\", \"displayName\":\"Bar regulator\"}]");

        assertThat(
                subject.getPathwayNames(ImmutableSet.of("R-FOO-333", "R-FOO-111", "R-FOO-666")).entrySet(),
                hasSize(2));
    }

    @Test
    public void returnEmptyIfSomethingGoesWrong() throws Exception {
        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), anyString(), eq(String.class)))
                .thenThrow(new RestClientException("Timeout!"));

        assertThat(subject.getPathwayNames(ImmutableSet.of("R-FOO-123")).entrySet(), hasSize(0));
    }

    @Test
    public void returnEmptyIfInvalidResponse() throws Exception {
        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), anyString(), eq(String.class)))
                .thenReturn("[{\"stId\":\"R-FOO-111\", \"displayName\":\"Bar signal\"}");

        assertThat(subject.getPathwayNames(ImmutableSet.of("R-FOO-111")).entrySet(), hasSize(0));
    }

    @Test
    public void returnEmptyIfEmptyResponse() throws Exception {
        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), anyString(), eq(String.class))).thenReturn("");

        assertThat(subject.getPathwayNames(ImmutableSet.of("R-FOO-123")).entrySet(), hasSize(0));
    }

    @Test
    public void fetchPathways() throws Exception {
        ImmutableSet<String> pathwayIds = ImmutableSet.of("R-FOO-111", "R-FOO-222", "R-FOO-333");

        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), eq(pathwayIds.stream().collect(Collectors.joining(","))), eq(String.class)))
                .thenReturn("[" +
                        "{\"stId\":\"R-FOO-111\", \"displayName\":\"Bar signal\"}," +
                        "{\"stId\":\"R-FOO-222\", \"displayName\":\"Bar receptor\"}," +
                        "{\"stId\":\"R-FOO-333\", \"displayName\":\"Bar regulator\"}]"
                );

        Map<String, String> result = subject.getPathwayNames(pathwayIds);

        assertThat(result.keySet(), hasSize(3));
        assertThat(result, hasEntry(is("R-FOO-111"), is("Bar signal")));
        assertThat(result, hasEntry(is("R-FOO-222"), is("Bar receptor")));
        assertThat(result, hasEntry(is("R-FOO-333"), is("Bar regulator")));
    }

    @Test
    public void makesRequestsInBatchesOfQueryMaxSize() throws Exception {
        when(restTemplateMock.postForObject(eq(ReactomeClient.URL), anyString(), eq(String.class))).thenReturn("");
        subject.getPathwayNames(SET_OF_45_STABLE_IDS);

        int numberOfCalls = SET_OF_45_STABLE_IDS.size() / ReactomeClient.QUERY_MAX_SIZE + 1;

        ArgumentCaptor<String> postData = ArgumentCaptor.forClass(String.class);
        verify(restTemplateMock, times(numberOfCalls))
                .postForObject(eq(ReactomeClient.URL), postData.capture(), eq(String.class));

        for (int i = 0 ; i < numberOfCalls - 1 ; i++) {
            assertThat(postData.getAllValues().get(i).split(",").length, is(ReactomeClient.QUERY_MAX_SIZE));
        }
        assertThat(
                postData.getAllValues().get(numberOfCalls - 1).split(",").length,
                is(SET_OF_45_STABLE_IDS.size() % ReactomeClient.QUERY_MAX_SIZE));
    }
}