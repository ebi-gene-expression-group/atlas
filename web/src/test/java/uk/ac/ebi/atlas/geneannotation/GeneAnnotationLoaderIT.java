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

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneannotation.biomart.BioMartGeneNameStream;

import java.io.File;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneAnnotationLoaderIT {

    private static final String HOMO_SAPIENS_DATASET = "hsapiens_gene_ensembl";

    private GeneAnnotationLoader subject;

    private static String[] gene1 = {"ens1", "g1"};
    private static String[] gene2 = {"ens2", "g2"};

    private ObjectInputStream<String[]> inputStream;

    private AnnotationEnvironment annotationEnvironment;

    private final static String TEST_ENV_PATH = "test-env";


    @Before
    public void initializeSubject() {
        inputStream = mock(ObjectInputStream.class);

        when(inputStream.readNext()).thenReturn(gene1).thenReturn(gene2).thenReturn(null);

        BioMartGeneNameStream.Builder builder = mock(BioMartGeneNameStream.Builder.class);
        when(builder.create(HOMO_SAPIENS_DATASET)).thenReturn(inputStream);

        annotationEnvironment = new AnnotationEnvironment(TEST_ENV_PATH);
        annotationEnvironment.setup();


        subject = new GeneAnnotationLoader(annotationEnvironment, builder);

    }

    @Test
    public void testLoadGeneNames() throws Exception {

        //given
        subject.loadGeneNames(HOMO_SAPIENS_DATASET);
        ConcurrentMap<String,String> map = annotationEnvironment.geneNames();

        //then
        assertThat(map.size(), is(2));
        assertThat(map.get(gene1[0]), is(gene1[1]));
        assertThat(map.get(gene2[0]), is(gene2[1]));
    }

    @After
    public void cleanup() throws Exception {
        annotationEnvironment.close();
        FileUtils.deleteDirectory(new File(TEST_ENV_PATH));

    }


}
