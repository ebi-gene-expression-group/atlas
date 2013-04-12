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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MicroarrayProfileTest {

    public static final String DESIGN_ELEMENT_NAME = "designElementName";
    public static final String GENE_ID = "geneId";

    MicroarrayProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayProfile(GENE_ID, DESIGN_ELEMENT_NAME);
    }

    @Test
    public void testGetDesignElementName() throws Exception {
        assertThat(subject.getDesignElementName(), is(DESIGN_ELEMENT_NAME));
    }
}