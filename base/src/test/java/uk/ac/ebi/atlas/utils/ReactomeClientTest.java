package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
}