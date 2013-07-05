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

package uk.ac.ebi.atlas.model.differential;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class DifferentialExperimentConfigurationTest {

    private static final String CONTRAST_ID = "contrastId";
    private static final String REFERENCE_ASSAY_GROUP = "reference_assay_group";
    private static final String TEST_ASSAY_GROUP = "test_assay_group";
    private static final String NAME = "name";
    private static final String XML_CONTENT =
            "<configuration>" +
                    "    <analytics>" +
                    "        <assay_groups>" +
                    "            <assay_group id=\"" + REFERENCE_ASSAY_GROUP + "\">" +
                    "                <assay>" + REFERENCE_ASSAY_GROUP + "</assay>" +
                    "            </assay_group>" +
                    "            <assay_group id=\"" + TEST_ASSAY_GROUP + "\">" +
                    "                <assay>" + TEST_ASSAY_GROUP + "</assay>" +
                    "            </assay_group>" +
                    "        </assay_groups>" +
                    "        <contrasts>" +
                    "            <contrast id=\"" + CONTRAST_ID + "\">" +
                    "                <name>" + NAME + "</name>" +
                    "                <reference_assay_group>" + REFERENCE_ASSAY_GROUP + "</reference_assay_group>" +
                    "                <test_assay_group>" + TEST_ASSAY_GROUP + "</test_assay_group>" +
                    "            </contrast>" +
                    "        </contrasts>" +
                    "    </analytics>" +
                    "</configuration>";

    DifferentialExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.load(new ByteArrayInputStream(XML_CONTENT.getBytes()));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(XML_CONTENT.getBytes()));

        subject = new DifferentialExperimentConfiguration(xmlConfiguration, doc);
    }

    @Test
    public void testGetContrasts() throws Exception {
        Set<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts.size(), is(1));
        Contrast contrast = contrasts.iterator().next();
        assertThat(contrast.getId(), is(CONTRAST_ID));
        assertThat(contrast.getDisplayName(), is(NAME));
        assertThat(contrast.getReferenceAssayGroup(), hasItem(REFERENCE_ASSAY_GROUP));
        assertThat(contrast.getTestAssayGroup(), hasItem(TEST_ASSAY_GROUP));
    }
}