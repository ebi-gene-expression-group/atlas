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

package uk.ac.ebi.atlas.expdesign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MicroArrayExpDesignMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_1066 = "E-MTAB-1066";

    @Inject
    private MicroArrayExpDesignMageTabParser subject;

    @Test
    public void testExtractAssays1066() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);
        assertThat(subject.extractAssays(), containsInAnyOrder("C1", "C2", "C3", "K1", "K2", "K3", "WT1", "WT2", "WT3"));
    }

    @Test
    public void testGetHybridizationNodeForAssay1066() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);
        assertThat(subject.getHybridizationNodeForAssay("C1"), is(not(nullValue())));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetHybridizationNodeForNonExistingAssay() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);
        subject.getHybridizationNodeForAssay("C14");
    }

    @Test
    public void testFindArrayForHybridizationNode1066() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);
        HybridizationNode scanNode = subject.getHybridizationNodeForAssay("C1");
        assertThat(subject.findArrayForHybridizationNode(scanNode), is("A-AFFY-35"));
    }

    @Test
    public void testExtractFactors1066() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);
        assertThat(subject.extractFactors(), containsInAnyOrder("GENOTYPE"));
    }

    @Test
    public void testFindFactorValueForHybridizationNodeAssay1066() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_1066);

        // C1	A-AFFY-35	3rd instar larva	cycC mutant,w1118; +; cycCY5	Drosophila melanogaster
        HybridizationNode scanNode = subject.getHybridizationNodeForAssay("C1");
        assertThat(subject.findFactorValueForHybridizationNode(scanNode, "GENOTYPE"), hasItem("cycC mutant"));

        // WT3	A-AFFY-35	3rd instar larva	wild type	Drosophila melanogaster	Oregon R
        scanNode = subject.getHybridizationNodeForAssay("WT3");
        assertThat(subject.findFactorValueForHybridizationNode(scanNode, "GENOTYPE"), hasItem("wild_type"));
    }
}