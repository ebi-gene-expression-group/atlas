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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.GeneNamesImportCommand;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationLoaderControllerTest {

    public static final String ARRAY_DESIGN = "arrayDesign";
    public static final String SPECIES = "species";
    public static final String UPDATED = "Updated";

    public static final String ARRAY_DESIGN_TYPE = "ensembl";
    public static final String BIOENTITY_TYPE = "gene";

    AnnotationLoaderController subject;

    @Mock
    private ApplicationProperties applicationPropertiesMock;

    @Mock
    private GeneNamesImportCommand geneNamesImportCommandMock;

    @Mock
    private DesignElementMappingLoader designElementLoaderMock;

    @Before
    public void setUp() throws Exception {
        subject = new AnnotationLoaderController(applicationPropertiesMock, geneNamesImportCommandMock, designElementLoaderMock);
    }

    @Test
    public void testUpdateAnnotations() throws Exception {
        Set<String> set = Sets.newHashSet(SPECIES);

        String result = subject.updateAnnotations(set);
        assertThat(result, is(UPDATED));
        verify(geneNamesImportCommandMock).loadGeneNames(Mockito.eq(set));
    }

    @Test
    public void testUpdateAnnotationsForAllLoadedExperiments() throws Exception {
        Set<String> set = Sets.newHashSet(SPECIES);
        when(applicationPropertiesMock.getBiomartDatasetIdentifiers()).thenReturn(set);

        String result = subject.updateAnnotationsForAllLoadedExperiments();
        assertThat(result, is(UPDATED));
        verify(geneNamesImportCommandMock).loadGeneNames(Mockito.eq(set));
    }

    @Test
    public void testUpdateDesignElements() throws Exception {

        String result = subject.updateDesignElements(ARRAY_DESIGN, ARRAY_DESIGN_TYPE);
        assertThat(result, is(UPDATED));
        verify(designElementLoaderMock).loadMappings(ARRAY_DESIGN, ARRAY_DESIGN_TYPE);
    }

    @Test
    public void testUpdateAllArrayDesigns() throws Exception {


        String result = subject.updateDesignElements(ARRAY_DESIGN, ARRAY_DESIGN_TYPE);
        assertThat(result, is(UPDATED));
        verify(designElementLoaderMock).loadMappings(ARRAY_DESIGN, ARRAY_DESIGN_TYPE);
    }
}