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

package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {

    private static final String ARRAY_DESIGN_ACCESSIONS = "arrayDesignAccessions";
    private static final String PUBMEDID = "PUBMEDID";

    @Mock
    private Contrast contrastMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperiment subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperiment(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "accession", new Date(), Sets.newHashSet(contrastMock),
                "description", false, Sets.newHashSet("species"), "kingdom", "ensembl", Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN_ACCESSIONS)),
                false, Sets.newHashSet(PUBMEDID), experimentDesignMock);
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAY_DESIGN_ACCESSIONS));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat(subject.getPubMedIds(), contains(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() throws Exception {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }
}