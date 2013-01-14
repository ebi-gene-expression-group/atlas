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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentTest {

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String RUN_ACCESSION_3 = "ENS2";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

    private FactorValue factorValue1 = new FactorValue("ORGANISM_PART", "ORGANISM_PART", "heart");
    private FactorValue factorValue2 = new FactorValue("ORGANISM_PART", "ORGANISM_PART", "liver");
    private FactorValue factorValue3 = new FactorValue("ORGANISM_PART", "ORGANISM_PART", "lung");
    private FactorValue factorValue4 = new FactorValue("MATERIAL_TYPE", "RNAtype", "total rna");

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    private Experiment subject;

    @Before
    public void initializeSubject() {
        experimentRun1 = new ExperimentRun(RUN_ACCESSION_1).addFactorValue("ORGANISM_PART", "ORGANISM_PART", "heart");
        experimentRun2 = new ExperimentRun(RUN_ACCESSION_2).addFactorValue("ORGANISM_PART", "ORGANISM_PART", "liver");
        experimentRun3 = new ExperimentRun(RUN_ACCESSION_3).addFactorValue("ORGANISM_PART", "ORGANISM_PART", "lung");
        experimentRun3.addFactorValue("MATERIAL_TYPE", "RNAtype", "total rna");

        subject = new Experiment(MOCK_EXPERIMENT_ACCESSION, null, Sets.newHashSet(RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3), "ORGANISM_PART")
                .addAll(Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3));
    }

    @Test
    public void testExperimentRunAccessions() {
        assertThat(subject.getExperimentRunAccessions(), hasItems(RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3));
    }

    @Test
    public void testExperimentAccession() {
        assertThat(subject.getExperimentAccession(), is(MOCK_EXPERIMENT_ACCESSION));
    }

    @Test
    public void testDescription() {
        assertThat(subject.getDescription(), nullValue(String.class));
    }

    @Test
    public void testNumberOfRuns() {
        assertThat(subject.getNumberOfRuns(), is(3));
    }

    @Test
    public void testSpecies() {
        assertThat(subject.getSpecie(), nullValue(String.class));
        subject.setSpecie("homo sapiens");
        assertThat(subject.getSpecie(), is("homo sapiens"));
    }

    @Test
    public void testExperimentRun() {
        assertThat(subject.getExperimentRun(RUN_ACCESSION_1), is(experimentRun1));
        assertThat(subject.getExperimentRun(RUN_ACCESSION_2), is(experimentRun2));
        assertThat(subject.getExperimentRun(RUN_ACCESSION_3), is(experimentRun3));
        assertThat(subject.getExperimentRun("non existing run"), nullValue(ExperimentRun.class));
    }

    @Test
    public void testDefaultFactorType() {
        assertThat(subject.getDefaultFactorType(), is("ORGANISM_PART"));
    }

    @Test
    public void testFactorValueByType() {
        assertThat(subject.getFactorValue(RUN_ACCESSION_1, "ORGANISM_PART").getValue(), is("heart"));
        assertThat(subject.getFactorValue(RUN_ACCESSION_1, "MATERIAL_TYPE"), nullValue(FactorValue.class));
        assertThat(subject.getFactorValue(RUN_ACCESSION_2, "ORGANISM_PART").getValue(), is("liver"));
        assertThat(subject.getFactorValue(RUN_ACCESSION_3, "ORGANISM_PART").getValue(), is("lung"));
        assertThat(subject.getFactorValue(RUN_ACCESSION_3, "MATERIAL_TYPE").getValue(), is("total rna"));
    }

    @Test
    public void testAllFactorValues() {
        assertThat(subject.getAllFactorValues(RUN_ACCESSION_1), hasItems(factorValue1));
        assertThat(subject.getAllFactorValues(RUN_ACCESSION_2), hasItems(factorValue2));
        assertThat(subject.getAllFactorValues(RUN_ACCESSION_3), hasItems(factorValue3, factorValue4));
    }

    @Test
    public void testFilteredFactorValues() {
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue1), "ORGANISM_PART").size(), is(1));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue1), "ORGANISM_PART"), hasItems(factorValue1.getValue()));
        // there is no such combination as factorValue1 and factorValue2 on an experimentRun
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue1, factorValue2), "ORGANISM_PART").size(), is(0));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue3, factorValue4), "ORGANISM_PART").size(), is(1));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue3, factorValue4), "ORGANISM_PART"), hasItems(factorValue3.getValue()));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue2), "ORGANISM_PART").size(), is(1));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue2), "ORGANISM_PART"), hasItems(factorValue2.getValue()));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue3), "MATERIAL_TYPE").size(), is(1));
        assertThat(subject.getFilteredFactorValues(Sets.newHashSet(factorValue3), "MATERIAL_TYPE"), hasItems(factorValue4.getValue()));
    }

}