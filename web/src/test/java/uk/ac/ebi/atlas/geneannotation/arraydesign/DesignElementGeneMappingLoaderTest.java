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

package uk.ac.ebi.atlas.geneannotation.arraydesign;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sleepycat.collections.TransactionRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;

import java.util.concurrent.ConcurrentMap;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DesignElementGeneMappingLoaderTest {

    public static final String ARRAY_DESIGN_ACCESSION = "arrayDesignAccession";
    private DesignElementGeneMappingLoader subject;

    @Mock
    private AnnotationEnvironment annotationEnvironmentMock;

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private TransactionRunner transactionRunnerMock;

    private ConcurrentMap<String, String> map = Maps.newConcurrentMap();

    @Before
    public void setUp() throws Exception {
        subject = new DesignElementGeneMappingLoader(annotationEnvironmentMock, restTemplateMock);

        when(restTemplateMock.getForObject(anyString(), eq(String.class), any())).thenReturn("{\"exportText\":\"{209575_at:ENSG00000243646,1553147_at:ENSG00000164188}\"}");
        when(annotationEnvironmentMock.getTransactionRunner()).thenReturn(transactionRunnerMock);
        when(annotationEnvironmentMock.geneDesignElementsToGeneNames()).thenReturn(map);

    }

    @Test
    public void testLoadMappingsForSingleDesignAccession() throws Exception {
        subject.loadMappings(ARRAY_DESIGN_ACCESSION);
    }

    @Test
    public void testLoadMappingsForCollectionOfDesignAccessions() throws Exception {
        subject.loadMappings(Sets.newHashSet(ARRAY_DESIGN_ACCESSION));
    }

    @Test
    public void testLoadMappings() throws Exception {
        subject.loadMappings(ARRAY_DESIGN_ACCESSION, false);
        subject.loadMappings(ARRAY_DESIGN_ACCESSION, true);
    }
}