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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GeneAnnotationLoaderIT {

    private static final String HOMO_SAPIENS_DATASET = "hsapiens_gene_ensembl";

    @Inject
    private GeneAnnotationLoader subject;

    @Inject
    private AnnotationEnvironment annotationEnvironment;

    @Before
    public void initializeSubject() {
    }

    @Test
    public void testLoadGeneNames() throws Exception {

        //given
        subject.loadGeneNames(HOMO_SAPIENS_DATASET);
        ConcurrentMap<String, String> map = annotationEnvironment.geneNames();

        //then
        assertThat(map.size(), is(greaterThan(20000)));
        assertThat(map.get("ENSG00000211855"), is("TRAJ34"));
    }


}
