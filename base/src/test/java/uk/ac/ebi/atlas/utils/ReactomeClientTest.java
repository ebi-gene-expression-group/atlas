package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReactomeClientTest {

    @Mock
    RestTemplate restTemplateMock;

    ReactomeClient subject;

    @Test
    public void returnEmptyStringIfRestTemplateThrows() throws Exception {
        when(restTemplateMock.getForObject(anyString(), any())).thenThrow(new RestClientException("Timeout!"));
        subject = new ReactomeClient(restTemplateMock);

        assertThat(subject.fetchPathwayNameFailSafe("foobar"), is(Optional.empty()));
    }

    @Test(expected = RuntimeException.class)
    public void throwIfNumberOfNamesDoesNotMatchNumberOfIds() throws Exception {
        when(restTemplateMock.postForObject(anyString(), anyString(), any()))
                .thenReturn("[{\"stId\":\"R-FOO-123\", \"displayName\":\"Bar signal\"},{\"stId\":\"R-FOO-321\"}]");
        subject = new ReactomeClient(restTemplateMock);
        subject.fetchPathwayNames(ImmutableSet.of("R-FOO-123", "R-FOO-321"));
    }

    @Test
    public void returnEmptyCollectionIfSomethingGoesWrong() throws Exception {
        when(restTemplateMock.postForObject(anyString(), anyString(), any())).thenThrow(new RestClientException("Timeout!"));
        subject = new ReactomeClient(restTemplateMock);

        assertThat(subject.fetchPathwayNames(ImmutableSet.of("R-FOO-123")).isEmpty(), is(true));
    }

    @Test
    public void returnEmptyCollectionIfBadResponse() throws Exception {
        when(restTemplateMock.postForObject(anyString(), anyString(), any())).thenReturn("[{\"foo\":\"bar\"}]");
        subject = new ReactomeClient(restTemplateMock);

        assertThat(subject.fetchPathwayNames(ImmutableSet.of("R-FOO-123")).isEmpty(), is(true));
    }

    @Test
    public void returnEmptyCollectionIfInvalidResponse() throws Exception {
        when(restTemplateMock.postForObject(anyString(), anyString(), any())).thenReturn("[{foobar]");
        subject = new ReactomeClient(restTemplateMock);

        assertThat(subject.fetchPathwayNames(ImmutableSet.of("R-FOO-123")).isEmpty(), is(true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fetchPathways() throws Exception {
        ImmutableSet<String> pathwayIds = ImmutableSet.of("R-FOO-111", "R-FOO-222", "R-FOO-333");

        when(restTemplateMock.postForObject(anyString(), eq(pathwayIds.stream().collect(Collectors.joining(","))), any()))
                .thenReturn("[" +
                        "{\"stId\":\"R-FOO-111\", \"displayName\":\"Bar signal\"}," +
                        "{\"stId\":\"R-FOO-222\", \"displayName\":\"Bar receptor\"}," +
                        "{\"stId\":\"R-FOO-333\", \"displayName\":\"Bar regulator\"}]"
                );
        subject = new ReactomeClient(restTemplateMock);
        Collection<Pair<String, String>> result = subject.fetchPathwayNames(pathwayIds);

        assertThat(ImmutableList.copyOf(result), hasSize(3));
        assertThat(result, containsInAnyOrder(Pair.of("R-FOO-111", "Bar signal"), Pair.of("R-FOO-222", "Bar receptor"), Pair.of("R-FOO-333", "Bar regulator")));
    }
}