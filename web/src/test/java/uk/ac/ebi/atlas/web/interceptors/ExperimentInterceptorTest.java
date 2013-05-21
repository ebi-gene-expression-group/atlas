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

package uk.ac.ebi.atlas.web.interceptors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentInterceptorTest {

    private ExperimentInterceptor subject;

    private HttpServletRequestWrapper requestWrapper;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private StopWatch stopWatch;

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentInterceptor();
        requestWrapper = new HttpServletRequestWrapper(requestMock);
        when(requestMock.getAttribute(ExperimentInterceptor.STOP_WATCH)).thenReturn(stopWatch);
    }

    @Test
    public void testPreHandle() throws Exception {
        assertThat(subject.preHandle(requestWrapper, null, null), is(true));

        StopWatch stopWatch = (StopWatch) requestWrapper.getAttribute(ExperimentInterceptor.STOP_WATCH);
        assertThat(stopWatch, is(not(nullValue())));
    }

    @Test
    public void testPostHandle() throws Exception {
        subject.postHandle(requestMock, null, null, null);

        verify(requestMock).getAttribute(ExperimentInterceptor.STOP_WATCH);
        verify(stopWatch).stop();
    }
}