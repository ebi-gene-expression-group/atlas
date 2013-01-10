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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ExperimentTest {

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String RUN_ACCESSION_3 = "ENS2";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

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
    public void testDefaultFactorType() {
        assertThat(subject.getDefaultFactorType(), is("ORGANISM_PART"));
    }

    @Test
    public void testDefaultFactorValue() {
        assertThat(subject.getDefaultFactorValue(RUN_ACCESSION_1).getValue(), is("heart"));
        assertThat(subject.getDefaultFactorValue(RUN_ACCESSION_2).getValue(), is("liver"));
        assertThat(subject.getDefaultFactorValue(RUN_ACCESSION_3).getValue(), is("lung"));
    }

    @Test
    public void testFactorValueByType() {
        assertThat(subject.getFactorValue(RUN_ACCESSION_1, "ORGANISM_PART").getValue(), is("heart"));
        assertThat(subject.getFactorValue(RUN_ACCESSION_1, "MATERIAL_TYPE"), nullValue(FactorValue.class));
        assertThat(subject.getFactorValue(RUN_ACCESSION_3, "ORGANISM_PART").getValue(), is("lung"));
        assertThat(subject.getFactorValue(RUN_ACCESSION_3, "MATERIAL_TYPE").getValue(), is("total rna"));
    }

}