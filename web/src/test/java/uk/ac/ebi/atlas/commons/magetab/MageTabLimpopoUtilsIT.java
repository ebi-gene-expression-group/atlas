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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MageTabLimpopoUtilsIT {

    private static final String HOMO_SAPIENS = "Homo sapiens";
    private static final String PUBMED_ID = "22496456";

    private MAGETABInvestigation investigation;

    @Inject
    private MageTabLimpopoUtils subject;

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
        investigation.IDF.pubMedId.add(PUBMED_ID);
    }

    @Test
    public void testExtractPubMedIdsFromIDF() throws Exception {
        assertThat(subject.extractPubMedIdsFromIDF(investigation), contains(PUBMED_ID));
    }

    @Test
    public void testParseInvestigation() throws Exception {
        MAGETABInvestigation magetabInvestigation = subject.parseInvestigation("E-MTAB-513");
        assertThat(magetabInvestigation, is(not(nullValue())));
        assertThat(subject.extractPubMedIdsFromIDF(investigation), contains(PUBMED_ID));
    }
}