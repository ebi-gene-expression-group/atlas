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
import com.sleepycat.collections.TransactionRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.berkeley.MapTransactionWorker;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.geneannotation.AnnotationMappingExtractor;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DesignElementGeneMappingLoaderTest {

    public static final String ARRAY_DESIGN_ACCESSION = "arrayDesignAccession";
    private DesignElementGeneMappingLoader subject;

    @Mock
    private AnnotationEnvironment annotationEnvironmentMock;


    @Mock
    private AnnotationMappingExtractor annotationMappingExtractorMock;

    @Mock
    private TransactionRunner transactionRunnerMock;

    @Mock
    private ConcurrentMap<String, String> destinationMapMock;

    @Before
    public void setUp() throws Exception {

        Map<String, String> annotations = Maps.newHashMap();
        annotations.put("de1", "gene1");
        annotations.put("de2", "gene2");

        when(annotationMappingExtractorMock.extractAnnotationsMap(any(String.class), any(String.class))).thenReturn(annotations);
        when(annotationEnvironmentMock.getTransactionRunner()).thenReturn(transactionRunnerMock);
        when(annotationEnvironmentMock.geneDesignElementsToGeneNames()).thenReturn(destinationMapMock);


        subject = new DesignElementGeneMappingLoader(annotationEnvironmentMock, annotationMappingExtractorMock);
    }

    @Test
    public void testLoadMappingsForSingleDesignAccession() throws Exception {
        subject.loadMappings(ARRAY_DESIGN_ACCESSION);
        verify(transactionRunnerMock).run(any(MapTransactionWorker.class));
    }
}