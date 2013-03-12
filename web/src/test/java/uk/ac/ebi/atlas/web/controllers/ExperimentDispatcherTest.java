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

package uk.ac.ebi.atlas.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentDispatcherTest {

    private static final String EXPERIMENT_URL = "http://x.y/z";

    private static final String REQUEST_PARAMETERS = "p1=1&p2=2";
    private static final String DOWNLOAD_URL = EXPERIMENT_URL + ".tsv?" + REQUEST_PARAMETERS;

    @Mock
    private HttpServletRequestWrapper httpServletRequestWrapperMock;

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Before
    public void initSubject() throws Exception {
        //given
        when(httpServletRequestWrapperMock.getRequest()).thenReturn(httpServletRequestMock);
        when(httpServletRequestWrapperMock.getRequestURI()).thenReturn(EXPERIMENT_URL);
        when(httpServletRequestMock.getQueryString()).thenReturn(REQUEST_PARAMETERS);
    }

    @Test
    public void buildDownloadUrl() {
        //when
        String downloadUrl = ExperimentDispatcher.buildDownloadURL(httpServletRequestWrapperMock);

        //then
        assertThat(downloadUrl, is(DOWNLOAD_URL));
    }

    @Test
    public void buildDownloadUrlWithoutQueryParameters() {
        //given
        given(httpServletRequestMock.getQueryString()).willReturn(null);

        //when
        String downloadUrl = ExperimentDispatcher.buildDownloadURL(httpServletRequestWrapperMock);

        //then
        assertThat(downloadUrl, is(EXPERIMENT_URL + ".tsv"));
    }}
