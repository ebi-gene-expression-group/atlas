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

package uk.ac.ebi.atlas.experimentloader.experimentdesign.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BioontologyClientTest {

    private static final String XPATH_EXPRESSION = "AN_XPATH_EXPRESSION";
    private static final String TERM = "A_TERM";

    @Mock
    private RestTemplate restTemplateMock;

    private BioontologyClient subject;

    @Before
    public void setUp() throws Exception {
        subject = new BioontologyClient(restTemplateMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidShouldThrowExceptionWhenResponseIsEmpty() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn("");

        subject.isValid(TERM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidShouldThrowExceptionWhenResponseIsNull() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString())).willReturn(null);

        subject.isValid(TERM);
    }

    @Test
    public void isValidShouldReturnFalse() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString()))
                .willReturn("{ontologyHitBean:{numHits:0}}");

        assertThat(subject.isValid(TERM), is(false));
    }

    @Test
    public void isValidShouldReturnTrue() {
        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyString()))
                .willReturn("{ontologyHitBean:{numHits:1}}");

        boolean valid = subject.isValid(TERM);

        ArgumentCaptor<String> endPointUrlCaptor = ArgumentCaptor.forClass(String.class);
        verify(restTemplateMock).getForObject(endPointUrlCaptor.capture(), eq(String.class), eq(TERM));

        assertThat(valid, is(true));
    }

}
