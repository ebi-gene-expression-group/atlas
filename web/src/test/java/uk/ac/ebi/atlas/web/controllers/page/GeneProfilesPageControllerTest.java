/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.commands.RequestContextBuilder;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesPageControllerTest {

    private static final String EXPERIMENT_URL = "http://x.y/z";

    private static final String REQUEST_PARAMETERS = "p1=1&p2=2";
    private static final String DOWNLOAD_URL = EXPERIMENT_URL + ".tsv?" + REQUEST_PARAMETERS;

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Mock
    private RankGeneProfilesCommand rankCommandMock;

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    @Mock
    private ExperimentsCache experimentCacheMock;

    @Mock
    private RequestContextBuilder requestContextBuilderMock;

    private GeneProfilesPageController subject;


    @Before
    public void initSubject() throws Exception {
        //given
        when(httpServletRequestMock.getRequestURI()).thenReturn(EXPERIMENT_URL);
        when(httpServletRequestMock.getQueryString()).thenReturn(REQUEST_PARAMETERS);

        FilterFactorsConverter filterFactorsConverterMock = mock(FilterFactorsConverter.class);

        subject = new GeneProfilesPageController(null, rankCommandMock, applicationPropertiesMock,
                experimentCacheMock, requestContextBuilderMock, filterFactorsConverterMock);
    }

    @Test
    public void buildDownloadUrl() {
        //when
        String downloadUrl = subject.buildDownloadURL(httpServletRequestMock);

        //then
        assertThat(downloadUrl, is(DOWNLOAD_URL));
    }

    @Test
    public void buildDownloadUrlWithoutQueryParameters() {
        //given
        given(httpServletRequestMock.getQueryString()).willReturn(null);

        //when
        String downloadUrl = subject.buildDownloadURL(httpServletRequestMock);

        //then
        assertThat(downloadUrl, is(EXPERIMENT_URL + ".tsv"));
    }
}
