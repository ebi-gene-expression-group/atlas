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

import org.apache.commons.lang3.tuple.Pair;
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
public class TwoColourExpDesignMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_GEOD_43049 = "E-GEOD-43049";

    @Inject
    private TwoColourExpDesignMageTabParser subject;

    @Test
    public void testExtractAssays43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        assertThat(subject.extractAssays(), containsInAnyOrder(
                Pair.of("GSM1055612.Cy5", 1), Pair.of("GSM1055613.Cy3", 2),
                Pair.of("GSM1055613.Cy5", 1), Pair.of("GSM1055617.Cy3", 2),
                Pair.of("GSM1055617.Cy5", 1), Pair.of("GSM1055615.Cy3", 2),
                Pair.of("GSM1055615.Cy5", 1), Pair.of("GSM1055614.Cy3", 2),
                Pair.of("GSM1055614.Cy5", 1), Pair.of("GSM1055616.Cy3", 2),
                Pair.of("GSM1055616.Cy5", 1), Pair.of("GSM1055612.Cy3", 2)));
    }

    @Test
    public void testExtractFactors43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        assertThat(subject.extractFactors(), containsInAnyOrder("CULTURE CONDITION"));
    }

    @Test
    public void testGetHybridizationNodeForAssay43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        assertThat(subject.getHybridizationNodeForAssay(Pair.of("GSM1055612.Cy5", 1)), is(not(nullValue())));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetHybridizationNodeForUnknownAssay43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        subject.getHybridizationNodeForAssay(Pair.of("GSM123456.Cy5", 1));
    }

    @Test
    public void testFindFactorValueForAssay43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055612.Cy5", 1), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055612.Cy3", 2), "CULTURE CONDITION"), is("Conventional"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055613.Cy5", 1), "CULTURE CONDITION"), is("Conventional"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055613.Cy3", 2), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055614.Cy5", 1), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055614.Cy3", 2), "CULTURE CONDITION"), is("Conventional"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055615.Cy5", 1), "CULTURE CONDITION"), is("Conventional"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055615.Cy3", 2), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055616.Cy5", 1), "CULTURE CONDITION"), is("Conventional"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055616.Cy3", 2), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055617.Cy5", 1), "CULTURE CONDITION"), is("Apical anaerobic"));
        assertThat(subject.findFactorValueForAssay(Pair.of("GSM1055617.Cy3", 2), "CULTURE CONDITION"), is("Conventional"));
    }

    @Test(expected = IllegalStateException.class)
    public void testFindFactorValueForUnknownAssay43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        subject.findFactorValueForAssay(Pair.of("GSM123456.Cy5", 1), "CULTURE CONDITION");
    }

    @Test
    public void testFindArrayForHybridizationNode43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        HybridizationNode hybridizationNode = subject.getHybridizationNodeForAssay(Pair.of("GSM1055612.Cy5", 1));
        assertThat(subject.findArrayForHybridizationNode(hybridizationNode), is("A-AGIL-28"));
    }

    @Test
    public void testFindCharacteristicValueForAssay43049() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_43049);
        // GSM1055612.Cy5	A-AGIL-28	Homo sapiens	Caco-2	Apical anaerobic	Apical anaerobic
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "culture condition"), hasItem("Apical anaerobic"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "cell line"), hasItem("Caco-2"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "Organism"), hasItem("Homo sapiens"));
        // GSM1055617.Cy3	A-AGIL-28	Homo sapiens	Caco-2	Conventional	Conventional
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "culture condition"), hasItem("Conventional"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "cell line"), hasItem("Caco-2"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "Organism"), hasItem("Homo sapiens"));
    }
}