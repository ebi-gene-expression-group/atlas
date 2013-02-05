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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentTest {

    private static final String SPECIE = "homo sapiens";

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String RUN_ACCESSION_3 = "ENS2";
    private static final String DESCRIPTION = "aDescription";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

    private static final String CELLULAR_COMPONENT = "CELLULAR_COMPONENT";
    private static final String MATERIAL_TYPE = "MATERIAL_TYPE";
    private static final String ORGANISM_PART = "ORGANISM_PART";

    private static final Factor ORGANISM_PART_HEART = new Factor(ORGANISM_PART, ORGANISM_PART, "heart");
    private static final Factor ORGANISM_PART_LIVER = new Factor(ORGANISM_PART, ORGANISM_PART, "liver");
    private static final Factor ORGANISM_PART_LUNG = new Factor(ORGANISM_PART, ORGANISM_PART, "lung");
    private static final Factor MATERIAL_TYPE_TOTALRNA = new Factor(MATERIAL_TYPE, "RNAtype", "total rna");
    private static final Factor MATERIAL_TYPE_LONGA = new Factor(MATERIAL_TYPE, "RNAtype", "long ploy a rna");
    private static final Factor CELLULAR_COMPONENT_NUCLEUS = new Factor(CELLULAR_COMPONENT, CELLULAR_COMPONENT, "nucleus");

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    private Experiment subject;

    @Before
    public void initializeSubject() {

        // heart, nucleus, long a
        experimentRun1 = new ExperimentRun(RUN_ACCESSION_1).addFactor(ORGANISM_PART_HEART);
        experimentRun1.addFactor(CELLULAR_COMPONENT_NUCLEUS);
        experimentRun1.addFactor(MATERIAL_TYPE_LONGA);

        // liver, nucleus, long a
        experimentRun2 = new ExperimentRun(RUN_ACCESSION_2).addFactor(ORGANISM_PART_LIVER);
        experimentRun2.addFactor(CELLULAR_COMPONENT_NUCLEUS);
        experimentRun2.addFactor(MATERIAL_TYPE_LONGA);

        // lung, nucleus, total rna
        experimentRun3 = new ExperimentRun(RUN_ACCESSION_3).addFactor(ORGANISM_PART_LUNG);
        experimentRun3.addFactor(CELLULAR_COMPONENT_NUCLEUS);
        experimentRun3.addFactor(MATERIAL_TYPE_TOTALRNA);

        subject = new ExperimentBuilder().forSpecie(SPECIE)
                                         .withDescription(DESCRIPTION)
                                         .withDefaultQueryType(ORGANISM_PART)
                                         .withDefaultFilterFactors(Collections.EMPTY_SET)
                                         .withExperimentRuns(Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3))
                                         .create();
    }

    @Test
    public void testExperimentRunAccessions() {
        assertThat(subject.getExperimentRunAccessions(), hasItems(RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3));
    }

    @Test
    public void testDescription() {
        assertThat(subject.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testSpecies() {
        assertThat(subject.getSpecie(), is(SPECIE));
    }

    @Test
    public void testDefaultFactorType() {
        assertThat(subject.getDefaultQueryFactorType(), is(ORGANISM_PART));
    }

    @Test
    public void testAllFactorValues() {
        assertThat(subject.getFactorGroup(RUN_ACCESSION_1), hasItems(ORGANISM_PART_HEART, MATERIAL_TYPE_LONGA, CELLULAR_COMPONENT_NUCLEUS));
        assertThat(subject.getFactorGroup(RUN_ACCESSION_2), hasItems(ORGANISM_PART_LIVER, MATERIAL_TYPE_LONGA, CELLULAR_COMPONENT_NUCLEUS));
        assertThat(subject.getFactorGroup(RUN_ACCESSION_3), hasItems(ORGANISM_PART_LUNG, MATERIAL_TYPE_TOTALRNA, CELLULAR_COMPONENT_NUCLEUS));
    }

    @Test
    public void testFilteredFactorValues() {
        assertThat(subject.getFilteredFactors(Sets.newHashSet(ORGANISM_PART_LUNG, MATERIAL_TYPE_TOTALRNA), CELLULAR_COMPONENT), hasItems(CELLULAR_COMPONENT_NUCLEUS));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(ORGANISM_PART_LIVER, CELLULAR_COMPONENT_NUCLEUS), MATERIAL_TYPE), hasItems(MATERIAL_TYPE_LONGA));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(MATERIAL_TYPE_LONGA, CELLULAR_COMPONENT_NUCLEUS), ORGANISM_PART), hasItems(ORGANISM_PART_HEART, ORGANISM_PART_LIVER));
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void filteredFactorValuesShouldThrowExceptionIfNoFactorHasQueryFilterType() {
        subject.getFilteredFactors(Sets.newHashSet(ORGANISM_PART_LUNG, MATERIAL_TYPE_TOTALRNA), "BLA");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void filteredFactorValuesShouldThrowExceptionIfQueryFilterTypeIsTheSameAsAnyFilterFactorType() {
        subject.getFilteredFactors(Sets.newHashSet(ORGANISM_PART_HEART), ORGANISM_PART);
    }

}
