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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NamedSdrfNodeTest {
    @Before
    public void setUp() throws Exception {

    }

    //equals should depend on node name only,
    //don't know if this is correct. I defined this constraint on the basis of the original -messier- implementation
    //that used map entries to decorate limpopo nodes (rather then objects)
    @Test
    public void equalsShouldDependOnNodeNameOnly() throws Exception {

        HybridizationNode hybridizationNode1 = new HybridizationNode();
        hybridizationNode1.setNodeName("hybridizationNode1");
        HybridizationNode hybridizationNode2 = new HybridizationNode();

        NamedSdrfNode namedSdrfNode1 = new NamedSdrfNode("name1", hybridizationNode1);
        NamedSdrfNode namedSdrfNode2 = new NamedSdrfNode("name1", hybridizationNode2);

        assertThat(namedSdrfNode1, is(namedSdrfNode2));

    }

}
