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
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TwoColourMageTabLimpopoExpDesignParserTest {

    static final String EXPERIMENT_ACCESSION_E_GEOD_43049 = "E-GEOD-43049";

    TwoColourMageTabLimpopoExpDesignParser subject;

    @Before
    public void setUp() throws Exception {

        subject = new TwoColourMageTabLimpopoExpDesignParser();
        subject.setIdfUrlTemplate("http://www.ebi.ac.uk/arrayexpress/files/{0}/{0}.idf.txt");
        subject.setIdfPathTemplate("/magetab/{0}/{0}.idf.txt");

    }

    @Test
    public void testExtractAssays43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
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
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
        assertThat(subject.extractFactors(), containsInAnyOrder("CULTURE CONDITION"));
    }

    @Test
    public void testGetHybridizationNodeForAssay43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
        assertThat(subject.getHybridizationNodeForAssay(Pair.of("bla", 1)), is(nullValue()));
        assertThat(subject.getHybridizationNodeForAssay(Pair.of("GSM1055612.Cy5", 1)), is(not(nullValue())));
    }

    @Test
    public void testFindFactorValueForAssay43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
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

    @Test
    public void testFindArrayForHybridizationNode43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
        HybridizationNode hybridizationNode = subject.getHybridizationNodeForAssay(Pair.of("GSM1055612.Cy5", 1));
        assertThat(subject.findArrayForHybridizationNode(hybridizationNode), is("A-AGIL-28"));
    }

    @Test
    public void testFindCharacteristicValueForAssay43049() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_GEOD_43049).build();
        // GSM1055612.Cy5	A-AGIL-28	Homo sapiens	Caco-2	Apical anaerobic	Apical anaerobic
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "culture condition"), hasItemInArray("Apical anaerobic"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "cell line"), hasItemInArray("Caco-2"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055612.Cy5", 1), "Organism"), hasItemInArray("Homo sapiens"));
        // GSM1055617.Cy3	A-AGIL-28	Homo sapiens	Caco-2	Conventional	Conventional
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "culture condition"), hasItemInArray("Conventional"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "cell line"), hasItemInArray("Caco-2"));
        assertThat(subject.findCharacteristicValueForAssay(Pair.of("GSM1055617.Cy3", 2), "Organism"), hasItemInArray("Homo sapiens"));
    }
}