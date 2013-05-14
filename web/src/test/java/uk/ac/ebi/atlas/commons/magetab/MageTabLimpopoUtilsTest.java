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

package uk.ac.ebi.atlas.commons.magetab;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MageTabLimpopoUtilsTest {

    private static final String HOMO_SAPIENS = "Homo sapiens";

    private MAGETABInvestigation investigation;

    @Before
    public void setUp() throws Exception {

        CharacteristicsAttribute characteristicsAttribute = new CharacteristicsAttribute();
        characteristicsAttribute.type = "ORGANISM";
        characteristicsAttribute.setAttributeValue(HOMO_SAPIENS);

        SourceNode sourceNode = new SourceNode();
        sourceNode.setNodeName("nodeName");
        sourceNode.characteristics.add(characteristicsAttribute);

        investigation = new MAGETABInvestigation();
        investigation.SDRF.addNode(sourceNode);
    }

    @Test
    public void testExtractSpeciesFromSDRF() throws Exception {
        assertThat(MageTabLimpopoUtils.extractSpeciesFromSDRF(investigation), contains(HOMO_SAPIENS));
    }

    @Test
    public void testParseInvestigation() throws Exception {
        MAGETABInvestigation magetabInvestigation = MageTabLimpopoUtils.parseInvestigation("E-MTAB-513", "/magetab/{0}/{0}.idf.txt", "http://www.ebi.ac.uk/arrayexpress/files/{0}/{0}.idf.txt");
        assertThat(magetabInvestigation, is(not(nullValue())));
        assertThat(MageTabLimpopoUtils.extractSpeciesFromSDRF(magetabInvestigation), contains(HOMO_SAPIENS));
    }
}