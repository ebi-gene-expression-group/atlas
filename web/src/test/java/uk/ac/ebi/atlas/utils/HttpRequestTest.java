/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestTest {

    private static final String URL = "url";
    private static final String VALUE = "value";
    private static final String NAME = "name";

    @Mock
    private org.apache.http.client.HttpClient httpClientMock;

    @Mock
    private NameValuePair nameValuePairMock;

    @Mock
    private HttpResponse responseMock;

    @Mock
    private StatusLine statusLineMock;

    @Mock
    private HttpEntity httpEntityMock;

    @Mock
    private InputStream inputStreamMock;

    private List<? extends NameValuePair> params;

    @Before
    public void setUp() throws Exception {
        params = Lists.newArrayList(nameValuePairMock);

        when(nameValuePairMock.getName()).thenReturn(NAME);
        when(nameValuePairMock.getValue()).thenReturn(VALUE);

        when(httpClientMock.execute(Matchers.<HttpUriRequest>anyObject())).thenReturn(responseMock);
        when(responseMock.getStatusLine()).thenReturn(statusLineMock);
        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(responseMock.getEntity()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContent()).thenReturn(inputStreamMock);
    }

    @Test
    public void testHttpPost() throws Exception {
        assertThat(HttpRequest.httpPost(httpClientMock, URL, params), is(inputStreamMock));

        verify(nameValuePairMock).getName();
        verify(nameValuePairMock).getValue();
        verify(httpClientMock).execute(any(HttpUriRequest.class));
        verify(responseMock).getStatusLine();
        verify(responseMock).getEntity();
    }

    @Test(expected = IOException.class)
    public void testError() throws Exception {
        when(statusLineMock.getStatusCode()).thenReturn(400);
        HttpRequest.httpPost(httpClientMock, URL, params);
    }
}