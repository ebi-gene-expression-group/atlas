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

package uk.ac.ebi.atlas.geneannotation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneNamesProviderTest {

    private GeneNamesProvider subject;

    @Mock
    private AnnotationEnvironment annotationEnvironmentMock;

    private ConcurrentMap<String, String> map = new ConcurrentHashMap();

    @Before
    public void initializeSubject() {
        map.put("e1", "g1");
        when(annotationEnvironmentMock.geneNames()).thenReturn(map);
        subject = new GeneNamesProvider(annotationEnvironmentMock);
    }

    @Test
    public void testGetGeneName() throws Exception {
        assertThat(subject.getGeneName("e1"), is("g1"));

        assertThat(subject.getGeneName("not there"), is("not there"));
    }


}
